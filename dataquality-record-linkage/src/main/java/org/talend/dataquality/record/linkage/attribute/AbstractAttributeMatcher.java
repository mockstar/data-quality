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
package org.talend.dataquality.record.linkage.attribute;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;
import org.talend.dataquality.record.linkage.constant.TokenizedResolutionMethod;
import org.talend.windowkey.FingerprintKeyer;

/**
 * Abstract matcher class for shared operations like blank string checking.
 */
public abstract class AbstractAttributeMatcher implements IAttributeMatcher, Serializable {

    private static final long serialVersionUID = -21096755142812677L;

    private NullOption nullOption = NullOption.nullMatchNull;

    private String attributeName = null;

    protected boolean tokenize = false;

    private TokenizedResolutionMethod tokenMethod = TokenizedResolutionMethod.SAMEPLACE;

    private double bestWeightSameOrder;

    private boolean initialComparison = false;

    private boolean fingerPrintApply = false;

    private String regexTokenize = " ";

    @Override
    public float getThreshold() {
        // TODO Default implementations
        throw new NotImplementedException();
    }

    @Override
    public double getWeight() {
        // TODO Default implementations
        throw new NotImplementedException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.attribute.IAttributeMatcher#getMatchingWeight(java.lang.String,
     * java.lang.String)
     */
    @Override
    public double getMatchingWeight(String str1, String str2) {
        switch (nullOption) {
        case nullMatchAll:
            if (isNullOrEmpty(str1) || isNullOrEmpty(str2)) {
                return 1.0;
            }
            break;
        case nullMatchNone:
            if (isNullOrEmpty(str1) || isNullOrEmpty(str2)) {
                return 0.0;
            }
            break;
        case nullMatchNull:
            boolean str1IsNull = isNullOrEmpty(str1);
            boolean str2IsNull = isNullOrEmpty(str2);
            if (str1IsNull && str2IsNull) { // both null => match
                return 1.0;
            } else if (str1IsNull || str2IsNull) { // only one null => non-match
                return 0.0;
            }
            break;
        default:
            break;
        }

        assert !isNullOrEmpty(str1) : "string should not be null or empty here"; //$NON-NLS-1$
        assert !isNullOrEmpty(str2) : "string should not be null or empty here"; //$NON-NLS-1$
        // TDQ-10366 qiongli,catch the Exception.
        try {

            if (fingerPrintApply) {
                FingerprintKeyer fg = new FingerprintKeyer();
                str1 = fg.key(str1);
                str2 = fg.key(str2);
            }

            if (!tokenize) {
                return getWeight(str1, str2);
            } else {
                switch (tokenMethod) {
                case ANYORDER:
                    return computeWeightTokenHungarian(str1, str2);
                case SAMEPLACE:
                    return computeWeightTokenSamePlace(str1, str2);
                case SAMEORDER:
                    return computeWeightTokenSameOrder(str1, str2);
                default:
                    return 0;
                }
            }
            // System.out.println("weight : " + weight);
        } catch (Exception exc) {
            // return 0 if it has exception.
            return 0;
        }
    }

    /**
     * 
     * DOC dprot if one of the two strings correspond to an initial, return 0 or 1 according to the other initial
     * if not, return -1
     * 
     * @param str1
     * @param str2
     * @return
     */
    private double getInitialSimilarity(String str1, String str2) {
        String strShort = str1, strLong = str2;
        if (strShort.length() > strLong.length()) {
            strShort = str2;
            strLong = str1;
        }
        if (strShort.length() == 1 || (strShort.length() == 2 && strShort.substring(1, 2).equals("."))) {
            if (strShort.charAt(0) == strLong.charAt(0))
                return 1;
            else
                return 0;
        }
        return -1;
    }

