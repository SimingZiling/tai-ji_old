package org.shaoyin.orm.dialect;


/**
 * 方言接口，处理不同方言统一化处理
 */
public interface Dialect {

    /**
     * @return 包装器
     */
    Wrapper getWrapper();

}
