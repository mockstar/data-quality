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
package org.talend.windowkey;

import org.talend.dataquality.common.character.StringHandler;

/**
 * 
 */
public class AlgoBox {

    private static final FingerprintKeyer FINGERPRINTKEYER = new FingerprintKeyer();

    private static final NGramFingerprintKeyer NGRAMKEYER = new NGramFingerprintKeyer();

    private static final org.apache.commons.codec.language.Soundex soundex =
            new org.apache.commons.codec.language.Soundex();

    private static final org.apache.commons.codec.language.DoubleMetaphone doublemetaphone =
            new org.apache.commons.codec.language.DoubleMetaphone();

    private static final org.apache.commons.codec.language.Metaphone metaphone =
            new org.apache.commons.codec.language.Metaphone();

    private static final org.apache.commons.codec.language.ColognePhonetic colognePhonetic =
            new org.apache.commons.codec.language.ColognePhonetic();

    /**
     * DOC ytao Comment method "main".
     * 
     * @param args
     */
    public static void main(String[] args) {

        String sInput = null;
        // key algos (notice that it is incorrect to return null, since the operation +)
        System.out.println("first_Char_EW:" + AlgoBox.first_Char_EW(sInput) + "-"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("first_N_Char_EW:" + AlgoBox.first_N_Char_EW(sInput, 2) + "-"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("first_N_Char:" + AlgoBox.first_N_Char(sInput, 5) + "-"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("last_N_Char:" + AlgoBox.last_N_Char(sInput, 3) + "-"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("first_N_Consonants:" + AlgoBox.first_N_Consonants(sInput, 2000) + "-"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("first_N_Vowels:" + AlgoBox.first_N_Vowels(sInput, 1000000) + "-"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("add_Left_Char:" + AlgoBox.add_Left_Char(sInput, "<") + "-"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        System.out.println("pick_Char:" + AlgoBox.pick_Char(sInput, "1-2;40;0-5") + "-"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        System.out.println("subStr:" + AlgoBox.subStr(sInput, "1;100") + "-"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        System.out.println("metaphone:" + AlgoBox.metaphone(sInput) + "-"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("soundex:" + AlgoBox.soundex(sInput) + "-"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("doublemetaphone:" + AlgoBox.doublemetaphone(sInput) + "-"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("exact:" + AlgoBox.exact(sInput) + "-"); //$NON-NLS-1$ //$NON-NLS-2$

        // optional algos (notice that it is no pbm to return null)
        System.out.println("removeDiacriticalMarks:" + AlgoBox.removeDiacriticalMarks(sInput) + "-"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("removeDMAndLowerCase: " + AlgoBox.removeDMAndLowerCase(sInput)); //$NON-NLS-1$
        System.out.println("removeDMAndUpperCase: " + AlgoBox.removeDMAndUpperCase(sInput)); //$NON-NLS-1$
        System.out.println("useDefault: " + AlgoBox.useDefault(sInput, "ytao")); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("add_Right_Char:" + AlgoBox.add_Right_Char(sInput, ">") + "-"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        System.out.println("lowerCase: " + AlgoBox.lowerCase(sInput)); //$NON-NLS-1$
        System.out.println("upperCase: " + AlgoBox.upperCase(sInput)); //$NON-NLS-1$

    }

    /**
     * Pick characters according to pattern
     * 
     * @deprecated Use {@link StringHandler#pickChar(String, String)} instead
     */
    @Deprecated
    public static String pick_Char(String sInput, String pattern) {
        return StringHandler.pickChar(sInput, pattern);
    }

    /**
     * First N vowels of the string
     * 
     * @deprecated Use {@link StringHandler#firstNConsonants(String, int)} instead
     */
    @Deprecated
    public static String first_N_Consonants(String sInput, int nb) {
        return StringHandler.firstNConsonants(sInput, nb);
    }

    /**
     * First N consonants of the string
     * 
     * @deprecated Use {@link StringHandler#firstNVowels(String, int)} instead
     */
    @Deprecated
    public static String first_N_Vowels(String sInput, int nb) {
        return StringHandler.firstNVowels(sInput, nb);
    }

    /**
     * substring
     * 
     * @deprecated Use {@link StringHandler#subStr(String, String)} instead
     */
    @Deprecated
    public static String subStr(String sInput, String pattern) {
        return StringHandler.subStr(sInput, pattern);
    }

    /**
     * first N characters of the string
     * 
     * @deprecated Use {@link StringHandler#firstNChar(String, int)} instead
     */
    @Deprecated
    public static String first_N_Char(String sInput, int nb) {
        return StringHandler.firstNChar(sInput, nb);
    }

    /**
     * last N characters of the string
     * 
     * @deprecated Use {@link StringHandler#lastNChar(String, int)} instead
     */
    @Deprecated
    public static String last_N_Char(String sInput, int nb) {
        return StringHandler.lastNChar(sInput, nb);
    }

    /**
     * N first characters of each word
     * 
     * @deprecated Use {@link StringHandler#firstNCharEW(String, int)} instead
     */
    @Deprecated
    public static String first_N_Char_EW(String sInput, int nb) {
        return StringHandler.firstNCharEW(sInput, nb);
    }

    /**
     * First character of each word
     * 
     * @deprecated Use {@link StringHandler#firstCharEW(String)} instead
     */
    @Deprecated
    public static String first_Char_EW(String sInput) {
        return StringHandler.firstCharEW(sInput);
    }

    public static String soundex(String sInput) {
        if (sInput == null) {
            return ""; //$NON-NLS-1$
        }
        return soundex.soundex(sInput);
    }

    public static String doublemetaphone(String sInput) {
        if (sInput == null) {
            return ""; //$NON-NLS-1$
        }

        return doublemetaphone.doubleMetaphone(sInput);
    }

    public static String metaphone(String sInput) {
        if (sInput == null) {
            return ""; //$NON-NLS-1$
        }

        return metaphone.metaphone(sInput);
    }

    /*-----------------------optional algo---------------------*/

    /**
     * Add left position character
     * 
     * @deprecated Use {@link StringHandler#addPrefix(String, String)} instead
     */
    @Deprecated
    public static String add_Left_Char(String sInput, String position) {
        return StringHandler.addPrefix(sInput, position);
    }

    /**
     * Add right position character
     * 
     * @deprecated Use {@link StringHandler#concatenate(String, String)} instead
     */
    @Deprecated
    public static String add_Right_Char(String sInput, String position) {
        return StringHandler.concatenate(sInput, position);
    }

    /**
     * Remove diacritical marks
     * 
     * @deprecated Use {@link StringHandler#removeDiacriticalMarks(String)} instead
     */
    @Deprecated
    public static String removeDiacriticalMarks(String sInput) {
        return StringHandler.removeDiacriticalMarks(sInput);
    }

    /**
     * Return the input or empty if null.
     * 
     * @deprecated Use {@link StringHandler#exact(String)} instead
     */
    @Deprecated
    public static String exact(String sInput) {
        return StringHandler.exact(sInput);
    }

    /**
     * Return the input or default if null or empty.
     * 
     * @deprecated Use {@link StringHandler#useDefault(String, String)} instead
     */
    @Deprecated
    public static String useDefault(String sInput, String insteadOf) {
        return StringHandler.useDefault(sInput, insteadOf);
    }

    /**
     * @deprecated Use {@link StringHandler#lowerCase(String)} instead
     */
    @Deprecated
    public static String lowerCase(String sInput) {
        return StringHandler.lowerCase(sInput);
    }

    /**
     * @deprecated Use {@link StringHandler#upperCase(String)} instead
     */
    @Deprecated
    public static String upperCase(String sInput) {
        return StringHandler.upperCase(sInput);
    }

    /**
     * @deprecated Use {@link StringHandler#removeDMAndLowerCase(String)} instead
     */
    @Deprecated
    public static String removeDMAndLowerCase(String sInput) {
        return StringHandler.removeDMAndLowerCase(sInput);
    }

    /**
     * @deprecated Use {@link StringHandler#removeDMAndUpperCase(String)} instead
     */
    @Deprecated
    public static String removeDMAndUpperCase(String sInput) {
        return StringHandler.removeDMAndUpperCase(sInput);
    }

    public static String fingerPrintKey(String sInput) {
        if (sInput == null) {
            return null;
        }
        return FINGERPRINTKEYER.key(sInput);
    }

    public static String nGramKey(String sInput) {
        if (sInput == null) {
            return null;
        }
        return NGRAMKEYER.key(sInput);
    }

    public static String colognePhonetic(String sInput) {
        if (sInput == null) {
            return null;
        }
        return colognePhonetic.colognePhonetic(sInput);
    }

}
