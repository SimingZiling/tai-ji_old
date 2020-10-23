package org.yang.localtools.util;

/**
 * 整形扩展功能
 */
public class IntegerUtil {

    private IntegerUtil(){}

    /**
     * 验证是否为空
     * @param inte 整形
     * @return true 为空 false 不为空
     */
    public static boolean isNull(Integer inte){
        return inte == null || inte.equals(0);
    }

}