    private double computeWeightTokenHungarian(String str1, String str2) {

        // --- Compute the lists of tokens
        String[] list1 = str1.split(regexTokenize);
        String[] list2 = str2.split(regexTokenize);

        // --- Create the matrix of weights
        int n = list1.length;
        int m = list2.length;
        int maxDim = Math.max(n, m);
        double[][] weights = new double[maxDim][maxDim];
        for (double[] row : weights)
            Arrays.fill(row, 0.0);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (initialComparison) {
                    double simInitial = getInitialSimilarity(list1[i], list2[j]);
                    if (simInitial != -1)
                        weights[i][j] = 1 - simInitial;
                    else {
                        double similarityMeasure = getWeight(list1[i], list2[j]);
                        weights[i][j] = 1 - similarityMeasure;
                    }
                } else {
                    double similarityMeasure = getWeight(list1[i], list2[j]);
                    weights[i][j] = 1 - similarityMeasure;
                }
            }
        }

        // --- Compute the maximum weighted matching
        int[] match = new HungarianAlgorithm(weights).execute();

        // --- Compute the weight
        double weight = 0;
        for (int i = 0; i < maxDim; i++) {
            if (i < n && match[i] < m) {
                // System.out.println("match[" + list1[i] + "," + list2[match[i]] + "] = " + (1 - weights[i][match[i]]));
                weight += (1 - weights[i][match[i]]);
            }
        }
        weight /= maxDim;
        return weight;
    }

    private double computeWeightTokenSamePlace(String str1, String str2) {
        // --- Compute the lists of tokens
        String[] list1 = str1.split(regexTokenize);
        String[] list2 = str2.split(regexTokenize);

        // --- Create the matrix of weights
        int n = list1.length;
        int m = list2.length;
        int maxDim = Math.max(n, m);
        int minDim = Math.min(n, m);

        double weight = 0;
        for (int i = 0; i < minDim; i++) {
            if (initialComparison) {
                double w = getInitialSimilarity(list1[i], list2[i]);
                if (w != -1) {
                    weight += w;
                } else {
                    weight += getWeight(list1[i], list2[i]);
                }
            } else {
                weight += getWeight(list1[i], list2[i]);
            }
        }
        weight /= maxDim;
        return weight;
    }

    // TODO Can we improve this method ? Especially the recursive call
    private double computeWeightTokenSameOrder(String str1, String str2) {
        // --- Compute the lists of tokens
        String[] list1 = str1.split(regexTokenize);
        String[] list2 = str2.split(regexTokenize);

        // --- Create the matrix of weights
        int n = list1.length;
        int m = list2.length;
        String[] shortString, longString;
        int maxDim, minDim;
        if (n < m) {
            shortString = list1;
            longString = list2;
            minDim = n;
            maxDim = m;
        } else {
            shortString = list2;
            longString = list1;
            minDim = m;
            maxDim = n;
        }

        // Loop on all the combination of minDim out of maxDim
        bestWeightSameOrder = 0;
        combinations(longString, minDim, 0, new String[minDim], shortString);
        bestWeightSameOrder /= maxDim;
        return bestWeightSameOrder;
    }

    private void combinations(String[] longString, int len, int startPosition, String[] result, String[] shortString) {
        if (len == 0) {
            // Compute weight:
            double weight = 0;
            for (int i = 0; i < shortString.length; i++) {
                if (initialComparison) {
                    double w = getInitialSimilarity(result[i], shortString[i]);
                    if (w != -1) {
                        weight += w;
                    } else {
                        weight += getWeight(result[i], shortString[i]);
                    }
                } else {
                    weight += getWeight(result[i], shortString[i]);
                }
            }
            if (bestWeightSameOrder < weight)
                bestWeightSameOrder = weight;
            return;
        }
        for (int i = startPosition; i <= longString.length - len; i++) {
            result[result.length - len] = longString[i];
            combinations(longString, len - 1, i + 1, result, shortString);
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str); //$NON-NLS-1$
    }

    /**
     * Calculate matching weight using specified matcher.
     * 
     * @param record1 the first string
     * @param record2 the second string
     * @return result between 0 and 1
     */
    protected abstract double getWeight(String record1, String record2);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.dataquality.record.linkage.attribute.IAttributeMatcher#setNullOption(org.talend.dataquality.record
     * .linkage.attribute.IAttributeMatcher.NullOption)
     */
    @Override
    public void setNullOption(NullOption option) {
        this.nullOption = option;
    }

    @Override
    public NullOption getNullOption() {
        return nullOption;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.attribute.IAttributeMatcher#getAttributeName()
     */
    @Override
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * Getter for fingerPrintApply.
     * 
     * @return the fingerPrintApply
     */
    public boolean isFingerPrintApply() {
        return fingerPrintApply;
    }

    /**
     * Getter for tokenMethod.
     * 
     * @return the tokenMethod
     */
    public TokenizedResolutionMethod getTokenMethod() {
        return tokenMethod;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.attribute.IAttributeMatcher#setAttributeName(java.lang.String)
     */
    @Override
    public void setAttributeName(String name) {
        this.attributeName = name;
    }

    /*
     * (non-Javadoc)
     */
    public void setTokenize(boolean tokenize) {
        this.tokenize = tokenize;
    }

    /**
     * Getter for tokenize.
     * 
     * @return the tokenize
     */
    public boolean isTokenize() {
        return tokenize;
    }

    /**
     * Getter for initialComparison.
     * 
     * @return the initialComparison
     */
    public boolean isInitialComparison() {
        return initialComparison;
    }

    /**
     * Sets the tokenMethod.
     * 
     * @param tokenMethod the tokenMethod to set
     */
    public void setTokenMethod(TokenizedResolutionMethod tokenMethod) {
        this.tokenMethod = tokenMethod;
    }

    /**
     * Sets the initialComparison.
     * 
     * @param initialComparison the initialComparison to set
     */
    public void setInitialComparison(boolean initialComparison) {
        this.initialComparison = initialComparison;
    }

    /**
     * Sets the regexTokenize.
     * 
     * @param regexTokenize the regexTokenize to set
     */
    public void setRegexTokenize(String regexTokenize) {
        this.regexTokenize = regexTokenize;
    }

    /**
     * Sets the fingerPrintApply.
     * 
     * @param fingerPrintApply the fingerPrintApply to set
     */
    public void setFingerPrintApply(boolean fingerPrintApply) {
        this.fingerPrintApply = fingerPrintApply;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.attribute.IAttributeMatcher#setNullOption(java.lang.String)
     */
    @Override
    public void setNullOption(String option) {
        if (IAttributeMatcher.NullOption.nullMatchAll.name().equalsIgnoreCase(option)) {
            this.nullOption = IAttributeMatcher.NullOption.nullMatchAll;
        } else if (IAttributeMatcher.NullOption.nullMatchNone.name().equalsIgnoreCase(option)) {
            this.nullOption = IAttributeMatcher.NullOption.nullMatchNone;
        } else if (IAttributeMatcher.NullOption.nullMatchNull.name().equalsIgnoreCase(option)) {
            this.nullOption = IAttributeMatcher.NullOption.nullMatchNull;
        } else {
            this.nullOption = IAttributeMatcher.NullOption.nullMatchNull;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.attribute.IAttributeMatcher#isDummyMatcher()
     */
    @Deprecated
    @Override
    public boolean isDummyMatcher() {
        return false;
    }

}
