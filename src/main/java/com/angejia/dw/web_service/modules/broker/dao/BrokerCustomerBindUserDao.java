package com.angejia.dw.web_service.modules.broker.dao;

import com.angejia.dw.web_service.core.base.dao.BaseDao;
import com.angejia.dw.web_service.modules.entity.product.angejia.BrokerCustomerBindUserEntity;


/**
 * 操作 Demand 数据表接口
 * @author Jason
 */

public interface BrokerCustomerBindUserDao extends BaseDao<BrokerCustomerBindUserEntity>{

    public BrokerCustomerBindUserEntity getCustomerIdByUserId(Integer userId);
}
