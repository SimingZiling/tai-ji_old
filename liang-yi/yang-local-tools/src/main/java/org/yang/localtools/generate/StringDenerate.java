package org.yang.localtools.generate;

/**
 * String转换
 */
public class StringDenerate {

    /**
     * 禁止创建StringDenerate对象
     */
    protected StringDenerate() {}

    /**
     * 首字母小写
     * @param str 字符串
     * @return 首字母小写后的字符串
     */
    public static String initialLowercase(String str){
        char[] chars = str.toCharArray();
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            chars[0] = (char) (chars[0] + 32);
        }
        return String.valueOf(chars);
    }

    /**
     * 首字母大写
     * @param str 字符串
     * @return 首字母小写后的字符串
     */
    public static String initialCapital(String str){
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char) (chars[0] - 32);
        }
        return String.valueOf(chars);
    }

}
