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

/**
 * Utility class for removal of accents in texts. This solution handles only the accented letters in latin-1 suppliment
 * charset,
 * but is much faster than StringUtils.stripAccents(input) from Apache Commons Lang package.
 */
public final class AsciiUtils {

    // private constructor, can't be instantiated!
    private AsciiUtils() {
    }

    /**
     * Removes diacritical mark from a character.
     * 
     * @param c a character
     * @return the same input character without the diacritical mark if any.
     * @deprecated Use {@link org.talend.dataquality.common.character.AsciiUtils#removeDiacriticalMark(char)} instead.
     */
    @Deprecated
    public static char removeDiacriticalMark(char c) {
        return org.talend.dataquality.common.character.AsciiUtils.removeDiacriticalMark(c);
    }

    /**
     * Removes diacritical marks from a string.
     * 
     * @param st a string
     * @return a new string without the diacritical mark if any.
     * @deprecated Use {@link org.talend.dataquality.common.character.AsciiUtils#removeDiacriticalMarks(String)} instead.
     */
    @Deprecated
    public static String removeDiacriticalMarks(String st) {
        return org.talend.dataquality.common.character.AsciiUtils.removeDiacriticalMarks(st);
    }

}
