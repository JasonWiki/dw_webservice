package com.angejia.dw.web_service.modules.broker.dao;

import com.angejia.dw.web_service.core.base.BaseDao;
import com.angejia.dw.web_service.modules.broker.model.DemandTb;

public interface DemandDao extends BaseDao<DemandTb>{

    public String getDemandByBrokerIdAndUserId(String brokerId, String userId);
}
