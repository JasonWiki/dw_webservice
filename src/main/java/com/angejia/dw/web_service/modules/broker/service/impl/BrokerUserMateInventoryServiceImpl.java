package com.angejia.dw.web_service.modules.broker.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// spring 注解
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.angejia.dw.web_service.core.utils.string.StringUtil;

// service
import com.angejia.dw.web_service.modules.broker.service.BrokerUserMateInventoryService;
import com.angejia.dw.web_service.modules.entity.dw.dw_service.ProertyInventoryIndexEntity;
import com.angejia.dw.web_service.modules.entity.product.angejia.BrokerCustomerBindUserEntity;
import com.angejia.dw.web_service.modules.entity.product.angejia.DemandEntity;
import com.angejia.dw.web_service.modules.user.service.UserPortraitService;
import com.angejia.dw.web_service.modules.inventory.service.InventoryService;

// dao
import com.angejia.dw.web_service.modules.broker.dao.DemandDao;
import com.angejia.dw.web_service.modules.broker.dao.BrokerCustomerBindUserDao;


@Service("brokerUserMateInventoryService")
public class BrokerUserMateInventoryServiceImpl implements BrokerUserMateInventoryService {

    @Autowired
    private DemandDao demandDao;

    @Autowired
    private UserPortraitService userPortraitService;

    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private BrokerCustomerBindUserDao brokerCustomerBindUserDao;


    /**
     * 顾问推荐房源
     */
    public List<Map<String, String>> getBrokerUserMateInventory(Long brokerId, Long userId, Long cityId) {
        // 最终推荐房源数据
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        // 客户需求
        DemandEntity demand = this.getDemand(brokerId, userId, cityId);

        if (demand != null) {
            System.out.println("客户需求:" + demand.getId() + ": " + demand.getCityId() + "," + demand.getDistrictIds() + "," + demand.getBlockIds() + "," + demand.getCommunityIds() + "," + demand.getStatus());

            // 小区搜索
            ProertyInventoryIndexEntity demandSearch = new ProertyInventoryIndexEntity();
            demandSearch.setCityId(1);

            // 批量查询
            Integer[] districtIds = StringUtil.strArrToIntArr("12;12".split(";"));
            Integer[] blockIds = StringUtil.strArrToIntArr("120;120".split(";"));
            Integer[] communityIds = StringUtil.strArrToIntArr("11998;11998".split(";"));
            Byte[] bedroomsIds = StringUtil.strArrToByteArr("2;3".split(";"));
            Byte[] priceTierIds = StringUtil.strArrToByteArr("5;7".split(";"));

            demandSearch.setDistrictIds(districtIds);
            demandSearch.setBlockIds(blockIds);
            demandSearch.setCommunityIds(communityIds);
            demandSearch.setBedroomsIds(bedroomsIds);
            demandSearch.setPriceTierIds(priceTierIds);

            result = inventoryService.searchInventoryByEntity(demandSearch, 0, 10);

        }
        
        // List<Map<String, String>> userPortrait = userPortraitService.getUserPortraitResult(userId.toString(), cityId.toString());

        return result;
    }

 
    /**
     * 获取客户需求
     * @param brokerId
     * @param userId
     * @param cityId
     * @return DemandEntity
     */
    public DemandEntity getDemand(Long brokerId, Long userId, Long cityId) {
        DemandEntity demand = new DemandEntity();

        // 通过 userId 获取 客户 ID
        BrokerCustomerBindUserEntity brokerCustomerBindUser = brokerCustomerBindUserDao.getCustomerIdByUserId(Integer.valueOf(userId.toString()));
        if (brokerCustomerBindUser != null) {
            // 通过 uesrId 找到的客户 CustomerId
            Integer customerId = brokerCustomerBindUser.getBrokerCustomerId();

            // 通过 brokerId 和 客户 Id 获取顾问对客户的需求秒数
            demand =  demandDao.getDemandByBrokerIdAndUserId(
                    brokerId, 
                    Long.valueOf(customerId.toString()),
                    cityId
            );
        }

        return demand;
    }
    

}
