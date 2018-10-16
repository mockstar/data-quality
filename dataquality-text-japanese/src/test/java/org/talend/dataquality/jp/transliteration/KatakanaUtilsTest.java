// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.jp.transliteration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KatakanaUtilsTest {

    @Test
    public void toFullWidthTest() {
        assertEquals("ツイッター", KatakanaUtils.toFullWidth("ﾂｲｯﾀｰ"));
        assertEquals("シュワルツェネッガー", KatakanaUtils.toFullWidth("ｼｭﾜﾙﾂｪﾈｯｶﾞｰ"));
        assertEquals("パパ", KatakanaUtils.toFullWidth("ﾊﾟﾊﾟ"));
        assertEquals("ピャニッチ", KatakanaUtils.toFullWidth("ﾋﾟｬﾆｯﾁ"));
        assertEquals("フィジカル", KatakanaUtils.toFullWidth("ﾌｨｼﾞｶﾙ"));
    }
}