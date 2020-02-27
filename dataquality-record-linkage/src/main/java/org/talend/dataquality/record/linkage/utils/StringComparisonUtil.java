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
package org.talend.dataquality.record.linkage.utils;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @author scorreia
 */
public final class StringComparisonUtil implements Serializable {

    private static final long serialVersionUID = 8786564034104314360L;

    /**
     * StringComparisonUtil constructor.
     */
    private StringComparisonUtil() {
    }

    /**
     * Return the number of characters in the two Strings that are the same.
     * 
     * @param str1 a String.
     * @param str2 a String.
     * @return The number of characters in the two Strings that are the same.
     * @deprecated Use {@link org.talend.dataquality.common.character.StringComparisonUtil#difference(String, String)}
     * instead.
     */
    @Deprecated
    public static int difference(String str1, String str2) {
        return org.talend.dataquality.common.character.StringComparisonUtil.difference(str1, str2);
    }

    // scorreia: this method comes from the simmetrics library (no change has been done).
    /**
     * Return a string buffer of characters from string1 within string2 if they are of a given distance separation from
     * the position in string1.
     * 
     * @param string1 a String.
     * @param string2 a String.
     * @param distanceSep maximum distance separating the characters from the two strings.
     * @return a string buffer of characters from string1 within string2 if they are of a given distance separation from
     * the position in string1.
     * @deprecated Use
     * {@link org.talend.dataquality.common.character.StringComparisonUtil#getCommonCharacters(String, String, int)}
     * instead.
     */
    @Deprecated
    public static StringBuilder getCommonCharacters(final String string1, final String string2, final int distanceSep) {
        return org.talend.dataquality.common.character.StringComparisonUtil.getCommonCharacters(string1, string2,
                distanceSep);
    }

    /**
     * Compute the prefix length found of common characters at the beginning of the strings.
     *
     * @param string1 a String.
     * @param string2 a String.
     * @return the prefix length found of common characters at the beginning of the strings.
     * @deprecated Use
     * {@link org.talend.dataquality.common.character.StringComparisonUtil#getPrefixLength(String, String)} instead.
     */
    @Deprecated
    public static int getPrefixLength(final String string1, final String string2) {
        return org.talend.dataquality.common.character.StringComparisonUtil.getPrefixLength(string1, string2);
    }
}
