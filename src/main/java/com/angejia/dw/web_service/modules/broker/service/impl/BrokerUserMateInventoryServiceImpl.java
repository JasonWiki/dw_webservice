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
import com.angejia.dw.web_service.modules.entity.portrait.UserTagsEntity;
import com.angejia.dw.web_service.modules.entity.product.angejia.BrokerCustomerBindUserEntity;
import com.angejia.dw.web_service.modules.entity.product.angejia.DemandEntity;
import com.angejia.dw.web_service.modules.user.service.UserPortraitService;
import com.angejia.dw.web_service.modules.inventory.service.InventoryService;

// dao
import com.angejia.dw.web_service.modules.broker.dao.DemandDao;
import com.angejia.dw.web_service.modules.broker.dao.BrokerCustomerBindUserDao;

// entity
import com.angejia.dw.web_service.modules.entity.portrait.UserTagsEntity;


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
        //demand = null;

        if (demand != null) {
            System.out.println(
                    "客户需求 id: " + demand.getId() 
                    + " 城市: " + demand.getCityId() 
                    + " 区域: " + demand.getDistrictIds() 
                    + " 版块: " + demand.getBlockIds() 
                    + " 小区: " + demand.getCommunityIds() 
                    + " 户型: " + demand.getBedrooms()
                    + " 预算: " + demand.getBudget() + " 万"
                    + " 需求状态: " + demand.getStatus() 
                    );

            String bedroomsIds = this.bedroomsMapping(demand.getBedrooms());
            System.out.println("户型原 ids : " + demand.getBedrooms() + " 格式化后 ids: " + bedroomsIds);

            // 预算 万
            Integer budget = demand.getBudget().intValue() * 10000;
            Integer priceMin = (int) (budget - (budget * 0.1));
            Integer priceMax = (int) (budget + (budget * 0.1));
            System.out.println("预算: " + budget + " 最小浮动: " + priceMin + " 最大浮动: " + priceMax);

            // 小区搜索
            ProertyInventoryIndexEntity demandCommunitySearch = new ProertyInventoryIndexEntity();
            demandCommunitySearch.setCityId(demand.getCityId());
            demandCommunitySearch.setCommunityIds(
                    StringUtil.strArrToIntArr(  demand.getCommunityIds().split(";") )
                    );
            demandCommunitySearch.setBedroomsIds(
                    StringUtil.strArrToByteArr( bedroomsIds.split(";"))
                    );
            demandCommunitySearch.setPriceMin(priceMin);
            demandCommunitySearch.setPriceMax(priceMax);
            demandCommunitySearch.setSearchFrom("demand_community");

            // 搜索房源
            result.addAll(inventoryService.searchInventoryByEntity(demandCommunitySearch, 0, 20));
            
            
            // 扩展到版块界别搜索
            ProertyInventoryIndexEntity demandBlockSearch = new ProertyInventoryIndexEntity();
            demandBlockSearch.setCityId(demand.getCityId());
            demandBlockSearch.setBlockIds(
                    StringUtil.strArrToIntArr(  demand.getBlockIds().split(";") )
                    );
            demandBlockSearch.setBedroomsIds(
                    StringUtil.strArrToByteArr( bedroomsIds.split(";"))
                    );
            demandBlockSearch.setPriceMin(priceMin);
            demandBlockSearch.setPriceMax(priceMax);
            demandBlockSearch.setSearchFrom("demand_block");

            // 搜索房源
            result.addAll(inventoryService.searchInventoryByEntity(demandBlockSearch, 0, 20));

        }

        /*
        // 客户画像
        List<Map<String, String>> userPortrait = userPortraitService.getUserPortraitResult(userId.toString(), cityId.toString());

        for (int i =0; i <= userPortrait.size()-1; i ++) {
            
            if (i >= 5) break;
            
            Map<String, String> userPortraitInfo = userPortrait.get(i);

            ProertyInventoryIndexEntity userPortraitSearch = new ProertyInventoryIndexEntity();

            // 城市 Id
            if (userPortraitInfo.get(UserTagsEntity.CITY_TAG_CODE) != "") {
                userPortraitSearch.setCityId( Integer.parseInt(userPortraitInfo.get(UserTagsEntity.CITY_TAG_CODE)) );
            }
            
            // 区域 Id
            if (userPortraitInfo.get(UserTagsEntity.DISTRICT_TAG_CODE) != "") {
                userPortraitSearch.setDistrictId( Integer.parseInt(userPortraitInfo.get(UserTagsEntity.DISTRICT_TAG_CODE)) );
            }
            
            // 版块 Id
            if (userPortraitInfo.get(UserTagsEntity.BLOCK_TAG_CODE) != "") {
                userPortraitSearch.setBlockId( Integer.parseInt(userPortraitInfo.get(UserTagsEntity.BLOCK_TAG_CODE)) );
            }
            
            // 小区 Id
            if (userPortraitInfo.get(UserTagsEntity.COMMUNITY_TAG_CODE) != "") {
                userPortraitSearch.setCommunityId( Integer.parseInt(userPortraitInfo.get(UserTagsEntity.COMMUNITY_TAG_CODE)) );
            }
            
            // 户型 id
            if (userPortraitInfo.get(UserTagsEntity.BEDROOMS_TAG_CODE) != "") {
                userPortraitSearch.setBedrooms( Byte.parseByte(userPortraitInfo.get(UserTagsEntity.BEDROOMS_TAG_CODE)) );
            }
            
            // 价格段 id
            if (userPortraitInfo.get(UserTagsEntity.PRICE_TAG_CODE) != "") {
                userPortraitSearch.setPriceTier( Byte.parseByte(userPortraitInfo.get(UserTagsEntity.PRICE_TAG_CODE)) );
            }
            
            userPortraitSearch.setSearchFrom("user_portrait");
            
            result.addAll(inventoryService.searchInventoryByEntity(userPortraitSearch, 0, 20));
        }
        
        System.out.println(userPortrait);
        */
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
    
    
    /**
     * 户型 map 替换
     * @param bedroomsIds 户型 ids 
     */
    public String bedroomsMapping(String bedroomsIds) {
        StringBuffer bedroomsBuffer = new StringBuffer();

        String[] bedroomsIdsArr = bedroomsIds.split(";");

        for (int i=0; i<= bedroomsIdsArr.length - 1; i ++) {
            String sp = "";
            if ( i < bedroomsIdsArr.length - 1) {
                sp = ";";
            }
            String curS = UserTagsEntity.BEDROOMS_MAP.get( bedroomsIdsArr[i] ) + sp;
            bedroomsBuffer.append( curS );
        }

        return bedroomsBuffer.toString();
    }

}
