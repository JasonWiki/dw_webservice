package com.angejia.dw.web_service.modules.inventory.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angejia.dw.web_service.modules.inventory.service.InventoryService;
import com.angejia.dw.web_service.modules.entity.dw.dw_service.ProertyInventoryIndexEntity;
import com.angejia.dw.web_service.modules.inventory.dao.PropertyInventoryIndexDao;

@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private PropertyInventoryIndexDao proertyInventoryIndexDao;

    public List<Map<String, String>> searchInventoryByEntity(ProertyInventoryIndexEntity entity, Integer offset, Integer limit) {

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        // 限制条件
        entity.setStatus( ProertyInventoryIndexEntity.STATUS ); // 在售
        // entity.setSurveyStatus( ProertyInventoryIndexEntity.SURVEY_STATUS );    // 已实勘
 
        // 过滤，组合结果集
        List<ProertyInventoryIndexEntity> dataList =  proertyInventoryIndexDao.getInventorysByEntity(entity, offset, limit);

        for(ProertyInventoryIndexEntity t : dataList) {

            Map<String, String> inventoryInfo = new HashMap<String, String>();
            inventoryInfo.put("inventory_id", t.getInventoryId().toString());
            inventoryInfo.put("city_id", t.getCityId().toString());
            inventoryInfo.put("district_id", t.getDistrictId().toString());
            inventoryInfo.put("block_id", t.getBlockId().toString());
            inventoryInfo.put("community_id", t.getCommunityId().toString());
            inventoryInfo.put("bedrooms_id", t.getBedrooms().toString());
            inventoryInfo.put("price", t.getPrice().toString());
            inventoryInfo.put("price_tier", t.getPriceTier().toString());
            inventoryInfo.put("inventory_type", t.getInventoryType().toString());
            
            if (entity.getSearchFrom() != null) inventoryInfo.put("search_from", entity.getSearchFrom().toString());
            result.add(inventoryInfo);

            /*
            System.out.println(
               "搜索房源: "
               + t.getInventoryId() 
               + " - " + t.getCityId()
               + " - " + t.getDistrictId()
               + " - " + t.getBlockId()
               + " - " + t.getCommunityId()
               + " - " + t.getBedrooms()
               + " - " + t.getPrice()
               + " - " + t.getPriceTier()
               + " - " + t.getStatus()
               + " - " + t.getSurveyStatus()
               + " - " + t.getIsReal()
               + " - " + t.getInventoryType()
               + " - " + t.getInventoryTypeId()
            );
            */
        }

        return result;
    }
}
