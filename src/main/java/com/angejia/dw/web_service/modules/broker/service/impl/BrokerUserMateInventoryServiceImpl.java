package com.angejia.dw.web_service.modules.broker.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// spring 
import org.springframework.stereotype.Service;

import com.angejia.dw.web_service.core.utils.array.ListUtil;
import com.angejia.dw.web_service.core.utils.string.StringUtil;
import com.angejia.dw.web_service.modules.broker.dao.BrokerCustomerBindUserDao;
// dao
import com.angejia.dw.web_service.modules.broker.dao.DemandDao;
import com.angejia.dw.web_service.modules.broker.entity.Broker;
import com.angejia.dw.web_service.modules.broker.service.BrokerService;
// service
import com.angejia.dw.web_service.modules.broker.service.BrokerUserMateInventoryService;
import com.angejia.dw.web_service.modules.entity.dw.dm_db.BrokerUserMateInventoryRsEntity;
import com.angejia.dw.web_service.modules.entity.dw.dw_service.PropertyInventoryIndexEntity;
// entity
import com.angejia.dw.web_service.modules.entity.portrait.UserTagsEntity;
import com.angejia.dw.web_service.modules.entity.product.angejia.BrokerCustomerBindUserEntity;
import com.angejia.dw.web_service.modules.entity.product.angejia.DemandEntity;
import com.angejia.dw.web_service.modules.inventory.dao.BrokerUserMateInventoryDao;
import com.angejia.dw.web_service.modules.inventory.service.InventoryService;
import com.angejia.dw.web_service.modules.user.service.UserRecommendService;

@Service("brokerUserMateInventoryService")
public class BrokerUserMateInventoryServiceImpl implements BrokerUserMateInventoryService {

    @Autowired
    private DemandDao demandDao;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserRecommendService userRecommendService;

    @Autowired
    private BrokerService brokerService;

    @Autowired
    private BrokerCustomerBindUserDao brokerCustomerBindUserDao;

    @Autowired
    private BrokerUserMateInventoryDao brokerUserMateInventoryDao;

