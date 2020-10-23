package org.yang.localtools.util;

/**
 * 字符串扩展功能
 */
public class StringUtil{

    private StringUtil(){}

    /**
     * 首字母小写
     * @param str 字符串
     * @return 首字母小写后的字符串
     */
    public static String initialLowercase(String str){
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 验证是否为空
     * @param str 字符串
     * @return true 为空 false 不为空
     */
    public static boolean isNull(String str){
        return str == null || str.equals("");
    }

}