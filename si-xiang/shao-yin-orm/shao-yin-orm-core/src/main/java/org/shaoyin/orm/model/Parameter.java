package org.shaoyin.orm.model;

import org.shaoyin.orm.exception.ShaoYinORMException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于参数模型，用于封装参数，参数列表与参数集合不能同时存在
 */
public class Parameter {

    /** 参数列表 */
    private final List<Object> parameterList = new ArrayList<>();

    /** 参数集合 */
    private final Map<String,Object> parameterMap = new HashMap<>();

    /**
     * 获取参数列表
     * @return 参数列表
     */
    public List<Object> getParameterList() {
        return parameterList;
    }

    /**
     * 获取参数集合
     * @return 参数集合
     */
    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    /**
     * 添加参数列表
     * @param parameter 参数
     * @return 参数对象
     */
    public Parameter add(String parameter){
        if(!parameterMap.isEmpty()){
            try {
                throw new ShaoYinORMException("已存在参数集合，参数列表与参数集合不能同时存在！");
            } catch (ShaoYinORMException e) {
                e.printStackTrace();
            }
        }
        parameterList.add(parameter);
        return this;
    }

    /**
     * 添加参数集合
     * @param key 参数键
     * @param value 参数值
     * @return 参数对象
     */
    public Parameter add(String key,Object value){
        if(!parameterList.isEmpty()){
            try {
                throw new ShaoYinORMException("已存在参数列表，参数列表与参数集合不能同时存在！");
            } catch (ShaoYinORMException e) {
                e.printStackTrace();
            }
        }
        parameterMap.put(key,value);
        return this;
    }
}
