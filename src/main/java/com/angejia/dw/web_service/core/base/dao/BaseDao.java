package com.angejia.dw.web_service.core.base.dao;

import java.util.List;
import java.io.Serializable;

public interface BaseDao<T>
{
    // 根据 ID 加载实体
    T get(Class<T> entityClazz , Serializable id);

    // 保存实体
    Serializable save(T entity);

    // 修改实体
    void update(T entity);

    // 删除实体
    void delete(T entity);

    // 根据 ID 删除实体
    void delete(Class<T> entityClazz , Serializable id);

    // 获取所有实体
    List<T> findAll(Class<T> entityClazz);

    // 获取实体总数
    long findCount(Class<T> entityClazz);
}
