package org.yang.localtools.verify;

/**
 * 数组验证
 */
public class ArrayVerify {

    /**
     * 验证数组是否为空
     * @param array 数组
     * @param <T> 数组元素类型
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array){
        return array == null || array.length == 0;
    }

}
