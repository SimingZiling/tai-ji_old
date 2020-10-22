package org.yang.localtools.random;

/**
 * 随机值
 */
public class RandomValue {

    /**
     * 通过最大数和最小数获取长整型随机数
     * @param max 最大数
     * @param min 最小数
     * @return 随机数
     */
    public static long randomLongByMaxAndMin(long max,long min){
        return (long) ((Math.random() * (max - min)) + min);
    }


}
