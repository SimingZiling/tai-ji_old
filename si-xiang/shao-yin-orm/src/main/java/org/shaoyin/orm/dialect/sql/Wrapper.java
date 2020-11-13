package org.shaoyin.orm.dialect.sql;

/**
 * 包装器
 * 用于字段的包装
 */
public class Wrapper {

    /** 前置包装符号 */
    private Character preWrapQuote;
    /** 后置包装符号 */
    private Character sufWrapQuote;

    /**
     * 无参构造函数
     */
    public Wrapper() {
    }

    /**
     * 构造函数
     * @param wrapQuote 单包装字符
     */
    public Wrapper(Character wrapQuote) {
        this.preWrapQuote = wrapQuote;
        this.sufWrapQuote = wrapQuote;
    }

    /**
     * 构造函数
     * @param preWrapQuote 前置包装符号
     * @param sufWrapQuote 后置包装符号
     */
    public Wrapper(Character preWrapQuote, Character sufWrapQuote) {
        this.preWrapQuote = preWrapQuote;
        this.sufWrapQuote = sufWrapQuote;
    }

    /*--------包装符号set/get方法-->开始--------*/

    public Character getPreWrapQuote() {
        return preWrapQuote;
    }

    public void setPreWrapQuote(Character preWrapQuote) {
        this.preWrapQuote = preWrapQuote;
    }

    public Character getSufWrapQuote() {
        return sufWrapQuote;
    }

    public void setSufWrapQuote(Character sufWrapQuote) {
        this.sufWrapQuote = sufWrapQuote;
    }

    /*--------包装符号set/get方法-->结束--------*/




}
