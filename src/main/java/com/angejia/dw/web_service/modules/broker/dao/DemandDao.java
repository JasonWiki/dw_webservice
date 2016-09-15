package com.angejia.dw.web_service.modules.broker.dao;

import com.angejia.dw.web_service.core.base.dao.BaseDao;
import com.angejia.dw.web_service.modules.entity.product.angejia.DemandEntity;


/**
 * @author Jason
 * 操作 Demand 数据表接口
 */

public interface DemandDao extends BaseDao<DemandEntity>{

    public DemandEntity getDemandByBrokerIdAndUserId(Long brokerId, Long userId, Long cityId);
}
