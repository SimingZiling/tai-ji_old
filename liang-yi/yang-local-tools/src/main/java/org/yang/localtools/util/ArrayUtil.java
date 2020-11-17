package org.yang.localtools.util;

import org.yang.localtools.verify.ArrayVerify;

import java.util.Arrays;

/**
 * 数组工具集
 */
public class ArrayUtil {

    /**
     * 数组或集合转String
     * @param object 集合或数组对象
     * @return 数组字符串，与集合转字符串格式相同
     */
    public static String arrayToString(Object object) {
        if (null == object) {
            return null;
        }

        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        } else if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        } else if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        } else if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        } else if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        } else if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        } else if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        } else if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        } else if (ArrayVerify.isArray(object)) {
            // 对象数组
            try {
                return Arrays.deepToString((Object[]) object);
            } catch (Exception ignore) {
            }
        }

        return object.toString();
    }
}