    /**
     * 顾问推荐房源
     */
    @Override
    public List<Map<String, String>> getBrokerUserMateInventories(Long brokerId, Long userId, Long cityId) {
        // 最终结果
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        // 保存推荐数据
        List<Map<String, String>> rsResult = new ArrayList<Map<String, String>>();

        // 顾问所在公司
        Broker broker = brokerService.getBrokerByUserId(brokerId);

        // 客户需求推荐房源
        DemandEntity demand = this.getDemand(brokerId, userId, cityId);
        if (demand != null) {
            System.out.println(
                    "客户需求 id: " + demand.getId()
                            + " 城市: " + demand.getCityId()
                            + " 区域: " + demand.getDistrictIds()
                            + " 版块: " + demand.getBlockIds()
                            + " 小区: " + demand.getCommunityIds()
                            + " 户型: " + demand.getBedrooms()
                            + " 预算: " + demand.getBudget() + " 万"
                            + " 需求状态: " + demand.getStatus());

            String bedroomsIds = this.bedroomsMapping(demand.getBedrooms());
            System.out.println("户型原 ids : " + demand.getBedrooms() + " 格式化后 ids: " + bedroomsIds);

            // 预算 万
            Integer budget = demand.getBudget().intValue() * 10000;
            Integer priceMin = (int) (budget - (budget * 0.1));
            Integer priceMax = (int) (budget + (budget * 0.1));
            System.out.println("预算: " + budget + " 最小浮动: " + priceMin + " 最大浮动: " + priceMax);

            // 小区搜索
            PropertyInventoryIndexEntity demandCommunitySearch = new PropertyInventoryIndexEntity();
            demandCommunitySearch.setCityId(demand.getCityId());
            demandCommunitySearch.setCommunityIds(
                    StringUtil.strArrToIntArr(demand.getCommunityIds().split(";")));
            demandCommunitySearch.setBedroomsIds(
                    StringUtil.strArrToByteArr(bedroomsIds.split(";")));
            demandCommunitySearch.setPriceMin(priceMin);
            demandCommunitySearch.setPriceMax(priceMax);
            demandCommunitySearch.setProviderId(broker.getCompanyId());
            demandCommunitySearch.setSearchFrom("demand_community");

            // 搜索房源
            rsResult.addAll(inventoryService.searchInventoryByEntity(demandCommunitySearch, 0, 20));

            // 扩展到版块界别搜索
            PropertyInventoryIndexEntity demandBlockSearch = new PropertyInventoryIndexEntity();
            demandBlockSearch.setCityId(demand.getCityId());
            demandBlockSearch.setBlockIds(
                    StringUtil.strArrToIntArr(demand.getBlockIds().split(";")));
            demandBlockSearch.setBedroomsIds(
                    StringUtil.strArrToByteArr(bedroomsIds.split(";")));
            demandBlockSearch.setPriceMin(priceMin);
            demandBlockSearch.setPriceMax(priceMax);
            demandBlockSearch.setProviderId(broker.getCompanyId());
            demandBlockSearch.setSearchFrom("demand_block");

            // 搜索房源
            rsResult.addAll(inventoryService.searchInventoryByEntity(demandBlockSearch, 0, 20));
        }

        // 用户 CBCF 推荐房源数据
        rsResult.addAll(userRecommendService.getCBCFRecommendInventories(userId.toString(), cityId.toString(),
                broker.getCompanyId(), 100));

        // 房源去重
        result = ListUtil.listMapValDistinct(rsResult, "inventory_id");

        // 保存推荐效果
        BrokerUserMateInventoryRsEntity brokerUserMateInventoryRsEntity = new BrokerUserMateInventoryRsEntity();
        brokerUserMateInventoryRsEntity.setBrokerId(Integer.parseInt(brokerId.toString()));
        brokerUserMateInventoryRsEntity.setUserId(Integer.parseInt(userId.toString()));
        brokerUserMateInventoryRsEntity.setCityId(Integer.parseInt(cityId.toString()));
        brokerUserMateInventoryRsEntity.setRsCnt(result.size());
        brokerUserMateInventoryRsEntity.setRsRrom("");
        brokerUserMateInventoryDao.saveBrokerUserMateInventoryRsByEntity(brokerUserMateInventoryRsEntity);

        return result;
    }

    /**
     * 获取客户需求
     *
     * @param brokerId
     * @param userId
     * @param cityId
     * @return DemandEntity
     */
    public DemandEntity getDemand(Long brokerId, Long userId, Long cityId) {
        DemandEntity demand = null;

        // 通过 userId 获取 客户 ID
        BrokerCustomerBindUserEntity brokerCustomerBindUser = brokerCustomerBindUserDao
                .getCustomerIdByUserId(Integer.valueOf(userId.toString()));
        if (brokerCustomerBindUser != null) {
            // 通过 uesrId 找到的客户 CustomerId
            Integer customerId = brokerCustomerBindUser.getBrokerCustomerId();

            // 通过 brokerId 和 客户 Id 获取顾问对客户的需求秒数
            demand = demandDao.getDemandByBrokerIdAndUserId(
                    brokerId, Long.valueOf(customerId.toString()), cityId);
        }

        return demand;
    }

    /**
     * 户型 map 替换
     *
     * @param bedroomsIds
     *            户型 ids
     */
    public String bedroomsMapping(String bedroomsIds) {

        if (bedroomsIds == null)
            return "";

        StringBuffer bedroomsBuffer = new StringBuffer();

        String[] bedroomsIdsArr = bedroomsIds.split(";");

        for (int i = 0; i < bedroomsIdsArr.length; i++) {
            String sp = "";
            if (i < bedroomsIdsArr.length - 1) {
                sp = ";";
            }
            String curS = UserTagsEntity.BEDROOMS_MAP.get(bedroomsIdsArr[i]) + sp;
            bedroomsBuffer.append(curS);
        }

        return bedroomsBuffer.toString();
    }
}
