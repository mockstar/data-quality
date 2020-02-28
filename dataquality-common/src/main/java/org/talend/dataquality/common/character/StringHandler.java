package org.talend.dataquality.common.character;

public class StringHandler {

    /**
     * Pick characters according to the given pattern of the form "expr1;expr2;...;exprN".
     * <p>
     * An expression can be of two forms :
     * <li>
     * <ul>
     * A single number in order to point to a unique position in the string.
     * </ul>
     * <ul>
     * Two numbers separated by a '-', in order to take all the characters between two positions.
     * </ul>
     * </li>
     *
     * @param sInput the input string
     * @param pattern the pattern of the form "expr1;expr2;...;exprN"
     * @return the concatenated sequence corresponding to the expressions in the pattern.
     */
    public static String pickChar(String sInput, String pattern) {

        if (sInput == null || "".equals(sInput.trim())) { //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        }

        if (pattern == null || "".equals(pattern.trim())) { //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        }

        String d_pattern = "^[0-9--;]*"; //$NON-NLS-1$

        if (!pattern.matches(d_pattern)) {
            return ""; //$NON-NLS-1$
        }

        StringBuilder sb = new StringBuilder();
        String[] arr_1 = pattern.split(";"); //$NON-NLS-1$

        for (String valueOf_arr_1 : arr_1) {

            if (!"".equals(valueOf_arr_1)) { //$NON-NLS-1$
                String[] arr_2 = valueOf_arr_1.split("-"); //$NON-NLS-1$
                int len_arr_2 = arr_2.length;

                if (len_arr_2 == 2) {

                    if (!"".equals(arr_2[0]) && !"".equals(arr_2[1])) {
                        sb.append(subStr(sInput, arr_2[0] + ";" + arr_2[1])); //$NON-NLS-1$
                    }
                } else if (len_arr_2 == 1) {
                    if (Integer.parseInt(arr_2[0]) < sInput.length()) {
                        // support surrogate pair.replace charAt with subString
                        int index = Integer.parseInt(arr_2[0]);
                        int startOffset = sInput.offsetByCodePoints(0, index);
                        String substring = sInput.substring(startOffset, sInput.offsetByCodePoints(startOffset, 1));
                        sb.append(substring);
                    }

                }
            }
        }

        return sb.toString();
    }

    /**
     * First N consonants of the string.
     *
     * @param sInput the input string
     * @param nb the number of consonants to take from the beginning of the input string
     * @return the first N consonants of the input string.
     */
    public static String firstNConsonants(String sInput, int nb) {
        if (sInput == null || "".equals(sInput.trim())) { //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        }

        String d_pattern = "[a-zA-Z&&[^aeiouAEIOU]]"; //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        int s_len = sInput.length();
        String s;

        for (int i = 0; i < s_len; i++) {
            s = sInput.substring(i, i + 1);
            if (!" ".equals(s) && s.matches(d_pattern) && ((--nb) >= 0)) { //$NON-NLS-1$
                sb.append(s);
            }
        }

        return sb.toString();
    }

    /**
     * First N vowels of the string.
     *
     * @param sInput the input string
     * @param nb the number of consonants to take from the beginning of the string
     * @return the first N vowels of the input string.
     */
    public static String firstNVowels(String sInput, int nb) {
        if (sInput == null || "".equals(sInput.trim())) { //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        }

        String d_pattern = "[aeiouAEIOU]"; //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        int s_len = sInput.length();
        String s;

        for (int i = 0; i < s_len; i++) {
            s = sInput.substring(i, i + 1);
            if (!" ".equals(s) && s.matches(d_pattern) && ((--nb) >= 0)) { //$NON-NLS-1$
                sb.append(s);
            }
        }

        return sb.toString();
    }

    /**
     * Substring the input according to the given pattern number of the form "start;end".
     * "start" and "end" must be positive integers. The character at the "end" position will not be returned.
     * If the "end" position is greater than the input length, everything from the start position will be returned.
     * 
     * @param sInput the input string
     * @param pattern pattern of the form "start;end"
     * @return the input string between start and end value of the pattern, with the end position excluded.
     */
    public static String subStr(String sInput, String pattern) {

        if (sInput == null || "".equals(sInput)) { //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        }

        if (pattern == null) {
            return ""; //$NON-NLS-1$
        } else {
            String d_pattern = "^[0-9]*[;][0-9]*"; //$NON-NLS-1$
            if (pattern.matches(d_pattern)) {
                int beginIndex = Integer.parseInt(pattern.substring(0, pattern.indexOf(";"))); //$NON-NLS-1$
                int endIndex = Integer.parseInt(pattern.substring(pattern.indexOf(";") + 1)); //$NON-NLS-1$

                int sInputCPCount = sInput.codePointCount(0, sInput.length());
                if (sInputCPCount < endIndex) {
                    endIndex = sInputCPCount;
                }

                if (beginIndex <= endIndex) {
                    return sInput.substring(sInput.offsetByCodePoints(0, beginIndex),
                            sInput.offsetByCodePoints(0, endIndex));
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * First N characters of the string.
     *
     * @param sInput the input string
     * @param nb the number of characters to take from the beginning of the string.
     * @return the first N characters of the input string.
     */
    public static String firstNChar(String sInput, int nb) {
        if (sInput == null || "".equals(sInput)) { //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        }

        if (nb < 0) {
            return sInput;
        }

        int sInputCPCount = sInput.codePointCount(0, sInput.length());
        if (sInputCPCount < nb) {
            nb = sInputCPCount;
        }
        return sInput.substring(0, sInput.offsetByCodePoints(0, nb));
    }

    /**
     * Last N characters of the string.
     *
     * @param sInput the input string
     * @param nb the number of characters to take from the end of the string
     * @return the last N characters of the input string.
     */
    public static String lastNChar(String sInput, int nb) {

        if (sInput == null || "".equals(sInput)) { //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        }
        int s_len = sInput.codePointCount(0, sInput.length());
        if (s_len < nb) {
            nb = s_len;
        }
        return sInput.substring(sInput.offsetByCodePoints(0, s_len - nb));
    }

    /**
     * N first characters of each word.
     *
     * @param sInput the input string
     * @param nb the number of characters to take from the beginning of each word
     * @return the first N characters of each word in the input string.
     */
    public static String firstNCharEW(String sInput, int nb) {

        if (sInput == null || "".equals(sInput)) { //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        }

        StringBuilder sb = new StringBuilder();
        String[] words = sInput.split("[ \t\n\r\f]+");

        for (String word : words) {
            sb.append(firstNChar(word, nb));
        }

        return sb.toString();
    }

    /**
     * First character of each word.
     *
     * @param sInput the input string
     * @return the first character of each word in the string.
     */
    public static String firstCharEW(String sInput) {

        if (sInput == null || "".equals(sInput)) { //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        }

        StringBuilder sb = new StringBuilder();
        String[] words = sInput.split("[ \t\n\r\f]+");

        for (String word : words) {
            sb.append(word, 0, word.offsetByCodePoints(0, 1));
        }

        return sb.toString();
    }

    /*-----------------------optional algo---------------------*/

    /**
     * Insert prefix string in a safe manner.
     *
     * @param sInput the input string
     * @param toInsert the string to insert on the left
     * @return the input string prefixed with the string to insert.
     */
    public static String addPrefix(String sInput, String toInsert) {

        if (toInsert == null || "".equals(toInsert)) { //$NON-NLS-1$
            return sInput;
        }

        if (sInput == null) {
            sInput = ""; //$NON-NLS-1$
        }

        return toInsert + sInput;
    }

    /**
     * Concatenate two strings in a safe manner.
     *
     * @param sInput the input string
     * @param toConcatenate string to concatenate
     * @return the concatenation of the input string with the given string.
     */
    public static String concatenate(String sInput, String toConcatenate) {

        if (toConcatenate == null || "".equals(toConcatenate)) { //$NON-NLS-1$
            return sInput;
        }

        if (sInput == null) {
            sInput = ""; //$NON-NLS-1$
        }

        return sInput + toConcatenate;
    }

    /**
     * Remove diacritical marks in the string.
     * 
     * @param sInput the input string
     * @return the input string where all the characters containing diacritical marks have been replaced by the
     * corresponding ascii character.
     */
    public static String removeDiacriticalMarks(String sInput) {
        if (sInput == null) {
            return null;
        }
        return AsciiUtils.removeDiacriticalMarks(sInput);
    }

    /**
     * Return the value or empty if not set.
     * 
     * @param sInput the input string
     * @return the input string if not null, an empty string otherwise.
     */
    public static String exact(String sInput) {
        // must set it to "" when it is null. otherwise use + to contact will get "null"
        return sInput == null ? "" : sInput; //$NON-NLS-1$
    }

    /**
     * Use default value in case input string is not set.
     * 
     * @param sInput input string
     * @param insteadOf default value
     * @return the input string if neither empty nor null, default value otherwise.
     */
    public static String useDefault(String sInput, String insteadOf) {

        if (sInput == null || "".equals(sInput)) { //$NON-NLS-1$
            return insteadOf;
        } else {
            return sInput;
        }
    }

    public static String lowerCase(String sInput) {
        if (sInput == null) {
            return null;
        }
        return sInput.toLowerCase();
    }

    public static String upperCase(String sInput) {
        if (sInput == null) {
            return null;
        }
        return sInput.toUpperCase();
    }

    public static String removeDMAndLowerCase(String sInput) {
        if (sInput == null) {
            return null;
        }
        return lowerCase(removeDiacriticalMarks(sInput));
    }

    public static String removeDMAndUpperCase(String sInput) {
        if (sInput == null) {
            return null;
        }
        return upperCase(removeDiacriticalMarks(sInput));
    }
}
