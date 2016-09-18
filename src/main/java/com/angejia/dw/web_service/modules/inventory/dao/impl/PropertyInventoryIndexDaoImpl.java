package com.angejia.dw.web_service.modules.inventory.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.angejia.dw.web_service.core.utils.string.StringUtil;

import com.angejia.dw.web_service.core.base.dao.impl.BaseDaoHibernate4;
import com.angejia.dw.web_service.core.base.dao.DataSourceSessionFactory;

import com.angejia.dw.web_service.modules.inventory.dao.PropertyInventoryIndexDao;
import com.angejia.dw.web_service.modules.entity.dw.dw_service.ProertyInventoryIndexEntity;



@Repository("proertyInventoryIndexDao")
//spring 整理注入, 必须开启事物
@Transactional("dwDataTransactionManager")
public class PropertyInventoryIndexDaoImpl extends BaseDaoHibernate4<ProertyInventoryIndexEntity> implements PropertyInventoryIndexDao {


    private static final Logger logger = Logger.getLogger(PropertyInventoryIndexDaoImpl.class);

    /**
     * 配置数据源
     */
    @Autowired
    private DataSourceSessionFactory dataSourceDao;

    // 
    public List<ProertyInventoryIndexEntity> getInventorysByEntity(ProertyInventoryIndexEntity entity, Integer offset, Integer limit) {

        super.setSessionFactory(dataSourceDao.getDwDataSessionFactory());

        // 创建一个查询对象
        Criteria criteria = super.getSessionFactory().getCurrentSession()
                   .createCriteria(ProertyInventoryIndexEntity.class);

        // 处理筛选条件

        // 在售状态
        if (entity.getStatus() != null) {
            criteria.add( Restrictions.eq("status", entity.getStatus()) );
        }

        // 实勘状态
        if (entity.getSurveyStatus() != null) {
            criteria.add( Restrictions.eq("surveyStatus", entity.getSurveyStatus()) );
        }

        // 是否真实房源
        if (entity.getIsReal() != null) {
            criteria.add( Restrictions.eq("isReal", entity.getIsReal()) );
        }

        // 城市
        if (entity.getCityId() != null) {
            criteria.add( Restrictions.eq("cityId", entity.getCityId()) );
        }

        // 区域
        if ( entity.getDistrictIds() != null && entity.getDistrictIds().length != 0 ) {  // 多个区域
            criteria.add( Restrictions.in( "districtId",  entity.getDistrictIds() ) ); 
        } else if (entity.getDistrictId() != null) {    // 单个区域
            criteria.add( Restrictions.eq("districtId", entity.getDistrictId()) );
        }

        // 版块
        if ( entity.getBlockIds() != null && entity.getBlockIds().length != 0 ) {  // 多个版块
            criteria.add( Restrictions.in( "blockId",  entity.getBlockIds() ) ); 
        } else if (entity.getBlockId() != null) {    // 单个版块
            criteria.add( Restrictions.eq("blockId", entity.getBlockId()) );
        }

        // 小区
        if ( entity.getCommunityIds() != null && entity.getCommunityIds().length != 0 ) {  // 多个小区
            criteria.add( Restrictions.in( "communityId",  entity.getCommunityIds() ) ); 
        } else if (entity.getCommunityId() != null) {    // 单个小区
            criteria.add( Restrictions.eq("communityId", entity.getCommunityId()) );
        }

        // 户型
        if ( entity.getBedroomsIds() != null && entity.getBedroomsIds().length != 0 ) {  // 多个户型
            criteria.add( Restrictions.in( "bedrooms",  entity.getBedroomsIds() ) ); 
        } else if (entity.getBedrooms() != null) {    // 单个 户型
            criteria.add( Restrictions.eq("bedrooms", entity.getCommunityId()) );
        }

        // 价格段
        if (entity.getPriceTierIds() != null && entity.getPriceTierIds().length != 0) { // 多个价格段
            criteria.add( Restrictions.in( "priceTier",  entity.getPriceTierIds() ) );
        } else if (entity.getPriceTier() != null) {
            criteria.add( Restrictions.eq("priceTier", entity.getPriceTier()) );
        }


        // 价格范围查询
        if (entity.getPriceMin() != null && entity.getPriceMax() != null ) {
            Restrictions.between("price", entity.getPriceMin(), entity.getPriceMax());
        // 价格从最小值查询
        } else if (entity.getPriceMin() != null) {
            Restrictions.between("price", entity.getPriceMin(), 999999999);
        } else if (entity.getPriceMax() != null) {
            Restrictions.between("price", 1, entity.getPriceMax());
        }

        // 排序
        criteria.addOrder(Order.asc("inventoryTypeId")); // 按照房源质量排序
        criteria.addOrder(Order.asc("price"));

        // 限制条数
        criteria.setFirstResult(offset);
        criteria.setMaxResults(limit);

        return criteria.list();
    }
    
    
    
    public List<ProertyInventoryIndexEntity> getInventorysByEntityBak(ProertyInventoryIndexEntity entity, Integer offset, Integer limit) {
        // 保存结果
        //List<ProertyInventoryIndexEntity> result = new ArrayList<ProertyInventoryIndexEntity>();

        super.setSessionFactory(dataSourceDao.getDwDataSessionFactory());
     // 创建一个查询对象
        Criteria criteria = super.getSessionFactory().getCurrentSession()
                .createCriteria(ProertyInventoryIndexEntity.class)
                // 筛选条件
                .add(Restrictions.eq("cityId", entity.getCityId()))
                .add(Restrictions.eq("districtId", entity.getDistrictId()))
                .add(Restrictions.eq("blockId", entity.getBlockId()))
                .add(Restrictions.eq("communityId", entity.getCommunityId()))

                //.add(Restrictions.eq("status", entity.getStatus()))
                //.add(Restrictions.eq("surveyStatus", entity.getSurveyStatus()))

                .addOrder(Order.asc("inventoryTypeId")) // 按照房源质量排序
                .addOrder(Order.asc("price"))   // 按照

                .setFirstResult(offset)
                .setMaxResults(limit)
                ;

        return criteria.list();
        /**
        for(Object o : criteria.list()) {
            ProertyInventoryIndexEntity t = (ProertyInventoryIndexEntity)o;
            System.out.println(
               "搜索房源: "
               + t.getInventoryId() 
               + " - " + t.getCityId()
               + " - " + t.getDistrictId()
               + " - " + t.getBlockId()
               + " - " + t.getCommunityId()
               + " - " + t.getBedrooms()
               + " - " + t.getPrice()
               + " - " + t.getPriceTier()
               + " - " + t.getStatus()
               + " - " + t.getSurveyStatus()
               + " - " + t.getIsReal()
               + " - " + t.getInventoryType()
               + " - " + t.getInventoryTypeId()
            );
        }
        */
    }
}
