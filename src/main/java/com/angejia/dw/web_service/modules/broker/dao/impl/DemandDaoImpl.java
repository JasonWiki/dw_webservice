package com.angejia.dw.web_service.modules.broker.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.angejia.dw.web_service.modules.broker.dao.DemandDao;

@Repository("demandDao")
public class DemandDaoImpl implements DemandDao {


    @Autowired
    private JdbcTemplate productJdbcTemplate;
    public JdbcTemplate getTemplate() {
        return productJdbcTemplate;
    }
    public void setTemplate(JdbcTemplate template) {
        this.productJdbcTemplate = template;
    }
    
    public String getMemberDemandByUserId(String userId) {
        return "Im tMemberDemandByUserId";
    }
}
