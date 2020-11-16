package org.yang.localtools.util;

public class CharUtil {

    /** 字符常量：花括号（左） <code>'{'</code> */
    public static final char DELIM_START = '{';
    /** 字符常量：花括号（右） <code>'}'</code> */
    public static final char DELIM_END = '}';
    /** 字符常量：反斜杠 {@code '\\'} */
    public static final char BACKSLASH = '\\';

    /**
     * 是否为空白字符
     * 定义：空白符包括空格、制表符、全角空格和不间断空格
     * @param c 检测字符
     * @return 空白字符
     */
    public static boolean isBlank(char c) {
        return isBlank((int) c);
    }

    /**
     * 是否为空白字符
     * @param c 检测字符
     * @return 是否为空白字符
     */
    public static boolean isBlank(int c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a';
    }
}
