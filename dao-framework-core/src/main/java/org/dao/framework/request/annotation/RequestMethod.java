package org.dao.framework.request.annotation;

/**
 * 请求方式
 */
public enum RequestMethod {

    GET("GET","向特定的资源发出请求"),
    HEAD("HEAD", "向服务器索与GET请求相一致的响应，只不过响应体将不会被返回。这一方法可以再不必传输整个响应内容的情况下，就可以获取包含在响应小消息头中的元信息。"),
    POST("POST", "向指定资源提交数据进行处理请求（例如提交表单或者上传文件）。数据被包含在请求体中。POST请求可能会导致新的资源的建立和/或已有资源的修改"),
    PUT("PUT", "向指定资源位置上传其最新内容"),
    PATCH("PATCH", "是对PUT方法的补充，用来对已知资源进行局部更新"),
    DELETE("DELETE", "请求服务器删除Request-URL所标识的资源"),
    OPTIONS("OPTIONS", "返回服务器针对特定资源所支持的HTTP请求方法，也可以利用向web服务器发送‘*’的请求来测试服务器的功能性"),
    TRACE("TRACE", "回显服务器收到的请求，主要用于测试或诊断");

    private final String method;// 请求方式
    private String msg;// 信息


    /**
     * 构造方法
     * @param method 请求方式
     * @param msg 请求参数
     */
    RequestMethod(String method, String msg) {
        this.method = method;
        this.msg = msg;
    }

    public String getMethod(){
        return method;
    }

    public String getMsg(){
        return msg;
    }
}
