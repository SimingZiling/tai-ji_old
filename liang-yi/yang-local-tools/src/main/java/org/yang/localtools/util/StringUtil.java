package org.yang.localtools.util;


import java.nio.ByteBuffer;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串扩展功能
 */
public class StringUtil{

    private StringUtil(){}

    /**
     * 字符串常量：点 {@code "."}
     */
    public static final String DOT = ".";

    /**
     * 字符串常量：{@code "null"} <br>
     * 注意：{@code "null" != null}
     */
    public static final String NULL = "null";

    /**
     * 字符串常量：空 JSON <code>"{}"</code>
     */
    public static final String EMPTY_JSON = "{}";

    /**
     * 字符常量：反斜杠 {@code '\\'}
     */
    public static final char C_BACKSLASH = CharUtil.BACKSLASH;

    /**
     * 字符常量：花括号（左） <code>'{'</code>
     */
    public static final char C_DELIM_START = CharUtil.DELIM_START;
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

    /**
     * 验证是否为空
     * @param str 字符串
     * @return true 为空 false 不为空
     *  // TODO 通过时方法 后续会删除
     */
    @Deprecated
    public static boolean isNull(String str){
        return str == null || str.equals("");
    }


    /**
     * 检测字符串是否为空白字符串
     * 定义：空字符串，空格、制表符、换行符等不见字符均为空白字符串
     * @param str 检测的字符串
     * @return 若为空白则返回 true
     */
    public static boolean isBlank(String str){
        // 定义长度
        int length;

        // 如果字符串为空或者长度为0时，返回为空
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++){
            // 如果有非空白字符则表示为非空字符串
            if(CharUtil.isBlank(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 检测对象是否为空白字符串
     * 定义：空字符串，空格、制表符、换行符等不见字符均为空白字符串
     * @param object 对象
     * @return 是否为空白字符串
     */
    public static boolean isBlank(Object object){
        if (object == null){
            return true;
        }else
            // 判断是否为字符串，如果是则转换为字符串进行判断
            if(object instanceof  String){
            return isBlank(String.valueOf(object));
        }
        return false;
    }

    /**
     * 验证字符串或者字符字符串数组是否包含空白字符串
     * @param strs 字符串
     * @return 是否包含空白字符串
     */
    public static boolean hasBlank(String... strs){
        // 判断数组元素是否为空,数组元素为空时 字符串为空
        if (ArrayUtil.isEmpty(strs)) {
            return true;
        }
        // 遍历数组，数组不为空时返回
        for (String str : strs) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证字符串或字符串数组是否全部为空白
     * @param strs 字符串或字符串数组
     * @return 是否所有字符串都为空
     */
    public static boolean isAllBlank(String... strs){
        // 判断数组元素是否为空,数组元素为空时 字符串为空白字符串
        if (ArrayUtil.isEmpty(strs)) {
            return true;
        }
        // 遍历数组
        for (String str : strs) {
            // 如果字符串不为空是则返回false
            if (!isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证字符串是否为空
     * 定义：空字符串，空格、制表符、换行符等不见字符均不为空白字符串
     * @param str 字符串
     * @return 是否为空字符串
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串对象是否为空
     * @param object 对象
     * @return 是否为空字符串
     */
    public static boolean isEmpty(Object object){
        if(object == null){
            return true;
        }else
            // 判断对象是否为字符串
            if(object instanceof String){
                return 0 == ((String) object).length();
        }
        return false;
    }

    /**
     * 判断字符串或字符串数组是否包含空字符串
     * @param strs 对象
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(String... strs){
        if (ArrayUtil.isEmpty(strs)) {
            return true;
        }

        for (String str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串或字符串数组是否全部为空字符串
     * @param strs 字符串
     * @return 是否全部为空字符串
     */
    public static boolean isAllEmpty(String... strs) {
        if (ArrayUtil.isEmpty(strs)) {
            return true;
        }

        for (String str : strs) {
            if (!isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 给定字符串是否被字符包围
     * @param str    字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 是否包围，空串不包围
     */
    public static boolean isSurround(String str, char prefix, char suffix) {
        if (isBlank(str)) {
            return false;
        }
        if (str.length() < 2) {
            return false;
        }
        return str.charAt(0) == prefix && str.charAt(str.length() - 1) == suffix;
    }


    /**
     * 获取被包含的字符串
     * @param includedStr 被包含的字符串（被检测的字符串）
     * @param strs 被测试是否包含的字符串或字符串数组
     * @param contains 是否忽略大小写
     * @return 返回包含的字符串列表
     */
    public static List<String> getContainsStr(boolean contains,String includedStr, String... strs){
        List<String> strings = new ArrayList<>();
        if (isEmpty(includedStr) || ArrayUtil.isEmpty(strs)) {
            return strings;
        }
        for (String str : strs){
            if(includeStr(includedStr,str,contains)){
                strings.add(str);
            }
        }
        return strings;
    }

    /**
     * 检测字符串是否包含指定字符串
     * @param includedStr 被包含的字符串（被检测的字符串）
     * @param str  被测试是否包含的字符串
     * @param contains 是否忽略大小写
     * @return 是否包含指定字符串
     */
    public static boolean includeStr(String includedStr,String str,boolean contains){
        if(includedStr == null){
            return str == null;
        }
        if(contains){
            return includedStr.toLowerCase().contains(str.toLowerCase());
        }
        else {
            return includedStr.contains(str);
        }
    }


}
