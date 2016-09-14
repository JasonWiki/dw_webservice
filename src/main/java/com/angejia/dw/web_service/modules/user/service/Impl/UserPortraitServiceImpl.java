package com.angejia.dw.web_service.modules.user.service.Impl;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angejia.dw.web_service.core.utils.hbase.HBaseUtil;
import com.angejia.dw.web_service.core.utils.json.JsonUtil;

import com.angejia.dw.web_service.modules.user.service.UserPortraitService;

import com.angejia.dw.web_service.modules.user.dao.UserPortraitDao;

@Service("userPortraitService")
public class UserPortraitServiceImpl extends HBaseUtil implements UserPortraitService {

    @Autowired
    private UserPortraitDao userPortraitDao;

    public Map<String, String> getUserNeedsSort(String Rowkey, String cityId) {
        Map<String, String> result = new HashMap<String, String>();

        // 获取需求标签组
        Map<String, String> needs = userPortraitDao.getUserPortraitNeedsByRowkey(Rowkey);

        // 获取标签分数组
        List<String> columnNames =  Arrays.asList("city", "district", "block", "community", "bedrooms", "price");
        Map<String, String> tagsScore = userPortraitDao.getUserPortraitTagsByRowkey(Rowkey, columnNames);

        if (needs == null || tagsScore == null) return result;

        // 标签分数
        Map<String, String> cityTagScore = new HashMap<String, String>();
        Map<String, String> districtTagScore = new HashMap<String, String>();
        Map<String, String> blockTagScore = new HashMap<String, String>();
        Map<String, String> communityTagScore = new HashMap<String, String>();
        Map<String, String> bedroomsTagScore = new HashMap<String, String>();
        Map<String, String> priceTagScore = new HashMap<String, String>();

        if (tagsScore.get("city") != null) cityTagScore = JsonUtil.JsonStrToMap(tagsScore.get("city"));
        if (tagsScore.get("district") != null) districtTagScore = JsonUtil.JsonStrToMap(tagsScore.get("district"));
        if (tagsScore.get("block") != null) blockTagScore = JsonUtil.JsonStrToMap(tagsScore.get("block"));
        if (tagsScore.get("community") != null) communityTagScore = JsonUtil.JsonStrToMap(tagsScore.get("community"));
        if (tagsScore.get("bedrooms") != null) bedroomsTagScore = JsonUtil.JsonStrToMap(tagsScore.get("bedrooms"));
        if (tagsScore.get("price") != null) priceTagScore = JsonUtil.JsonStrToMap(tagsScore.get("price"));

        // 标签组分数
        Map<String, Map<String,String>> actionNeeds = new HashMap<String, Map<String,String>>();
        if (needs.get("actionNeeds") != null) actionNeeds =  JsonUtil.JsonStrToMap(needs.get("actionNeeds"));

        // 遍历标签组
        for(Map.Entry<String, Map<String, String>> tags : actionNeeds.entrySet()) {

            String key = tags.getKey();
            Map<String, String> tagsInfo = tags.getValue();

            int curTagScore = 0;
            for(Map.Entry<String, String> curTagInfo : tagsInfo.entrySet()) {
                String curTag = curTagInfo.getKey();

                switch(curTag) {
                    case "city" :
                        curTagScore += 1;
                        break;
                }
                     
            }
            
            System.out.println("Key = " + key + ", Value = " + tagsInfo);  
        }
        
        //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  


        System.out.println("-----");
        System.out.println(cityTagScore);
        System.out.println(districtTagScore);
        System.out.println(blockTagScore);
        System.out.println(communityTagScore);
        System.out.println(bedroomsTagScore);
        System.out.println(priceTagScore);

        return result;
    }

    public void getUserPortrait(String rowkey, String cityId) {
        // 获取需求标签组
        Map<String, String> needs = userPortraitDao.getUserPortraitNeedsByRowkey(rowkey);

        // 获取标签分数组
        List<String> columnNames =  Arrays.asList("city", "district", "block", "community", "bedrooms", "price");
        Map<String, String> tagsScore = userPortraitDao.getUserPortraitTagsByRowkey(rowkey, columnNames);

        // 
    }
}
