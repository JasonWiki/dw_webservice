package com.angejia.dw.web_service.modules.broker.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import  com.angejia.dw.web_service.core.base.impl.BaseDaoHibernate4;

import com.angejia.dw.web_service.modules.broker.dao.DemandDao;
import com.angejia.dw.web_service.modules.broker.model.DemandTb;;

@Repository("demandDao")
//@Transactional("productDataTransactionManager")
@Transactional()
public class DemandDaoImpl extends BaseDaoHibernate4<DemandTb> implements DemandDao {

    private static final Logger logger = Logger.getLogger(DemandDaoImpl.class);

    @Autowired
    private JdbcTemplate productJdbc;
    public JdbcTemplate getProductJdbc() {
        return productJdbc;
    }
    public void setProductJdbc(JdbcTemplate productJdbc) {
        this.productJdbc = productJdbc;
    }


    public String getDemandByBrokerIdAndUserId(String brokerId, String userId) {

        System.out.println(super.findCount(DemandTb.class));
        
        System.out.println(123);
        
        return "Im tMemberDemandByUserId";
    }
}
