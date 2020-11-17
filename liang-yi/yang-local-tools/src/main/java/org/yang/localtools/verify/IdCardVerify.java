package org.yang.localtools.verify;


import org.yang.localtools.generate.IdCardDenerate;
import org.yang.localtools.generate.exception.GenerateToolsException;
import org.yang.localtools.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 身份证校验
 */
public class IdCardVerify {

    /**
     * 身份证中生日格式
     */
    private static final  String BIRTH_DATE_FORMAT = "yyyyMMdd";

    /**
     * 验证中国身份证
     * @param idCard 身份证
     * @return 是否为中国身份证
     */
    public static boolean isChineseIdCard(String idCard){
        // 首先判断传入身份证是否为空，如果为空则返回false
        if(!StringVerify.isBlank(idCard)){
            return false;
        }
        // 判断身份证是否为旧身份证长度，如果是则通过旧身份证生成新身份证在进行验证
        if(idCard.length() == IdCardDenerate.OLD_ID_CARD_LENGTH){
            // 生成新身份证
            try {
                idCard = IdCardDenerate.generateNewIdCardByOldIdCard(idCard);
            } catch (GenerateToolsException e) {
                return false;
            }
        }
        if(idCard.length() == IdCardDenerate.NEW_ID_CARD_LENGTH){
            // 身份证基础验证
            if(!idCard.matches("\\d{15}(\\d{2}[0-9xX])?")){
                return false;
            }
            // 验证校验码
            if(IdCardDenerate.calculateVerifyCode(idCard) != idCard.charAt(IdCardDenerate.NEW_ID_CARD_LENGTH - 1)){
                return false;
            }
            try {
                String birthdayPart = IdCardDenerate.getBirthDayByIdCard(idCard);
                Date birthDate = new SimpleDateFormat(BIRTH_DATE_FORMAT).parse(birthdayPart);
                // 验证生日是否生成时间 如果没有生成则表示生日不正确
                if(birthDate == null){
                    return false;
                }
                // 如果身份证生日在当天之后的话也不正确，没有人先拿身份证再出生的
                if(!birthDate.before(new Date())){
                    return false;
                }
                // 在1900年之前出生的也不能通过，据当前120岁，估计岁互联网操作不是很熟悉了都
                if(!birthDate.after(new Date(-2209017600000L))){
                    return false;
                }
                // 年月日正确性验证，月份[1,12],日期[1,31],还需要校验闰年、大月、小月的情况时
                String realBirthdayPart = new SimpleDateFormat(BIRTH_DATE_FORMAT).format(birthDate);
                if(!birthdayPart.equals(realBirthdayPart)){
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
                return true;
        }else {
            return false;
        }
    }
}
