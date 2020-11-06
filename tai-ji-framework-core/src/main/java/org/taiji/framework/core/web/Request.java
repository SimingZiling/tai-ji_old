package org.taiji.framework.core.web;

import java.util.Map;

/**
 * 请求
 */
public class Request {

    private Map<String,String[]> requestParameterMap ;// 请求参数

    private Map<String,String> requestHeaderMap ;// 请求头

    private String requestMethod;// 请求方式

    public Map<String, String> getRequestHeaderMap() {
        return requestHeaderMap;
    }

    public void setRequestHeaderMap(Map<String, String> requestHeaderMap) {
        this.requestHeaderMap = requestHeaderMap;
    }

    public Map<String, String[]> getRequestParameterMap() {
        return requestParameterMap;
    }

    public void setRequestParameterMap(Map<String, String[]> requestParameterMap) {
        this.requestParameterMap = requestParameterMap;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * 通过请求参数名称获参数信息
     * @param parameterName 参数名称
     * @return 参数信息
     */
    public String[] getRequestParameter(String parameterName){
        return this.requestParameterMap.get(parameterName);
    }

    /**
     * 通过请求头获取请求头信息
     * @param headerName 请求头名称
     * @return 参数信息
     */
    public String getRequestHeader(String headerName){
        return this.requestHeaderMap.get(headerName);
    }


}
