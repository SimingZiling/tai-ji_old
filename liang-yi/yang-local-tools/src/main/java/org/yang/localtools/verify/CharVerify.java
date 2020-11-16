package org.yang.localtools.verify;

/**
 * 字符验证
 */
public class CharVerify {

    /**
     * 判断是否为空白字符
     * @param c 字符
     * @return 是否为空白字符
     */
    public static boolean isBlank(char c){
        return isBlank((int)c);
    }

    /**
     * 判断是否为空白字符
     * @param ascii ascii码
     * @return 是否为空白字符
     */
    public static boolean isBlank(int ascii){
        return Character.isWhitespace(ascii)
                || Character.isSpaceChar(ascii)
                || ascii == '\ufeff'
                || ascii == '\u202a';
    }

}
