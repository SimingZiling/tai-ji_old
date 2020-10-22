package org.yang.localtools.util;

/**
 * 长整形扩展功能
 */
public class LongUtil {

    private LongUtil(){}

    /**
     * 验证是否为空
     * @param lon 长整型
     * @return true 为空 false 不为空
     */
    public static boolean isNull(Long lon){
        return lon == null || lon.equals(0L);
    }

}
