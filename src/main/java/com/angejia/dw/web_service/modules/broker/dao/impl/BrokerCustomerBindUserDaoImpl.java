package com.angejia.dw.web_service.modules.broker.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// 代表一次查询
import org.hibernate.Criteria;
// 生成查询条件
import org.hibernate.criterion.Restrictions;

import com.angejia.dw.web_service.core.base.dao.DataSourceSessionFactory;
import com.angejia.dw.web_service.core.base.dao.impl.BaseDaoHibernate4;
import com.angejia.dw.web_service.modules.broker.dao.BrokerCustomerBindUserDao;
import com.angejia.dw.web_service.modules.entity.product.angejia.BrokerCustomerBindUserEntity;

@Repository("brokerCustomerBindUserDao")
// spring 整理注入, 必须开启事物
@Transactional("productDataTransactionManager")
public class BrokerCustomerBindUserDaoImpl extends BaseDaoHibernate4<BrokerCustomerBindUserEntity> implements BrokerCustomerBindUserDao {

    private static final Logger logger = Logger.getLogger(BrokerCustomerBindUserDaoImpl.class);
    /**
     * 配置数据源
     */
    @Autowired
    private DataSourceSessionFactory dataSourceDao;


    /**
     * 通过 客户 id 获取 user id 
     * @param brokerId
     * @param userId
     * @return
     */
    public BrokerCustomerBindUserEntity getCustomerIdByUserId(Integer userId) {
        super.setSessionFactory(dataSourceDao.getProductDataSessionFactory());

        // 创建一个查询对象
        Criteria criteria = super.getSessionFactory().getCurrentSession()
                .createCriteria(BrokerCustomerBindUserEntity.class)
                // 筛选条件
                .add(Restrictions.eq("isActive", BrokerCustomerBindUserEntity.IS_ACTIVE))
                .add(Restrictions.eq("userId", userId));

        // 获取一条记录
        BrokerCustomerBindUserEntity result = (BrokerCustomerBindUserEntity) criteria.uniqueResult();

        return result;

    }
}
