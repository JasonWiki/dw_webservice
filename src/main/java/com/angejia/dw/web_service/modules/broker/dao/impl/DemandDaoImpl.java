package com.angejia.dw.web_service.modules.broker.dao.impl;

import java.util.List;
import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.Session;
// 代表一次查询
import org.hibernate.Criteria;
// 代表查询条件
import org.hibernate.criterion.Criterion;
// 生成查询条件
import org.hibernate.criterion.Restrictions;

import com.angejia.dw.web_service.core.base.dao.impl.BaseDaoHibernate4;

import com.angejia.dw.web_service.core.base.dao.DataSourceSessionFactory;
import com.angejia.dw.web_service.modules.broker.dao.DemandDao;
import com.angejia.dw.web_service.modules.entity.product.angejia.DemandEntity;;

@Repository("demandDao")
// spring 整理注入, 必须开启事物
@Transactional("productDataTransactionManager")
/**
 * demand 处理方法
 * @author Jason
 */
public class DemandDaoImpl extends BaseDaoHibernate4<DemandEntity> implements DemandDao {

    private static final Logger logger = Logger.getLogger(DemandDaoImpl.class);

    /**
     * 配置数据源
     */
    @Autowired
    private DataSourceSessionFactory dataSourceDao;


    @Autowired
    private JdbcTemplate productJdbc;
    public JdbcTemplate getProductJdbc() {
        return productJdbc;
    }
    public void setProductJdbc(JdbcTemplate productJdbc) {
        this.productJdbc = productJdbc;
    }


    /**
     * 获取需求
     */
    public DemandEntity getDemandByBrokerIdAndUserId(Long brokerId, Long userId) {
        super.setSessionFactory(dataSourceDao.getProductDataSessionFactory());

        // 创建一个查询对象
        Criteria criteria = super.getSessionFactory().getCurrentSession()
                .createCriteria(DemandEntity.class)
                // 筛选条件
                .add(Restrictions.eq("status", DemandEntity.DEMAND_STATUS))
                .add(Restrictions.eq("brokerId", brokerId))
                .add(Restrictions.eq("customerId", userId));

        // 返回一条数据
        DemandEntity result = (DemandEntity) criteria.uniqueResult();

        //System.out.println(demand.getId() + ": " + demand.getCityId() + "," + demand.getDistrictIds() + "," + demand.getBlockIds() + "," + demand.getCommunityIds() + "," + demand.getStatus());
        return result;
    }


    public void test(Long brokerId, Long userId) {

        // 获取工厂对象
        Session currentSession = super.getSessionFactory().getCurrentSession();
        // 创建一个查询对象
        Criteria criteria = currentSession.createCriteria(DemandEntity.class);

        // 添加限制条件
        Criterion conditions1 = Restrictions.eq("status", DemandEntity.DEMAND_STATUS);
        criteria.add(conditions1);
        criteria.add(Restrictions.eq("brokerId", brokerId));
        criteria.add(Restrictions.eq("customerId", userId));

        System.out.println(DemandEntity.DEMAND_STATUS);
        System.out.println(brokerId);
        System.out.println(userId);

        // 返回一条数据
        DemandEntity a = (DemandEntity) criteria.uniqueResult();
        System.out.println(a.getId() + ": " + a.getCityId() + "," + a.getDistrictIds() + "," + a.getBlockIds() + "," + a.getCommunityIds() + "," + a.getStatus());


        // 设置返回第一行结果集
        criteria.setFirstResult(0);
        // 设置返回的最大
        //criteria.setMaxResults(10);
        // 返回结果集
        List list = criteria.list();
        System.out.println(criteria.uniqueResult());;


        for (Object obj : list) {
            DemandEntity s = (DemandEntity) obj;
            System.out.println(s.getId() + ": " + s.getCityId() + "," + s.getDistrictIds() + "," + s.getBlockIds() + "," + s.getCommunityIds() + "," + s.getStatus());
        }
        System.out.println(list);

    }
}
