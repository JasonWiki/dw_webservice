package com.angejia.dw.web_service.modules.user.service.Impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import java.util.Collections;
import java.util.Comparator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angejia.dw.web_service.core.utils.hbase.HBaseUtil;
import com.angejia.dw.web_service.core.utils.json.JsonUtil;

import com.angejia.dw.web_service.modules.user.service.UserPortraitService;
import com.angejia.dw.web_service.modules.user.dao.UserPortraitDao;

import com.angejia.dw.web_service.modules.entity.portrait.UserTagsEntity;

@Service("userPortraitService")
public class UserPortraitServiceImpl extends HBaseUtil implements UserPortraitService {

    @Autowired
    private UserPortraitDao userPortraitDao;



    /**
     * 获取用户画像结果
     * @param rowKey
     * @param cityId
     * @return  List<Map<标签名, 标签 ID>>
     */
    public List<Map<String, String>> getUserPortraitResult(String rowKey, String cityId) {
        System.out.println("用户画像 Service: rowKey - " + rowKey + " cityId - " + cityId);

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        // 需求标签组
        List<Map<String,String>> actionNeeds = this.getUserPortraitActionNeedsList(rowKey);
        //System.out.println("画像需求标签组: " + actionNeeds);
        if (actionNeeds.isEmpty()) return result;

        // 标签组分数
        Map<String, Map<String,String>> tagsScore  = this.getUserPortraitTagsScore(rowKey);
        //System.out.println("画像需求分数标签组: " + tagsScore);
        if (tagsScore.isEmpty()) return result;


        // 遍历 List
        for (int i =0; i <= actionNeeds.size()-1; i ++) {
            Map<String, String> tagsGroupInfo = actionNeeds.get(i);    // 标签组, 一组的 city, district ...

            Integer curTagGroupScore = 0;   // 用来累加当前标签组分数
            for(Map.Entry<String, String> curTags : tagsGroupInfo.entrySet()) {
                String curTagCode = curTags.getKey();   // 当前标签组标签的 code , 比如 city 标签
                String curTagId = curTags.getValue();   // 当前标签的 id, 比如 city 标签的 id 为 1

                //System.out.println(curTagCode + "  " + curTagId);

                // 根据出现的标签加分
                switch(curTagCode) {
                    case UserTagsEntity.CITY_TAG_CODE :
                        curTagGroupScore += Integer.valueOf(tagsScore.get(UserTagsEntity.CITY_TAG_CODE).get(curTagId));
                        break;
                    case UserTagsEntity.DISTRICT_TAG_CODE :
                        curTagGroupScore += Integer.valueOf(tagsScore.get(UserTagsEntity.DISTRICT_TAG_CODE).get(curTagId));
                        break;
                    case UserTagsEntity.BLOCK_TAG_CODE :
                        curTagGroupScore += Integer.valueOf(tagsScore.get(UserTagsEntity.BLOCK_TAG_CODE).get(curTagId));
                        break;
                    case UserTagsEntity.COMMUNITY_TAG_CODE :
                        curTagGroupScore += Integer.valueOf(tagsScore.get(UserTagsEntity.COMMUNITY_TAG_CODE).get(curTagId));
                        break;
                    case UserTagsEntity.BEDROOMS_TAG_CODE :
                        curTagGroupScore += Integer.valueOf(tagsScore.get(UserTagsEntity.BEDROOMS_TAG_CODE).get(curTagId));
                        break;
                    case UserTagsEntity.PRICE_TAG_CODE :
                        curTagGroupScore += Integer.valueOf(tagsScore.get(UserTagsEntity.PRICE_TAG_CODE).get(curTagId));
                        break;
                    default :
                }
            }

            // 分数相乘
            Integer tagsScoreTotal =  curTagGroupScore * Integer.valueOf(tagsGroupInfo.get("cnt"));
            tagsGroupInfo.put("score", tagsScoreTotal.toString());
        }

        // 按照分数排序
        Collections.sort(actionNeeds,new Comparator<Map<String, String>> (){
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
              // 降序
              return Integer.parseInt(o2.get("cnt")) - Integer.parseInt(o1.get("cnt"));
            }
        });


        // 保留当前城市标签
        for (int i =0; i <= actionNeeds.size()-1; i ++) {
            Map<String, String> tagsGroupInfo = actionNeeds.get(i);

            //System.out.println(tagsGroupInfo.get(UserTagsEntity.CITY_TAG_CODE) + " - " + cityId + " - " + tagsGroupInfo);

            if (tagsGroupInfo.get(UserTagsEntity.CITY_TAG_CODE) == null 
               || tagsGroupInfo.get(UserTagsEntity.CITY_TAG_CODE).equals(cityId) == false
             ) continue;
            // if (tagsGroupInfo.get(UserTagsEntity.CITY_TAG_CODE).equals(cityId) == false) continue;

            result.add(tagsGroupInfo);
        }


