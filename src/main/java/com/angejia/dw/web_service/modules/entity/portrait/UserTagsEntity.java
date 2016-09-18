package com.angejia.dw.web_service.modules.entity.portrait;

import java.util.Map;
import java.util.HashMap;

public class UserTagsEntity {

    // 标签分数
    public static final String CITY_TAG_CODE = "city";
    public static final String DISTRICT_TAG_CODE = "district";
    public static final String BLOCK_TAG_CODE = "block";
    public static final String COMMUNITY_TAG_CODE = "community";
    public static final String BEDROOMS_TAG_CODE = "bedrooms";
    public static final String PRICE_TAG_CODE = "price";
    
    // 标签组
    public static final String ACTION_NEEDS_CODE = "actionNeeds";
    public static final String TAG_GROUP_SCORE = "score";   // 标签组分数
    public static final String TAG_GROUP_CNT = "cnt";       // 标签组次数

    // 户型 映射
    public static Map<String, String> BEDROOMS_MAP = new HashMap<String, String>() {
        {
            put("7", "6"); 
            
            put("6", "5"); 
            put("5", "4");  
            put("4", "3");  
            put("3", "2");
            put("2", "1");
            
            put("1", "0");
        }
    };
}
