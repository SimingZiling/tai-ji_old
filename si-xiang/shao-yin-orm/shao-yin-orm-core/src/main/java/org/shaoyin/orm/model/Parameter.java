package org.shaoyin.orm.model;

import org.shaoyin.orm.exception.ShaoYinORMException;

import java.util.*;

/**
 * 参数，传递列表参数与Map参数不能共存
 */
public class Parameter{

    private Boolean type;

    /** 参数列表，解决直接传参问题例如添加 */
    private final List<Object> parameterList = new ArrayList<>();

    /** 参数Map集合，解决键值对应，例如删除的条件 */
    private final Map<String,Object> conditionMap = new HashMap<>();

    /** 参数Map集合，解决键值对应，例如更新的参数和条件 */
    private final Map<String,Object> parameterMap = new HashMap<>();

    /**
     * 获取参数类型
     * @return 参数类型
     */
    public Boolean getType() {
        return type;
    }

    public void setParameterType(boolean type) throws ShaoYinORMException {
        if(this.type == null){
            this.type = type;
        }
//        else if(Boolean.TRUE.equals(this.type) && !type){
//            throw new ShaoYinORMException("当前参数为列表集合类型，禁止传入Map集合类型参数！");
//        }else if(Boolean.FALSE.equals(this.type) && type){
//            throw new ShaoYinORMException("当前参数为Map集合类型，禁止传入列表类型参数！");
//        }
    }

    public List<Object> getParameterList() {

        return parameterList;
    }

    public Map<String, Object> getConditionMap() {
        return conditionMap;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    /**
     * 添加参数
     * @param parameter 参数
     * @return 参数对象
     */
    public Parameter add(Object parameter){
        try {
            setParameterType(true);
        } catch (ShaoYinORMException e) {
            e.printStackTrace();
        }
        parameterList.add(parameter);
        return this;
    }

    /**
     * 添加参数（集合）
     * @param key 参数键
     * @param parameter 参数值
     * @return 参数对象
     */
    public Parameter add(String key,Object parameter){
        try {
            setParameterType(false);
        } catch (ShaoYinORMException e) {
            e.printStackTrace();
        }
        parameterMap.put(key,parameter);
        return this;
    }

    /**
     * 添加条件
     * @param key 条件键
     * @param condition 条件值
     * @return 参数对象
     */
    public Parameter addCondition(String key,Object condition){
        try {
            setParameterType(false);
        } catch (ShaoYinORMException e) {
            e.printStackTrace();
        }
        conditionMap.put(key,condition);
        return this;
    }
}
