package com.angejia.dw.web_service.core.base.dao;

import org.hibernate.SessionFactory;

/**
 * @author Jason
 */
public class DataSourceSessionFactory {

    /**
     * 保存 spring 注入的 productDataSessionFactory 工厂
     */
    public SessionFactory productDataSessionFactory;
    public void setProductDataSessionFactory(SessionFactory productDataSessionFactory) {
        this.productDataSessionFactory = productDataSessionFactory;
    }
    public SessionFactory getProductDataSessionFactory() {
        return this.productDataSessionFactory;
    }
}
