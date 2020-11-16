package org.yang.localtools.generate;

import org.yang.localtools.generate.exception.GenerateToolsException;
import org.yang.localtools.util.StringUtil;

/**
 * 身份的生成
 */
public class IdCardDenerate {

    /**旧身份证位数*/
    public static final  int OLD_ID_CARD_LENGTH = 15;

    /**新身份证位数*/
    public static final  int NEW_ID_CARD_LENGTH = 18;

    /**18位身份证中，各个数字的生成校验码时的权值*/
    private static final int[] VERIFY_CODE_WEIGHT ={ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

    /**
     * 18位身份证中最后一位校验码
     */
    private static final char[] VERIFY_CODE = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };


    /**
     * 通过旧身份证生成新身份证
     * @param oldIdCard 就身份证
     * @return 新身份证
     */
    public static String generateNewIdCardByOldIdCard(String oldIdCard) throws GenerateToolsException {
        // 如果旧身份证为空，则返回空字符串
        if(StringUtil.isBlank(oldIdCard) || oldIdCard.length() != OLD_ID_CARD_LENGTH){
            throw new GenerateToolsException("旧身份证为空或位数不正确！");
        }
        // 设置字符串长度为新身份证长度
        StringBuilder idCard = new StringBuilder(NEW_ID_CARD_LENGTH);
        // 添加生日年的前两位，2000年已经更换身份证，不用考虑20的情况 建国1949年，也不用考虑19以前的情况
        idCard.append("19");
        idCard.append(oldIdCard.substring(6));
        idCard.append(calculateVerifyCode(idCard));
        return idCard.toString();
    }

    /**
     * 计算校验码
     * @param idCard 身份证号码
     * @return 校验码
     */
    public static char calculateVerifyCode(CharSequence idCard){
        int sum = 0;
        for (int i = 0; i < NEW_ID_CARD_LENGTH -1; i++){
            char ch = idCard.charAt(i);
            sum += (ch - '0') * VERIFY_CODE_WEIGHT[i];
        }
        return VERIFY_CODE[sum % 11];
    }

    /**
     * 通过身份证获取生日
     * @param idCard 身份证
     * @return 生日
     */
    public static String getBirthDayByIdCard(String idCard) {
        return idCard.substring(6, 14);
    }
}
