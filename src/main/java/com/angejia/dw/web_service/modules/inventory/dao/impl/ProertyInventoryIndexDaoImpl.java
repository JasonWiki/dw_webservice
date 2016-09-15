package com.angejia.dw.web_service.modules.inventory.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.angejia.dw.web_service.core.base.dao.impl.BaseDaoHibernate4;

import com.angejia.dw.web_service.core.base.dao.DataSourceSessionFactory;
import com.angejia.dw.web_service.modules.entity.dw.dw_service.ProertyInventoryIndexEntity;
import com.angejia.dw.web_service.modules.inventory.dao.ProertyInventoryIndexDao;


@Repository("proertyInventoryIndexDao")
//spring 整理注入, 必须开启事物
@Transactional("dwDataTransactionManager")
public class ProertyInventoryIndexDaoImpl extends BaseDaoHibernate4<ProertyInventoryIndexEntity> implements ProertyInventoryIndexDao {


    private static final Logger logger = Logger.getLogger(ProertyInventoryIndexDaoImpl.class);

    /**
     * 配置数据源
     */
    @Autowired
    private DataSourceSessionFactory dataSourceDao;

    public List<ProertyInventoryIndexEntity> getInventorysByEntity(ProertyInventoryIndexEntity entity, Integer offset, Integer limit) {
        List<ProertyInventoryIndexEntity> result = new ArrayList<ProertyInventoryIndexEntity>();

        super.setSessionFactory(dataSourceDao.getDwDataSessionFactory());
     // 创建一个查询对象
        Criteria criteria = super.getSessionFactory().getCurrentSession()
                .createCriteria(ProertyInventoryIndexEntity.class)
                // 筛选条件
                //.add(Restrictions.eq("status", 6))
                .add(Restrictions.eq("cityId", entity.getCityId()))
                .add(Restrictions.eq("districtId", entity.getDistrictId()))
                .add(Restrictions.eq("blockId", entity.getBlockId()))
                .add(Restrictions.eq("communityId", entity.getCommunityId()));

        System.out.println(criteria.list());
        return result;
    }
}
