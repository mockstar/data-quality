// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.record.linkage.iterator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.talend.dataquality.matchmerge.Attribute;
import org.talend.dataquality.matchmerge.Record;

import junit.framework.TestCase;

/**
 * DOC qiongli class global comment. Detailled comment
 */
public class ResultSetIteratorTest extends TestCase {

    List<String> elementNames = new ArrayList<String>();

    @Override
    @Before
    public void setUp() throws Exception {
        elementNames.add("Id"); //$NON-NLS-1$
        elementNames.add("Name"); //$NON-NLS-1$
        elementNames.add("birthday"); //$NON-NLS-1$
    }

    @Test
    // Test zeroDate like as "0000-00-00 00:00:". it will get a SQLException and set current date to null and continue.
    public void testNext() throws SQLException {

        ResultSetMetaData metaData = Mockito.mock(ResultSetMetaData.class);
        when(metaData.getColumnCount()).thenReturn(3);

        ResultSet results = Mockito.mock(ResultSet.class);
        when(results.getMetaData()).thenReturn(metaData);
        when(results.getObject(any())).thenReturn(1, "Lily", "2015-6-25");

        Statement statement = Mockito.mock(Statement.class);
        when(statement.getResultSet()).thenReturn(results);

        Connection conn = Mockito.mock(Connection.class);
        when(conn.createStatement()).thenReturn(statement);

        ResultSetIterator resIterator = new ResultSetIterator(conn, null, elementNames);
        Record next = resIterator.next();
        Assert.assertNotNull(next);
        List<Attribute> attributes = next.getAttributes();
        Assert.assertTrue(attributes.size() == 3);
        for (Attribute attribute : attributes) {
            Assert.assertNotNull(attribute);
        }
    }

    /**
     * Test method for {@link org.talend.dataquality.record.linkage.iterator.ResultSetIterator#next()}. when it get
     * SQLException like as the date is "0000-00-00 00:00:00", replace the attribute to null and continue to do next.
     * 
     * @throws SQLException
     */
    @Test
    // Test zeroDate like as "0000-00-00 00:00:". it will get a SQLException and set current date to null and continue.
    public void testNext_zerodDate() throws SQLException {
        ResultSetMetaData metaData = Mockito.mock(ResultSetMetaData.class);
        when(metaData.getColumnCount()).thenReturn(3);

        ResultSet results = Mockito.mock(ResultSet.class);
        when(results.getMetaData()).thenReturn(metaData);
        when(results.getObject(any())).thenReturn(2, "Lily", "0000-00-00 00:00:00");

        Statement statement = Mockito.mock(Statement.class);
        when(statement.getResultSet()).thenReturn(results);

        Connection conn = Mockito.mock(Connection.class);
        when(conn.createStatement()).thenReturn(statement);

        ResultSetIterator resIterator = new ResultSetIterator(conn, null, elementNames);
        Record next = resIterator.next();
        Assert.assertNotNull(next);
        Assert.assertTrue(next.getAttributes().size() == 3);
        Assert.assertNull(next.getAttributes().get(2).getValue());
    }

}
