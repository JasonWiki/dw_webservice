package com.angejia.dw.web_service.modules.broker.dao;

import com.angejia.dw.web_service.core.base.dao.BaseDao;
import com.angejia.dw.web_service.modules.broker.model.DemandTb;


/**
 * 操作 Demand 数据表接口
 * @author Jason
 */

public interface DemandDao extends BaseDao<DemandTb>{

    public DemandTb getDemandByBrokerIdAndUserId(Long brokerId, Long userId);
}
