package org.yang.localtools.util;

/**
 * 浮点型扩展功能
 */
public class FloatUtil {

    private FloatUtil(){}

    /**
     * 验证是否为空
     * @param flo 浮点型
     * @return true 为空 false 不为空
     */
    public static boolean isNull(Float flo){
        return flo == null || flo.equals(0F);
    }

}