        //System.out.println(actionNeeds.size() + " :" + actionNeeds);
        //System.out.println(result.size() + " :" + result);

        
        return result;
    }



    /**
     * 获取标签分数
     * @param rowkey
     * @return Map<标签名, Map<标签 ID, 标签分数>>
     */
    public Map<String, Map<String,String>> getUserPortraitTagsScore(String rowKey) {
        Map<String, Map<String,String>> result = new HashMap<String, Map<String,String>>();

        // 获取标签分数组
        List<String> columnNames =  Arrays.asList(UserTagsEntity.CITY_TAG_CODE, UserTagsEntity.DISTRICT_TAG_CODE, UserTagsEntity.BLOCK_TAG_CODE, UserTagsEntity.COMMUNITY_TAG_CODE, UserTagsEntity.BEDROOMS_TAG_CODE, UserTagsEntity.PRICE_TAG_CODE);
        Map<String, String> tagsScore = userPortraitDao.getUserPortraitTagsByRowkey(rowKey, columnNames);

        // 标签分数
        Map<String, String> cityTagScore = new HashMap<String, String>();
        Map<String, String> districtTagScore = new HashMap<String, String>();
        Map<String, String> blockTagScore = new HashMap<String, String>();
        Map<String, String> communityTagScore = new HashMap<String, String>();
        Map<String, String> bedroomsTagScore = new HashMap<String, String>();
        Map<String, String> priceTagScore = new HashMap<String, String>();

        if (tagsScore != null) {
            if (tagsScore.get(UserTagsEntity.CITY_TAG_CODE) != null) cityTagScore = JsonUtil.JsonStrToMap(tagsScore.get(UserTagsEntity.CITY_TAG_CODE));
            if (tagsScore.get(UserTagsEntity.DISTRICT_TAG_CODE) != null) districtTagScore = JsonUtil.JsonStrToMap(tagsScore.get(UserTagsEntity.DISTRICT_TAG_CODE));
            if (tagsScore.get(UserTagsEntity.BLOCK_TAG_CODE) != null) blockTagScore = JsonUtil.JsonStrToMap(tagsScore.get(UserTagsEntity.BLOCK_TAG_CODE));
            if (tagsScore.get(UserTagsEntity.COMMUNITY_TAG_CODE) != null) communityTagScore = JsonUtil.JsonStrToMap(tagsScore.get(UserTagsEntity.COMMUNITY_TAG_CODE));
            if (tagsScore.get(UserTagsEntity.BEDROOMS_TAG_CODE) != null) bedroomsTagScore = JsonUtil.JsonStrToMap(tagsScore.get(UserTagsEntity.BEDROOMS_TAG_CODE));
            if (tagsScore.get(UserTagsEntity.PRICE_TAG_CODE) != null) priceTagScore = JsonUtil.JsonStrToMap(tagsScore.get(UserTagsEntity.PRICE_TAG_CODE));
        } 

        result.put(UserTagsEntity.CITY_TAG_CODE, cityTagScore);
        result.put(UserTagsEntity.DISTRICT_TAG_CODE, districtTagScore);
        result.put(UserTagsEntity.BLOCK_TAG_CODE, blockTagScore);
        result.put(UserTagsEntity.COMMUNITY_TAG_CODE, communityTagScore);
        result.put(UserTagsEntity.BEDROOMS_TAG_CODE, bedroomsTagScore);
        result.put(UserTagsEntity.PRICE_TAG_CODE, priceTagScore);

        return result;
    }



    /**
     * 获取标签组
     * @param rowKey
     * @return Map<标签组自增 id, Map<标签名, 标签 ID>>
     */
    public Map<String, Map<String,String>> getUserPortraitActionNeedsMap(String rowKey) {
        Map<String, Map<String,String>> result = new HashMap<String, Map<String,String>>();

        // 需求标签组
        List<String> columnNames =  Arrays.asList(UserTagsEntity.ACTION_NEEDS_CODE);
        Map<String, String> actionNeeds = userPortraitDao.getUserPortraitNeedsByRowkey(rowKey, columnNames);

        if (actionNeeds == null) return result;

        // 提取标签
        if (actionNeeds.get(UserTagsEntity.ACTION_NEEDS_CODE) != null) {
            result =  JsonUtil.JsonStrToMap(actionNeeds.get(UserTagsEntity.ACTION_NEEDS_CODE));
        }

        return result;
    }



    /**
     * 获取标签组 
     * @param rowKey
     * @return List<Map<标签名, 标签 ID>>
     */
    public List<Map<String,String>> getUserPortraitActionNeedsList(String rowKey) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        // 提取标签
        Map<String, Map<String,String>> actionNeedsMap =  this.getUserPortraitActionNeedsMap(rowKey);
        //System.out.println(actionNeedsMap);

        // 放入 list 中
        for(Map.Entry<String, Map<String, String>> tags : actionNeedsMap.entrySet()) {

            String tagGroupAutoKey = tags.getKey();  // 标签组自增 id key 

            Map<String, String> tagsGroupInfo = tags.getValue();    // 标签组, 一组的 city, district ...

            result.add(tagsGroupInfo);
        }

        //System.out.println(result);
        return result;
    }

}
