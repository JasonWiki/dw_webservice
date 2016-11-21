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
     * 获取用户画像推荐房源数据
     * 
     * @param userId
     * @param cityId
     * @param limit
     *            获取推荐条数
     * @return
     */
    public List<Map<String, String>> getUserCBCFRecommendInventorys(String userId, String cityId, Integer limit) {
        // 最终结果
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        // 保存推荐数据
        List<Map<String, String>> rsResult = new ArrayList<Map<String, String>>();

        // 客户画像
        List<Map<String, String>> userPortrait = userPortraitService.getUserPortraitResult(userId.toString(),
                cityId.toString());
        // System.out.println("用户画像: 数量: " + userPortrait.size() + " - " +
        // userPortrait);

        for (int i = 0; i < userPortrait.size(); i++) {

            if (rsResult.size() >= limit || i >= 5)
                break;

            // 客户画像
            Map<String, String> userPortraitInfo = userPortrait.get(i);
            // System.out.println(userPortraitInfo);

            // 客户画像标签推荐的房源数据
            List<Map<String, String>> userPortraitRsInventorys = this.getRecommendInventorysByTags(userPortraitInfo,
                    "user_portrait_" + String.valueOf(i), 0, 20);

            rsResult.addAll(userPortraitRsInventorys);

        }

        // 去重房源
        result = ListUtil.listMapValDistinct(rsResult, "inventory_id");

        return result;
    }

    /**
     * 获取用户画像推荐房源数据
     * 
     * @param userId
     * @param cityId
     * @param limit
     *            获取推荐条数
     * @return
     */
    public List<Map<String, String>> getUserCBCFRecommendMarketingInventorys(String userId, String cityId,
            Integer limit) {
        // 最终结果
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        // 保存推荐数据
        List<Map<String, String>> rsResult = new ArrayList<Map<String, String>>();

        // 客户画像
        List<Map<String, String>> userPortrait = userPortraitService.getUserPortraitResult(userId.toString(),
                cityId.toString());
        // System.out.println("用户画像: 数量: " + userPortrait.size() + " - " +
        // userPortrait);

        for (int i = 0; i < userPortrait.size(); i++) {

            if (rsResult.size() >= limit || i >= 5)
                break;

            // 客户画像
            Map<String, String> userPortraitInfo = userPortrait.get(i);
            // System.out.println(userPortraitInfo);

            // 客户画像标签推荐的房源数据
            List<Map<String, String>> userPortraitRsInventorys = this.getRecommendMarketingInventorysByTags(userPortraitInfo,
                    "user_portrait_" + String.valueOf(i), 0, 20);

            rsResult.addAll(userPortraitRsInventorys);

        }

        // 去重房源
        result = ListUtil.listMapValDistinct(rsResult, "inventory_id");

        return result;
    }

    /**
     * 根据标签获取房源数据
     * 
     * @param searchTags
     *            搜索标签
     * @param searchFrom
     *            搜索来源
     * @param offset
     * @param limit
     * @return List<Map<String, String>>
     */
    public List<Map<String, String>> getRecommendMarketingInventorysByTags(Map<String, String> searchTags,
            String searchFrom, Integer offset, Integer limit) {
        PropertyInventoryIndexEntity propertyInventoryIndexEntity = new PropertyInventoryIndexEntity();

        propertyInventoryIndexEntity.setIsMarketing(Byte.parseByte("1"));

        // 城市 Id
        if (searchTags.get(UserTagsEntity.CITY_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.CITY_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.CITY_TAG_CODE));
            propertyInventoryIndexEntity.setCityId(Integer.parseInt(searchTags.get(UserTagsEntity.CITY_TAG_CODE)));
        }

        // 区域 Id
        if (searchTags.get(UserTagsEntity.DISTRICT_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.DISTRICT_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.DISTRICT_TAG_CODE));
            propertyInventoryIndexEntity
                    .setDistrictId(Integer.parseInt(searchTags.get(UserTagsEntity.DISTRICT_TAG_CODE)));
        }

        // 版块 Id
        if (searchTags.get(UserTagsEntity.BLOCK_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.BLOCK_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.BLOCK_TAG_CODE));
            propertyInventoryIndexEntity.setBlockId(Integer.parseInt(searchTags.get(UserTagsEntity.BLOCK_TAG_CODE)));
        }

        // 小区 Id
        if (searchTags.get(UserTagsEntity.COMMUNITY_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.COMMUNITY_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.COMMUNITY_TAG_CODE));
            propertyInventoryIndexEntity
                    .setCommunityId(Integer.parseInt(searchTags.get(UserTagsEntity.COMMUNITY_TAG_CODE)));
        }

        // 户型 id
        if (searchTags.get(UserTagsEntity.BEDROOMS_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.BEDROOMS_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.BEDROOMS_TAG_CODE));
            if (searchTags.get(UserTagsEntity.BEDROOMS_TAG_CODE).length() <= 2) {
                propertyInventoryIndexEntity
                        .setBedrooms(Byte.parseByte(searchTags.get(UserTagsEntity.BEDROOMS_TAG_CODE)));
            }
        }

        // 价格段 id
        if (searchTags.get(UserTagsEntity.PRICE_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.PRICE_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.PRICE_TAG_CODE));
            if (searchTags.get(UserTagsEntity.PRICE_TAG_CODE).length() <= 2) {
                propertyInventoryIndexEntity
                        .setPriceTier(Byte.parseByte(searchTags.get(UserTagsEntity.PRICE_TAG_CODE)));
            }
        }

        // 搜索来源
        propertyInventoryIndexEntity.setSearchFrom(searchFrom);

        // 搜索房源数据
        return inventoryService.searchInventoryByEntity(propertyInventoryIndexEntity, offset, limit);
    }

    /**
     * 根据标签获取房源数据
     * 
     * @param searchTags
     *            搜索标签
     * @param searchFrom
     *            搜索来源
     * @param offset
     * @param limit
     * @return List<Map<String, String>>
     */
    public List<Map<String, String>> getRecommendInventorysByTags(Map<String, String> searchTags, String searchFrom,
            Integer offset, Integer limit) {
        PropertyInventoryIndexEntity propertyInventoryIndexEntity = new PropertyInventoryIndexEntity();

        propertyInventoryIndexEntity.setIsMarketing(Byte.parseByte("0"));
        // 城市 Id
        if (searchTags.get(UserTagsEntity.CITY_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.CITY_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.CITY_TAG_CODE));
            propertyInventoryIndexEntity.setCityId(Integer.parseInt(searchTags.get(UserTagsEntity.CITY_TAG_CODE)));
        }

        // 区域 Id
        if (searchTags.get(UserTagsEntity.DISTRICT_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.DISTRICT_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.DISTRICT_TAG_CODE));
            propertyInventoryIndexEntity
                    .setDistrictId(Integer.parseInt(searchTags.get(UserTagsEntity.DISTRICT_TAG_CODE)));
        }

        // 版块 Id
        if (searchTags.get(UserTagsEntity.BLOCK_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.BLOCK_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.BLOCK_TAG_CODE));
            propertyInventoryIndexEntity.setBlockId(Integer.parseInt(searchTags.get(UserTagsEntity.BLOCK_TAG_CODE)));
        }

        // 小区 Id
        if (searchTags.get(UserTagsEntity.COMMUNITY_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.COMMUNITY_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.COMMUNITY_TAG_CODE));
            propertyInventoryIndexEntity
                    .setCommunityId(Integer.parseInt(searchTags.get(UserTagsEntity.COMMUNITY_TAG_CODE)));
        }

        // 户型 id
        if (searchTags.get(UserTagsEntity.BEDROOMS_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.BEDROOMS_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.BEDROOMS_TAG_CODE));
            if (searchTags.get(UserTagsEntity.BEDROOMS_TAG_CODE).length() <= 2) {
                propertyInventoryIndexEntity
                        .setBedrooms(Byte.parseByte(searchTags.get(UserTagsEntity.BEDROOMS_TAG_CODE)));
            }
        }

        // 价格段 id
        if (searchTags.get(UserTagsEntity.PRICE_TAG_CODE) != null) {
            // System.out.println(UserTagsEntity.PRICE_TAG_CODE + " : " +
            // userPortraitInfo.get(UserTagsEntity.PRICE_TAG_CODE));
            if (searchTags.get(UserTagsEntity.PRICE_TAG_CODE).length() <= 2) {
                propertyInventoryIndexEntity
                        .setPriceTier(Byte.parseByte(searchTags.get(UserTagsEntity.PRICE_TAG_CODE)));
            }
        }

        // 搜索来源
        propertyInventoryIndexEntity.setSearchFrom(searchFrom);

        // 搜索房源数据
        return inventoryService.searchInventoryByEntity(propertyInventoryIndexEntity, offset, limit);
    }
}
