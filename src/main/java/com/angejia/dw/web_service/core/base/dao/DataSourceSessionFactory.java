package com.angejia.dw.web_service.core.base.dao;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Jason
 */
public class DataSourceSessionFactory {

    /**
     * 保存 spring 注入的 productDataSessionFactory 工厂 (指向 angejia 库)
     */
    public SessionFactory productDataSessionFactory;
    public void setProductDataSessionFactory(SessionFactory productDataSessionFactory) {
        this.productDataSessionFactory = productDataSessionFactory;
    }
    public SessionFactory getProductDataSessionFactory() {
        return this.productDataSessionFactory;
    }


    /**
     * 保存 spring 注入的 dwDataSessionFactory 工厂 (指向 dw_service 库)
     */
    public SessionFactory dwDataSessionFactory;
    public SessionFactory getDwDataSessionFactory() {
        return dwDataSessionFactory;
    }
    public void setDwDataSessionFactory(SessionFactory dwDataSessionFactory) {
        this.dwDataSessionFactory = dwDataSessionFactory;
    }


    /**
     * spring dw 数据库 JDBC
     */
    public JdbcTemplate dwDbJdbc;
    public JdbcTemplate getDwDbJdbc() {
        return dwDbJdbc;
    }
    public void setDwDbJdbc(JdbcTemplate dwDbJdbc) {
        this.dwDbJdbc = dwDbJdbc;
    }


    /**
     * spring 业务数据库 JDBC
     */
    public JdbcTemplate productDataJdbc;
    public JdbcTemplate getProductDataJdbc() {
        return productDataJdbc;
    }
    public void setProductDataJdbc(JdbcTemplate productDataJdbc) {
        this.productDataJdbc = productDataJdbc;
    }

}
