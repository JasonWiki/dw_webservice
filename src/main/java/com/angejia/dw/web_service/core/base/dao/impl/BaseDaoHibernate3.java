package com.angejia.dw.web_service.core.base.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.*;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.angejia.dw.web_service.core.base.dao.BaseDao;

import org.springframework.orm.hibernate4.HibernateCallback;

public class BaseDaoHibernate3<T> extends HibernateDaoSupport implements BaseDao<T>
{
 // 根据ID加载实体
    public T get(Class<T> entityClazz, Serializable id)
    {
        return getHibernateTemplate().get(entityClazz, id);
    }

    // 保存实体
    public Serializable save(T entity)
    {
        return getHibernateTemplate().save(entity);
    }

    // 更新实体
    public void update(T entity)
    {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    // 删除实体
    public void delete(T entity)
    {
        getHibernateTemplate().delete(entity);
    }

    // 根据ID删除实体
    public void delete(Class<T> entityClazz, Serializable id)
    {
        delete(get(entityClazz , id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll(Class<T> entityClazz)
    {
        return (List<T>)getHibernateTemplate().find("select en from "
            + entityClazz.getSimpleName() + " en");
    }

    @Override
    @SuppressWarnings("unchecked")
    public long findCount(Class<T> entityClazz)
    {
        List<Long> list = (List<Long>)getHibernateTemplate().find(
            "select count(*) from " + entityClazz.getSimpleName() + " en");
        return list.get(0);
    }
    /**
     * 使用hql 语句进行分页查询操作
     * @param hql 需要查询的hql语句
     * @param pageNo 查询第pageNo页的记录
     * @param pageSize 每页需要显示的记录数
     * @return 当前页的所有记录
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByPage(final String hql,
        final int pageNo, final int pageSize)
    {
        // 通过一个HibernateCallback对象来执行查询
        List<T> list = getHibernateTemplate()
            .execute(new HibernateCallback<List<T>>()
        {
            // 实现HibernateCallback接口必须实现的方法
            public List<T> doInHibernate(Session session)
            {
                // 执行Hibernate分页查询
                List<T> result = session.createQuery(hql)
                    .setFirstResult((pageNo - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .list();
                return result;
            }
        });
        return list;
    }

    /**
     * 使用hql 语句进行分页查询操作
     * @param hql 需要查询的hql语句
     * @param pageNo 查询第pageNo页的记录
     * @param pageSize 每页需要显示的记录数
     * @param params 如果hql带占位符参数，params用于传入占位符参数
     * @return 当前页的所有记录
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByPage(final String hql , final int pageNo, 
        final int pageSize , final  Object... params)
    {
        // 通过一个HibernateCallback对象来执行查询
        List<T> list = getHibernateTemplate()
            .execute(new HibernateCallback<List<T>>()
        {
            // 实现HibernateCallback接口必须实现的方法
            public List<T> doInHibernate(Session session)
            {
                // 执行Hibernate分页查询
                Query query = session.createQuery(hql);
                // 为包含占位符的HQL语句设置参数
                for(int i = 0 , len = params.length ; i < len ; i++)
                {
                    query.setParameter(i + "" , params[i]);
                }
                List<T> result = query.setFirstResult((pageNo - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .list();
                return result;
            }
        });
        return list;
    }
}
