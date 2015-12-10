// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.statistics.type;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.talend.dataquality.statistics.datetime.DateTimePatternManager;

/**
 * Utility class refering data types given single value
 * 
 * @author zhao
 *
 */
public class TypeInferenceUtils {

    private static final Pattern patternInteger = Pattern.compile("^(\\+|-)?\\d+$");

    private static final Pattern patternDouble = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$");

    /**
     * Detect if the given value is a double type.
     * 
     * <p>
     * Note:<br>
     * 1. This method support only English locale.<br>
     * e.g. {@code TypeInferenceUtils.isDouble("3.4")} returns {@code true}.<br>
     * e.g. {@code TypeInferenceUtils.isDouble("3,4")} returns {@code false}.<br>
     * 2. Exponential notation can be detected as a valid double.<br>
     * e.g. {@code TypeInferenceUtils.isDouble("1.0E+4")} returns {@code true}.<br>
     * e.g. {@code TypeInferenceUtils.isDouble("1.0e-4")} returns {@code true}.<br>
     * e.g. {@code TypeInferenceUtils.isDouble("1.0e-04")} returns {@code true}.<br>
     * 3. Numbers marked with a type is invalid.<br>
     * e.g. {@code TypeInferenceUtils.isDouble("3.4d")} returns {@code false}.<br>
     * e.g. {@code TypeInferenceUtils.isDouble("123L")} returns {@code false}.<br>
     * 4. White space is invalid.<br>
     * e.g. {@code TypeInferenceUtils.isDouble(" 3.4")} returns {@code false}.<br>
     * e.g. {@code TypeInferenceUtils.isDouble("3.4 ")} returns {@code false}.<br>
     * 5. "." is not obligatory.<br>
     * e.g. {@code TypeInferenceUtils.isDouble("100")} returns {@code true}.
     * <P>
     * 
     * @param value the value to be detected.
     * @return true if the value is a double type, false otherwise.
     */
    public static boolean isDouble(String value) {
        if (!isEmpty(value)) {
            if (patternDouble.matcher(value).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Detect if the given value is a integer type.
     * 
     * @param value the value to be detected.
     * @return true if the value is a integer type, false otherwise.
     */
    public static boolean isInteger(String value) {
        if (!isEmpty(value)) {
            if (patternInteger.matcher(value).matches()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumber(String value) {
        return isDouble(value) || isInteger(value);

    }

    /**
     * Get double value given a string value. Compute the expression if allow calculation parameter is set to true.
     * 
     * @author mzhao
     * @since 1.0
     * @param value String value to be converted to double.
     * @param allowCalculas Whether or not an expression need to be evaluated as double (in case of the expression
     * exist)
     * @return converted double value , Doulbe.NaN if the string value cannot be converted as double.
     */
    public static double getDouble(String value, boolean allowCalculas) {
        // TODO implement allowCalculas
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    /**
     * Get big integer from a string.
     * 
     * @param value
     * @return big integer instance , or null if numer format exception occurrs.
     */
    public static BigInteger getBigInteger(String value) {
        BigInteger bint = null;
        try {
            bint = new BigInteger(value);
        } catch (NumberFormatException e) {
            return null;
        }
        return bint;
    }

    /**
     * Detect if the given value is a boolean type.
     * 
     * @param value the value to be detected.
     * @return true if the value is a boolean type, false otherwise.
     */
    public static boolean isBoolean(String value) {
        if (!isEmpty(value) && (value.trim().length() == 4 || value.trim().length() == 5)) {
            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Detect if the given value is a date type. <br>
     *
     * @param value the value to be detected.
     * @return true if the value is a date type, false otherwise.
     * @see TypeInferenceUtils#isDate(String, List)
     */
    public static boolean isDate(String value) {
        return isDate(value, Collections.<String> emptyList());
    }

    /**
     * Detect if the given value is a date type using the given custom date patterns first. <br>
     * Date regex used to match: http://regexlib.com/REDetails.aspx?regexp_id=361 ,and regex matching yyy-MM-dd
     * HH:mm:ss.SSS
     *
     * @param value the value to be detected.
     * @param customDatePatterns optional custom date patterns to use before the registered ones.
     * @return true if the value is a date type, false otherwise.
     */
    public static boolean isDate(String value, List<String> customDatePatterns) {
        return DateTimePatternManager.isDate(value, customDatePatterns);
    }

    /**
     * Detect if the given value is a date type using the given custom date patterns first. <br>
     * Date regex used to match: http://regexlib.com/REDetails.aspx?regexp_id=361 ,and regex matching yyy-MM-dd
     * HH:mm:ss.SSS
     *
     * @param value the value to be detected.
     * @param customDatePatterns optional custom date patterns to use before the registered ones.
     * @param locale the locale to use to parse the date.
     * @return true if the value is a date type, false otherwise.
     */
    public static boolean isDate(String value, List<String> customDatePatterns, Locale locale) {
        return DateTimePatternManager.isDate(value, customDatePatterns, locale);
    }

    /**
     * Detect if the given value is a time type.
     * 
     * @param value
     * @return
     */
    public static boolean isTime(String value) {
        return DateTimePatternManager.isTime(value);
    }

    /**
     * Detect if the given value is blank or null.
     * 
     * @param value the value to be detected.
     * @return true if the value is blank or null, false otherwise.
     */
    public static boolean isEmpty(String value) {
        return (value == null || value.trim().length() == 0);
    }

    /**
     * 
     * @param type the expected type
     * @param value the value to be detected
     * @return true if the type of value is expected, false otherwise.
     */
    public static boolean isValid(DataTypeEnum type, String value) {

        switch (type) {
        case BOOLEAN:
            return isBoolean(value);
        case INTEGER:
            return isInteger(value);
        case DOUBLE:
            return isDouble(value);
        case DATE:
            return isDate(value);
        case STRING:
            // Everything can be a string
            return true;
        default:
            // Unsupported type
            return false;
        }
    }

}
