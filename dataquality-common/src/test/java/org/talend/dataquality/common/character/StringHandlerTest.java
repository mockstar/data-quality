package org.talend.dataquality.common.character;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringHandlerTest {

    private static final String QUO_STR = "\""; //$NON-NLS-1$

    private static final String SPACE_STR = " "; //$NON-NLS-1$

    private static final String NULL_STR = "null"; //$NON-NLS-1$

    private static final String BLANK_STR = ""; //$NON-NLS-1$

    private static final String TEST_STR = "test"; //$NON-NLS-1$

    private final String MIXTD_SURROGATEPAIR = "𠀀𠀐我𠀑ab"; //$NON-NLS-1$

    private static final String JAPANESE1_STR = "リンゴ"; //$NON-NLS-1$

    private static final String JAPANESE2_STR = "リンゴを食べる"; //$NON-NLS-1$

    private static final String JAPANESE3_STR = "リンゴ を 食べる"; //$NON-NLS-1$

    /**
     * Test method for {@link StringHandler#addPrefix(String, String)}
     */
    @Test
    public void testAddPrefix() {
        assertEquals("<test", StringHandler.addPrefix(TEST_STR, "<")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(TEST_STR, StringHandler.addPrefix(TEST_STR, null));
        assertEquals(NULL_STR, StringHandler.addPrefix(NULL_STR, BLANK_STR));
        assertEquals("\\test", StringHandler.addPrefix(TEST_STR, "\\")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(QUO_STR, StringHandler.addPrefix(null, QUO_STR));
        assertEquals("<リンゴ", StringHandler.addPrefix(JAPANESE1_STR, "<")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test method for {@link StringHandler#concatenate(String, String)}
     */
    @Test
    public void testConcatenate() {
        assertEquals("test<", StringHandler.concatenate(TEST_STR, "<")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(TEST_STR, StringHandler.concatenate(TEST_STR, null));
        assertEquals(NULL_STR, StringHandler.concatenate(NULL_STR, BLANK_STR));
        assertEquals("test\\", StringHandler.concatenate(TEST_STR, "\\")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(QUO_STR, StringHandler.concatenate(null, QUO_STR));
        assertEquals("リンゴ<", StringHandler.concatenate(JAPANESE1_STR, "<")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test method for {@link StringHandler#exact(String)}
     */
    @Test
    public void testExact() {
        assertEquals(BLANK_STR, StringHandler.exact(null));
        assertEquals(TEST_STR, StringHandler.exact(TEST_STR));
        assertEquals(BLANK_STR, StringHandler.exact(BLANK_STR));
        assertEquals(NULL_STR, StringHandler.exact(NULL_STR));
        assertEquals(QUO_STR, StringHandler.exact(QUO_STR));
        assertEquals(JAPANESE1_STR, StringHandler.exact(JAPANESE1_STR));
    }

    /**
     * Test method for {@link StringHandler#firstCharEW(String)}
     */
    @Test
    public void testFirstCharEW() {
        assertEquals(BLANK_STR, StringHandler.firstCharEW(null));
        assertEquals("t", StringHandler.firstCharEW(TEST_STR)); //$NON-NLS-1$
        assertEquals(BLANK_STR, StringHandler.firstCharEW(BLANK_STR));
        assertEquals("n", StringHandler.firstCharEW(NULL_STR)); //$NON-NLS-1$
        assertEquals(QUO_STR, StringHandler.firstCharEW(QUO_STR));
        // TDQ-15079: Support Chinese　and surrogate pair characters
        assertEquals("拓", StringHandler.firstCharEW("拓蓝科技")); //$NON-NLS-1$//$NON-NLS-2$
        assertEquals("𠀀", StringHandler.firstCharEW(MIXTD_SURROGATEPAIR)); //$NON-NLS-1$
        assertEquals("リ", StringHandler.firstCharEW(JAPANESE1_STR)); //$NON-NLS-1$
        assertEquals("リ", StringHandler.firstCharEW(JAPANESE2_STR)); //$NON-NLS-1$
        assertEquals("リを食", StringHandler.firstCharEW(JAPANESE3_STR)); //$NON-NLS-1$
    }

    /**
     * Test method for {@link StringHandler#firstNChar(String, int)}
     */
    @Test
    public void testFirstNChar() {
        assertEquals(BLANK_STR, StringHandler.firstNChar(null, 1));
        assertEquals(TEST_STR, StringHandler.firstNChar(TEST_STR, 4));
        assertEquals(TEST_STR, StringHandler.firstNChar(TEST_STR, 5));
        assertEquals(TEST_STR, StringHandler.firstNChar(TEST_STR, -1));
        assertEquals(BLANK_STR, StringHandler.firstNChar(BLANK_STR, 1));
        assertEquals("n", StringHandler.firstNChar(NULL_STR, 1)); //$NON-NLS-1$
        assertEquals(QUO_STR, StringHandler.firstNChar(QUO_STR, 1));
        assertEquals("   ", StringHandler.firstNChar("   abc", 3)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(MIXTD_SURROGATEPAIR, StringHandler.firstNChar(MIXTD_SURROGATEPAIR, 10));
        assertEquals("𠀀𠀐", StringHandler.firstNChar(MIXTD_SURROGATEPAIR, 2)); //$NON-NLS-1$
        assertEquals("𠀀𠀐我𠀑a", StringHandler.firstNChar(MIXTD_SURROGATEPAIR, 5)); //$NON-NLS-1$
        assertEquals("リ", StringHandler.firstNChar(JAPANESE1_STR, 1)); //$NON-NLS-1$
        assertEquals("リン", StringHandler.firstNChar(JAPANESE2_STR, 2)); //$NON-NLS-1$
        assertEquals("リンゴ を", StringHandler.firstNChar(JAPANESE3_STR, 5)); //$NON-NLS-1$
    }

    /**
     * Test method for {@link StringHandler#firstNCharEW(String, int)}
     */
    @Test
    public void testFirstNCharEW() {
        assertEquals(BLANK_STR, StringHandler.firstNCharEW(null, 1));
        assertEquals(TEST_STR, StringHandler.firstNCharEW(TEST_STR, 4));
        assertEquals(TEST_STR, StringHandler.firstNCharEW(TEST_STR, 5));
        assertEquals("tt", StringHandler.firstNCharEW("test\ntest", 1)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("tete", StringHandler.firstNCharEW("test\ttest", 2)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("testes", StringHandler.firstNCharEW("test\ftest", 3)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("tete", StringHandler.firstNCharEW("test test", 2)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("testtest", StringHandler.firstNCharEW("test\rtest", 4)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(BLANK_STR, StringHandler.firstNCharEW(BLANK_STR, 1));
        assertEquals("n", StringHandler.firstNCharEW(NULL_STR, 1)); //$NON-NLS-1$
        assertEquals(QUO_STR, StringHandler.firstNCharEW(QUO_STR, 1));
        assertEquals("ab", StringHandler.firstNCharEW("   abc", 2)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("a", StringHandler.firstNCharEW("   abc", 1)); //$NON-NLS-1$ //$NON-NLS-2$
        // TDQ-15079: Support Chinese　and surrogate pair characters
        assertEquals("拓科", StringHandler.firstNCharEW("拓蓝\r科技", 1)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("𠀀𠀐𠀑a", StringHandler.firstNCharEW("𠀀𠀐我\t𠀑ab", 2)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("リ", StringHandler.firstNCharEW(JAPANESE1_STR, 1)); //$NON-NLS-1$
        assertEquals("リン", StringHandler.firstNCharEW(JAPANESE2_STR, 2)); //$NON-NLS-1$
        assertEquals("リンを食べ", StringHandler.firstNCharEW(JAPANESE3_STR, 2)); //$NON-NLS-1$
    }

    /**
     * Test method for {@link StringHandler#firstNConsonants(String, int)}
     */
    @Test
    public void testFirstNConsonants() {
        assertEquals(BLANK_STR, StringHandler.firstNConsonants(null, 1));
        assertEquals("tst", StringHandler.firstNConsonants(TEST_STR, 2000)); //$NON-NLS-1$
        assertEquals("t", StringHandler.firstNConsonants("test\ntest", 1)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("ts", StringHandler.firstNConsonants("test\ttest", 2)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("tstts", StringHandler.firstNConsonants("test test", 5)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(BLANK_STR, StringHandler.firstNConsonants(BLANK_STR, 1));
        assertEquals(BLANK_STR, StringHandler.firstNConsonants(SPACE_STR, 1));
        assertEquals("n", StringHandler.firstNConsonants(NULL_STR, 1)); //$NON-NLS-1$
        assertEquals(BLANK_STR, StringHandler.firstNConsonants(JAPANESE1_STR, 1));
    }

    /**
     * Test method for {@link StringHandler#firstNVowels(String, int)}
     */
    @Test
    public void testFirstNVowels() {
        assertEquals(BLANK_STR, StringHandler.firstNVowels(null, 1));
        assertEquals("e", StringHandler.firstNVowels(TEST_STR, 2000)); //$NON-NLS-1$
        assertEquals("e", StringHandler.firstNVowels("test\ntest", 1)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("ee", StringHandler.firstNVowels("test\ttest", 2)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("ee", StringHandler.firstNVowels("test test", 5)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(BLANK_STR, StringHandler.firstNVowels(BLANK_STR, 1));
        assertEquals(BLANK_STR, StringHandler.firstNVowels(SPACE_STR, 1));
        assertEquals("u", StringHandler.firstNVowels(NULL_STR, 1)); //$NON-NLS-1$
        assertEquals(BLANK_STR, StringHandler.firstNVowels(QUO_STR, 1));
        assertEquals(BLANK_STR, StringHandler.firstNVowels(JAPANESE1_STR, 1));
    }

    /**
     * Test method for {@link StringHandler#lastNChar(String, int)}
     */
    @Test
    public void testLastNChar() {
        assertEquals(BLANK_STR, StringHandler.lastNChar(null, 1));
        assertEquals("test", StringHandler.lastNChar(TEST_STR, 2000)); //$NON-NLS-1$
        assertEquals("t", StringHandler.lastNChar("test\ntest", 1)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("st", StringHandler.lastNChar("test\ttest", 2)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(" test", StringHandler.lastNChar("test test", 5)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(BLANK_STR, StringHandler.lastNChar(BLANK_STR, 1));
        assertEquals(SPACE_STR, StringHandler.lastNChar(SPACE_STR, 1));
        assertEquals("l", StringHandler.lastNChar(NULL_STR, 1)); //$NON-NLS-1$
        assertEquals(QUO_STR, StringHandler.lastNChar(QUO_STR, 1));
        assertEquals(QUO_STR, StringHandler.lastNChar(QUO_STR, 1));
        // TDQ-15079: Support Chinese　and surrogate pair characters
        assertEquals(MIXTD_SURROGATEPAIR, StringHandler.lastNChar(MIXTD_SURROGATEPAIR, 100));
        assertEquals("𠀑ab", StringHandler.lastNChar(MIXTD_SURROGATEPAIR, 3)); //$NON-NLS-1$ 
        assertEquals("𠀐我𠀑ab", StringHandler.lastNChar(MIXTD_SURROGATEPAIR, 5)); //$NON-NLS-1$ 
        assertEquals("ゴ", StringHandler.lastNChar(JAPANESE1_STR, 1)); //$NON-NLS-1$
        assertEquals("べる", StringHandler.lastNChar(JAPANESE2_STR, 2)); //$NON-NLS-1$
        assertEquals(" 食べる", StringHandler.lastNChar(JAPANESE3_STR, 4)); //$NON-NLS-1$
    }

    /**
     * Test method for {@link StringHandler#lowerCase(String)}
     */
    @Test
    public void testLowerCase() {
        assertNull(StringHandler.lowerCase(null));
        assertEquals("test", StringHandler.lowerCase(TEST_STR)); //$NON-NLS-1$
        assertEquals("test\ntest", StringHandler.lowerCase("Test\ntest")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("test\ttest", StringHandler.lowerCase("Test\ttest")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("test test123", StringHandler.lowerCase("Test test123")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(BLANK_STR, StringHandler.lowerCase(BLANK_STR));
        assertEquals(SPACE_STR, StringHandler.lowerCase(SPACE_STR));
        assertEquals(NULL_STR, StringHandler.lowerCase("Null")); //$NON-NLS-1$
        assertEquals(QUO_STR, StringHandler.lowerCase(QUO_STR));
        assertEquals(JAPANESE1_STR, StringHandler.lowerCase(JAPANESE1_STR));
    }

    /**
     * Test method for {@link StringHandler#pickChar(String, String)}
     */
    @Test
    public void testPickChar() {
        assertEquals(BLANK_STR, StringHandler.pickChar(TEST_STR, BLANK_STR));
        assertEquals(BLANK_STR, StringHandler.pickChar(TEST_STR, null));
        assertEquals(BLANK_STR, StringHandler.pickChar(BLANK_STR, "test")); //$NON-NLS-1$
        assertEquals(BLANK_STR, StringHandler.pickChar(null, "test")); //$NON-NLS-1$
        assertEquals(BLANK_STR, StringHandler.pickChar(TEST_STR, "test")); //$NON-NLS-1$
        assertEquals(BLANK_STR, StringHandler.pickChar(TEST_STR, "tes")); //$NON-NLS-1$
        assertEquals("bcdf", StringHandler.pickChar("abcdef", "1;2-4;5")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        assertEquals("etest", StringHandler.pickChar(TEST_STR, "1-2;40;0-5")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("e", StringHandler.pickChar("Test test", "1-2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        assertEquals(BLANK_STR, StringHandler.pickChar(SPACE_STR, BLANK_STR));
        assertEquals(BLANK_STR, StringHandler.pickChar(TEST_STR, SPACE_STR));
        assertEquals(BLANK_STR, StringHandler.pickChar(QUO_STR, "1")); //$NON-NLS-1$
        // TDQ-15079: Support Chinese　and surrogate pair characters
        assertEquals(BLANK_STR, StringHandler.pickChar(MIXTD_SURROGATEPAIR, BLANK_STR));
        assertEquals("𠀐", StringHandler.pickChar(MIXTD_SURROGATEPAIR, "1")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("我", StringHandler.pickChar(MIXTD_SURROGATEPAIR, "2")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("𠀑", StringHandler.pickChar(MIXTD_SURROGATEPAIR, "3")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("a", StringHandler.pickChar(MIXTD_SURROGATEPAIR, "4")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("ン", StringHandler.pickChar(JAPANESE1_STR, "1")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(BLANK_STR, StringHandler.pickChar(JAPANESE1_STR, "リ")); //$NON-NLS-1$ //$NON-NLS-2$

        System.out.println("Picked : " + StringHandler.pickChar(TEST_STR, "3-1")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test method for {@link StringHandler#removeDiacriticalMarks(String)}
     */
    @Test
    public void testRemoveDiacriticalMarks() {
        assertNull(StringHandler.removeDiacriticalMarks(null));
        assertEquals(TEST_STR, StringHandler.removeDiacriticalMarks(TEST_STR));
        assertEquals(BLANK_STR, StringHandler.removeDiacriticalMarks(BLANK_STR));
        assertEquals("1-2;40;0-5", StringHandler.removeDiacriticalMarks("1-2;40;0-5")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("Test test", StringHandler.removeDiacriticalMarks("Test test")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(SPACE_STR, StringHandler.removeDiacriticalMarks(SPACE_STR));
        assertEquals(QUO_STR, StringHandler.removeDiacriticalMarks(QUO_STR));
        assertEquals(JAPANESE1_STR, StringHandler.removeDiacriticalMarks(JAPANESE1_STR));
    }

    /**
     * Test method for {@link StringHandler#removeDMAndLowerCase(String)}
     */
    @Test
    public void testRemoveDMAndLowerCase() {
        assertNull(StringHandler.removeDMAndLowerCase(null));
        assertEquals(TEST_STR, StringHandler.removeDMAndLowerCase(TEST_STR));
        assertEquals(BLANK_STR, StringHandler.removeDMAndLowerCase(BLANK_STR));
        assertEquals("testdtestm", StringHandler.removeDMAndLowerCase("TestDtestM")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("test test12", StringHandler.removeDMAndLowerCase("Test test12")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(SPACE_STR, StringHandler.removeDMAndLowerCase(SPACE_STR));
        assertEquals(QUO_STR, StringHandler.removeDMAndLowerCase(QUO_STR));
        assertEquals(JAPANESE1_STR, StringHandler.removeDMAndLowerCase(JAPANESE1_STR));
    }

    /**
     * Test method for {@link StringHandler#removeDMAndUpperCase(String)}
     */
    @Test
    public void testRemoveDMAndUpperCase() {
        assertNull(StringHandler.removeDMAndUpperCase(null));
        assertEquals("TEST", StringHandler.removeDMAndUpperCase(TEST_STR)); //$NON-NLS-1$
        assertEquals(BLANK_STR, StringHandler.removeDMAndUpperCase(BLANK_STR));
        assertEquals("TESTDTESTM1-2;40;0-5", StringHandler.removeDMAndUpperCase("TestDtestM1-2;40;0-5")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("TESTDTESTMTEST TEST", StringHandler.removeDMAndUpperCase("TestDtestMTest test")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(SPACE_STR, StringHandler.removeDMAndUpperCase(SPACE_STR));
        assertEquals(QUO_STR, StringHandler.removeDMAndUpperCase(QUO_STR));
        assertEquals(JAPANESE1_STR, StringHandler.removeDMAndUpperCase(JAPANESE1_STR));
    }

    /**
     * Test method for {@link StringHandler#subStr(String, String)}
     */
    @Test
    public void testSubStr() {
        assertEquals(BLANK_STR, StringHandler.subStr(TEST_STR, BLANK_STR));
        assertEquals(BLANK_STR, StringHandler.subStr(TEST_STR, null));
        assertEquals(BLANK_STR, StringHandler.subStr(BLANK_STR, TEST_STR));
        assertEquals(BLANK_STR, StringHandler.subStr(null, TEST_STR));
        assertEquals(BLANK_STR, StringHandler.subStr(TEST_STR, SPACE_STR));
        assertEquals("est", StringHandler.subStr(TEST_STR, "1;100")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("cd", StringHandler.subStr("abcdef", "2;4")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        assertEquals("𠀐我", StringHandler.subStr(MIXTD_SURROGATEPAIR, "1;3")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("我𠀑a", StringHandler.subStr(MIXTD_SURROGATEPAIR, "2;5")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("ゴを", StringHandler.subStr(JAPANESE2_STR, "2;4")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Test method for {@link StringHandler#upperCase(String)}
     */
    @Test
    public void testUpperCase() {
        assertNull(StringHandler.upperCase(null));
        assertEquals("TEST", StringHandler.upperCase(TEST_STR)); //$NON-NLS-1$
        assertEquals("TEST\nTEST", StringHandler.upperCase("Test\ntest")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("TEST\tTEST", StringHandler.upperCase("Test\ttest")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals("TEST TEST12", StringHandler.upperCase("Test test12")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(BLANK_STR, StringHandler.upperCase(BLANK_STR));
        assertEquals(SPACE_STR, StringHandler.upperCase(SPACE_STR));
        assertEquals("NULL ", StringHandler.upperCase("Null ")); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(QUO_STR, StringHandler.upperCase(QUO_STR));
        assertEquals(JAPANESE1_STR, StringHandler.upperCase(JAPANESE1_STR));
    }

    /**
     * Test method for {@link StringHandler#useDefault(String, String)}
     */
    @Test
    public void testUseDefault() {
        assertEquals(TEST_STR, StringHandler.useDefault(BLANK_STR, TEST_STR));
        assertEquals(SPACE_STR, StringHandler.useDefault(SPACE_STR, BLANK_STR));
        assertEquals(TEST_STR, StringHandler.useDefault(null, TEST_STR));
        assertEquals(TEST_STR, StringHandler.useDefault(TEST_STR, SPACE_STR));
        assertEquals("Test test12", StringHandler.useDefault("Test test12", NULL_STR)); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals(JAPANESE1_STR, StringHandler.useDefault(JAPANESE1_STR, JAPANESE2_STR));
    }
}