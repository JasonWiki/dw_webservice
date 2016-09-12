package com.angejia.dw.web_service.modules.broker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

// spring 注解
import org.springframework.stereotype.Service;

import com.angejia.dw.web_service.modules.broker.service.BrokerUserMateInventoryService;
import com.angejia.dw.web_service.modules.broker.dao.DemandDao;

@Service("brokerUserMateInventoryService")
public class BrokerUserMateInventoryServiceImpl implements BrokerUserMateInventoryService {

    @Autowired
    private DemandDao demandDao;

    public String getBrokerUserMateInventory(String brokerId, String userId) {
        
        return demandDao.getMemberDemandByUserId(userId);
    }


}
