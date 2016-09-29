package com.angejia.dw.web_service.modules.user.service.Impl;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angejia.dw.web_service.modules.user.service.UserPortraitService;
import com.angejia.dw.web_service.modules.user.service.UserRecommendService;
import com.angejia.dw.web_service.modules.inventory.service.InventoryService;

import com.angejia.dw.web_service.modules.user.dao.UserUBCFDao;
import com.angejia.dw.web_service.core.utils.array.ListUtil;
import com.angejia.dw.web_service.modules.entity.dw.dw_service.PropertyInventoryIndexEntity;
import com.angejia.dw.web_service.modules.entity.portrait.UserTagsEntity;


@Service("userRecommendService")
public class UserRecommendServiceImpl implements UserRecommendService {

    @Autowired
    private UserPortraitService userPortraitService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserUBCFDao userUBCFDao;

    /**
     * 获取用户画像推荐房源年数据
     */
    public List<Map<String, String>> getUserPortraitRecommendInventorys( String userId, String cityId, Integer offset, Integer limit) {

        // 最终结果
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        // 保存推荐数据
        List<Map<String, String>> rsResult = new ArrayList<Map<String, String>>();

        //客户画像
        List<Map<String, String>> userPortrait = userPortraitService.getUserPortraitResult(userId.toString(), cityId.toString());
        //System.out.println("用户画像:  数量: " + userPortrait.size() + " - " +  userPortrait);

        for (int i =0; i <= userPortrait.size()-1; i ++) {

            if (i >= 5) break;

            Map<String, String> userPortraitInfo = userPortrait.get(i);

            PropertyInventoryIndexEntity userPortraitSearch = new PropertyInventoryIndexEntity();

            //System.out.println("--- 画像标签 index : " + i + " - 分数 : " + userPortraitInfo.get(UserTagsEntity.TAG_GROUP_SCORE) + " ---");

            // 城市 Id
            if (userPortraitInfo.get(UserTagsEntity.CITY_TAG_CODE) != null) {
                //System.out.println(UserTagsEntity.CITY_TAG_CODE + " : " + userPortraitInfo.get(UserTagsEntity.CITY_TAG_CODE));
                userPortraitSearch.setCityId( Integer.parseInt(userPortraitInfo.get(UserTagsEntity.CITY_TAG_CODE)) );
            }

            // 区域 Id
            if (userPortraitInfo.get(UserTagsEntity.DISTRICT_TAG_CODE) != null) {
                //System.out.println(UserTagsEntity.DISTRICT_TAG_CODE + " : " + userPortraitInfo.get(UserTagsEntity.DISTRICT_TAG_CODE));
                userPortraitSearch.setDistrictId( Integer.parseInt(userPortraitInfo.get(UserTagsEntity.DISTRICT_TAG_CODE)) );
            }

            // 版块 Id
            if (userPortraitInfo.get(UserTagsEntity.BLOCK_TAG_CODE) != null) {
                //System.out.println(UserTagsEntity.BLOCK_TAG_CODE + " : "  + userPortraitInfo.get(UserTagsEntity.BLOCK_TAG_CODE));
                userPortraitSearch.setBlockId( Integer.parseInt(userPortraitInfo.get(UserTagsEntity.BLOCK_TAG_CODE)) );
            }

            // 小区 Id
            if (userPortraitInfo.get(UserTagsEntity.COMMUNITY_TAG_CODE) != null) {
                //System.out.println(UserTagsEntity.COMMUNITY_TAG_CODE + " : " + userPortraitInfo.get(UserTagsEntity.COMMUNITY_TAG_CODE));
                userPortraitSearch.setCommunityId( Integer.parseInt(userPortraitInfo.get(UserTagsEntity.COMMUNITY_TAG_CODE)) );
            }

            // 户型 id
            if (userPortraitInfo.get(UserTagsEntity.BEDROOMS_TAG_CODE) != null) {
                //System.out.println(UserTagsEntity.BEDROOMS_TAG_CODE + " : " + userPortraitInfo.get(UserTagsEntity.BEDROOMS_TAG_CODE));
                userPortraitSearch.setBedrooms( Byte.parseByte(userPortraitInfo.get(UserTagsEntity.BEDROOMS_TAG_CODE)) );
            }

            // 价格段 id
            if (userPortraitInfo.get(UserTagsEntity.PRICE_TAG_CODE) != null) {
                //System.out.println(UserTagsEntity.PRICE_TAG_CODE + " : " + userPortraitInfo.get(UserTagsEntity.PRICE_TAG_CODE));
                userPortraitSearch.setPriceTier( Byte.parseByte(userPortraitInfo.get(UserTagsEntity.PRICE_TAG_CODE)) );
            }

            userPortraitSearch.setSearchFrom("user_portrait_" + String.valueOf(i));

            rsResult.addAll(inventoryService.searchInventoryByEntity(userPortraitSearch, offset, limit));
        }

        // 去重房源
        result = ListUtil.listMapValDistinct(rsResult, "inventory_id");
/*
        // 重复房源去重
        Map<String, String> isExistsInventoryIds = new HashMap<String, String>();   // 保存已经存在的房源
        for( Map<String, String> curMap : rsResult){

            // 当前推荐出来的房源 ID
            String inventoryId = curMap.get("inventory_id");

            if (inventoryId != null) {
                // 如果 当前 inventoryRsId key 不存在
                if (! isExistsInventoryIds.containsKey(inventoryId)) {
                    // 追加到最终的结果中
                    result.add(curMap);
                }
                isExistsInventoryIds.put(inventoryId, "");

            }
        }
*/

        return result;
    }


    /**
     * 获取 UBCF 推荐的房源数据
     */
    public List<Map<String, String>> getUserUBCFRecommendInventorys(String userId, String cityId, Integer offset, Integer limit) {

        return userUBCFDao.getUserCBCFRecommendInventorys(userId, cityId, offset, limit);
    }

}
