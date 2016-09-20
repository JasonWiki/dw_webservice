package com.angejia.dw.web_service.modules.inventory.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.angejia.dw.web_service.modules.inventory.dao.BrokerUserMateInventoryDao;

import com.angejia.dw.web_service.modules.entity.dw.dm_db.BrokerUserMateInventoryRsEntity;

@Repository("brokerUserMateInventoryDao")
public class BrokerUserMateInventoryDaoImpl implements BrokerUserMateInventoryDao{

    @Autowired
    private JdbcTemplate productDbJdbc;
    public JdbcTemplate getProductDbJdbc() {
        return productDbJdbc;
    }
    public void setProductDbJdbc(JdbcTemplate productDbJdbc) {
        this.productDbJdbc = productDbJdbc;
    }


    /**
     * 保存推荐效果
     * @param brokerId
     * @param userId
     * @param cityId
     * @param rsCnt 推荐条数
     * @param RsRrom 推荐来源 
     * 把这些包装在 BrokerUserMateInventoryRsEntity 对象中即可
     */
    public Integer saveBrokerUserMateInventoryRsByEntity(BrokerUserMateInventoryRsEntity entity) {

        String sqlStr = "INSERT INTO dm_db.dm_broker_user_mate_inventory_rs(broker_id,user_id,city_id,rs_cnt,rs_from) "
                + "values(?,?,?,?,?)";

        Object[] params = new Object[]{entity.getBrokerId(), entity.getUserId(), entity.getCityId(), entity.getRsCnt(), entity.getRsRrom() };

        return productDbJdbc.update(sqlStr, params);

    }
  
}
