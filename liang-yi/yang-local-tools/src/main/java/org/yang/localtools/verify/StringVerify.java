package org.yang.localtools.verify;

import java.lang.reflect.Array;

/**
 * 字符串验证
 */
public class StringVerify {

    /**
     * 禁止生成StringVerify对象
     */
    protected StringVerify(){}

    /**
     * 验证是否为空白字符串
     * 空白字符串定义：显示为空白的均为空白字符串，例如：空格、制表符、换行符等
     * @param str 字符串
     * @return 是否为空白字符串
     */
    public static boolean isBlank(String str){
        // 定义长度，在后续验证字符时使用
        int length;

        if(str == null || (length = str.length()) == 0){
            return true;
        }

        // 准备遍历字符串
        for (int i = 0 ; i < length ; i++){
            // 如果有非空白字符则表示该字符串为非空白字符串
            if(!CharVerify.isBlank(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 验证对象是否为空白字符串需要时String字符串
     * 空白字符串定义：显示为空白的均为空白字符串，例如：空格、制表符、换行符等
     * @param object 对象
     * @return 是否为空白字符串
     */
    public static boolean isBlank(Object object){
      if(object == null){
          return true;
      }else
          // 判断对象是否为字符串，如果不是则返回false
          if(object instanceof String){
              // 将对象转换为字符串
              return isBlank(String.valueOf(object));
          }
          return false;
    }

    /**
     * 验证字符串或字符串列表包含空白字符串
     * @param strs 字符串列表
     * @return 是否包含空白字符串
     */
    public static boolean hasBlank(String... strs){
        // 判断数组元素是否为空
        if(ArrayVerify.isEmpty(strs)){
            return true;
        }
        // 遍历数组，数组不为空时返回包包含空字符串
        for (String str : strs){
            if(isBlank(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * 验证字符串或字符串列表是否全部为空白
     * @param strs 字符串列表
     * @return 是否全部为空白
     */
    public static boolean isAllBlank(String... strs){
        // 判断数组元素是否为空
        if(ArrayVerify.isEmpty(strs)){
            return true;
        }
        // 遍历数组，数组不为空时返回包包含空字符串
        for (String str : strs){
            if(!isBlank(str)){
                return false;
            }
        }
        return true;
    }

    /**
     * 验证字符串是否为空
     * 为空定义：包含空格、制表符、换行符均不为空
     * @param str 字符串
     * @return 是否为空字符串
     */
    public static boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }

    /**
     * 验证字符串对象是否为空
     * 为空定义：包含空格、制表符、换行符均不为空
     * @param object 对象
     * @return 是否为空字符串
     */
    public static boolean isEmpty(Object object){
        if(object == null){
            return true;
        }else if(object instanceof String){
            return isEmpty(String.valueOf(object));
        }else {
            return true;
        }
    }

    /**
     * 验证字符串或字符串列表包含空字符串
     * @param strs 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(String... strs){
        if(ArrayVerify.isEmpty(strs)){
            return true;
        }
        for (String str: strs){
            if(isEmpty(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * 验证字符串或字符串列表全部为空字符串
     * @param strs 字符串列表
     * @return 是否全部为空字符串
     */
    public static boolean isAllEmpty(String... strs){
        if(ArrayVerify.isEmpty(strs)){
            return true;
        }
        for (String str : strs){
            // 当包含非空字符串时，为非空
            if(!isEmpty(str)){
                return false;
            }
        }
        return true;
    }

}
