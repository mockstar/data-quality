package org.talend.dataquality.common.character;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringComparisonUtilTest {

    private String str1 = "abc"; //$NON-NLS-1$

    private String str2 = "abc"; //$NON-NLS-1$

    private String str3 = "abcd"; //$NON-NLS-1$

    private String str4 = "abd"; //$NON-NLS-1$

    private String str5 = "bcd"; //$NON-NLS-1$

    private String str6 = "拓蓝科技"; //$NON-NLS-1$

    private String str7 = "拓蓝"; //$NON-NLS-1$

    private String str8 = "𠀠𠀡𠀢"; //$NON-NLS-1$

    private String str9 = "𠀠"; //$NON-NLS-1$

    private String str10 = "𠀐𠀐𠀐"; //$NON-NLS-1$

    private String str11 = "𠀐"; //$NON-NLS-1$

    /**
     * Test method for
     * {@link StringComparisonUtil#difference(String,String)} .
     */
    @Test
    public void testDifference() {
        assertEquals(0, StringComparisonUtil.difference(null, str2));
        assertEquals(0, StringComparisonUtil.difference(str1, null));
        assertEquals(3, StringComparisonUtil.difference(str1, str2));
        assertEquals(3, StringComparisonUtil.difference(str1, str3));
        assertEquals(2, StringComparisonUtil.difference(str1, str4));
        assertEquals(0, StringComparisonUtil.difference(str1, str5));
        assertEquals(0, StringComparisonUtil.difference(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(1, StringComparisonUtil.difference(" ", " ")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(0, StringComparisonUtil.difference(null, null));
        // TDQ-15079: Support Chinese and surrogate pair characters
        assertEquals(2, StringComparisonUtil.difference(str6, str7));
        assertEquals(1, StringComparisonUtil.difference(str8, str9));
        assertEquals(0, StringComparisonUtil.difference(str8, str10));
        assertEquals(0, StringComparisonUtil.difference(str8, str11));
        assertEquals(1, StringComparisonUtil.difference(str10, str11));
        assertEquals(2, StringComparisonUtil.difference("𠀠𠀡𠀢", "𠀠𠀡a"));
        assertEquals(3, StringComparisonUtil.difference("𠀐𠀑𠀒𠀓𠀔", "𠀐𠀑a𠀓b"));
    }

    /**
     * Test method for {@link StringComparisonUtil#getCommonCharacters(String, String, int))} .
     */
    @Test
    public void testGetCommonCharacters() {
        assertEquals("abc", StringComparisonUtil.getCommonCharacters(str1, str2, 1).toString()); //$NON-NLS-1$
        assertEquals("abc", StringComparisonUtil.getCommonCharacters(str1, str3, 1).toString()); //$NON-NLS-1$
        assertEquals("ab", StringComparisonUtil.getCommonCharacters(str1, str4, 1).toString()); //$NON-NLS-1$
        assertEquals("bc", StringComparisonUtil.getCommonCharacters(str1, str5, 1).toString()); //$NON-NLS-1$
        assertEquals(" ", StringComparisonUtil.getCommonCharacters(" ", "  ", 1).toString()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        assertEquals("", StringComparisonUtil.getCommonCharacters(null, "  ", 1).toString()); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("", StringComparisonUtil.getCommonCharacters(null, null, 1).toString()); //$NON-NLS-1$ //$NON-NLS-2$
        // TDQ-15079: Support Chinese and surrogate pair characters
        assertEquals(str7, StringComparisonUtil.getCommonCharacters(str6, str7, 1).toString());
        assertEquals(str9, StringComparisonUtil.getCommonCharacters(str8, str9, 1).toString());
        assertEquals("𠀠𠀡", StringComparisonUtil.getCommonCharacters("𠀠𠀡𠀢", "𠀠a𠀡", 2).toString());
        assertEquals("𠀐𠀑𠀒", StringComparisonUtil.getCommonCharacters("𠀐𠀑𠀒𠀓𠀔", "a𠀐𠀒c𠀑", 3).toString());
    }

    /**
     * Test method for
     * {@link StringComparisonUtil#getPrefixLength(String,String)}
     * .
     */
    @Test
    public void testGetPrefixLength() {
        assertEquals(0, StringComparisonUtil.getPrefixLength(null, str2));
        assertEquals(0, StringComparisonUtil.getPrefixLength(str1, null));
        assertEquals(3, StringComparisonUtil.getPrefixLength(str1, str2));
        assertEquals(3, StringComparisonUtil.getPrefixLength(str1, str3));
        assertEquals(2, StringComparisonUtil.getPrefixLength(str1, str4));
        assertEquals(0, StringComparisonUtil.getPrefixLength(str1, str5));
        assertEquals(0, StringComparisonUtil.getPrefixLength(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(1, StringComparisonUtil.getPrefixLength(" ", "  ")); //$NON-NLS-1$ //$NON-NLS-2$
        // TDQ-15079: Support Chinese and surrogate pair characters
        assertEquals(2, StringComparisonUtil.getPrefixLength(str6, str7));
        assertEquals(1, StringComparisonUtil.getPrefixLength(str8, str9));
        assertEquals(2, StringComparisonUtil.getPrefixLength("𠀐𠀑𠀒𠀓𠀔", "𠀐𠀑𠀠𠀡𠀢𠀣"));
        assertEquals(1, StringComparisonUtil.getPrefixLength("𠀐𠀑𠀒𠀓𠀔", "𠀐a"));
        assertEquals(0, StringComparisonUtil.getPrefixLength("我𠀑𠀒𠀓𠀔", "𠀐𠀑𠀒ab"));
    }

}