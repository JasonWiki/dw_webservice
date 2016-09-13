package com.angejia.dw.web_service.modules.broker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

// spring 注解
import org.springframework.stereotype.Service;

// service
import com.angejia.dw.web_service.modules.broker.service.BrokerUserMateInventoryService;
import com.angejia.dw.web_service.modules.user.service.UserPortraitService;

// dao
import com.angejia.dw.web_service.modules.broker.dao.DemandDao;
import com.angejia.dw.web_service.modules.broker.dao.BrokerCustomerBindUserDao;

// model
import com.angejia.dw.web_service.modules.broker.model.DemandTb;
import com.angejia.dw.web_service.modules.broker.model.BrokerCustomerBindUser;

@Service("brokerUserMateInventoryService")
public class BrokerUserMateInventoryServiceImpl implements BrokerUserMateInventoryService {

    @Autowired
    private DemandDao demandDao;
    
    @Autowired
    private UserPortraitService userPortraitService;

    @Autowired
    private BrokerCustomerBindUserDao brokerCustomerBindUserDao;

    public String getBrokerUserMateInventory(Long brokerId, Long userId) {

        //DemandTb demand = this.getDemand(brokerId, userId);
        //System.out.println(demand.getId() + ": " + demand.getCityId() + "," + demand.getDistrictIds() + "," + demand.getBlockIds() + "," + demand.getCommunityIds() + "," + demand.getStatus());

        this.getUserPortrait(userId);
        return "";
    }

    
    
    /**
     * 获取客户画像
     * @param userId
     */
    public void getUserPortrait(Long userId) {
        userPortraitService.getUserPortraitByRowkey(userId.toString());
    }
    

    /**
     * 获取客户需求
     * @param brokerId
     * @param userId
     * @return
     */
    public DemandTb getDemand(Long brokerId, Long userId) {
        DemandTb demand = new DemandTb();

        // 通过 userId 获取 客户 ID
        BrokerCustomerBindUser brokerCustomerBindUser = brokerCustomerBindUserDao.getCustomerIdByUserId(Integer.valueOf(userId.toString()));
        if (brokerCustomerBindUser != null) {
            // 通过 uesrId 找到的客户 CustomerId
            Integer customerId = brokerCustomerBindUser.getBrokerCustomerId();

            // 通过 brokerId 和 客户 Id 获取顾问对客户的需求秒数
            demand =  demandDao.getDemandByBrokerIdAndUserId(brokerId, Long.valueOf(customerId.toString()));

        }

        return demand;
    }
    

}
