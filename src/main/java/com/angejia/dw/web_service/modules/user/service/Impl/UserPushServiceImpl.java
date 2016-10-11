package com.angejia.dw.web_service.modules.user.service.Impl;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angejia.dw.web_service.core.utils.number.IntegerUtil;
import com.angejia.dw.web_service.modules.user.service.UserPortraitService;
import com.angejia.dw.web_service.modules.user.service.UserPushService;
import com.angejia.dw.web_service.modules.user.service.UserRecommendService;

@Service("userPushService")
public class UserPushServiceImpl implements UserPushService {

    @Autowired
    private UserRecommendService userRecommendService;

    @Autowired
    private UserPortraitService userPortraitService;


    /**
     * 获取用户 Push 信息
     */
    public Map<String, String> getUserInfoByUserId(String userId, String cityId, Integer limit) {

        Map<String, String> result = new HashMap<String, String>();

        // 获取客户画像
        List<Map<String, String>> userPortraitRs = userPortraitService.getUserPortraitResult(userId, cityId);
        if ( !userPortraitRs.isEmpty() ) {
            // 排名第一的客户画像
            Map<String, String> topOneUserPortrait  = userPortraitRs.get(0);
            result.putAll(topOneUserPortrait);

            Integer offset = IntegerUtil.generateRandom(0, 3);
            // 搜索推荐数据
            List<Map<String, String>> rsInventorys =  userRecommendService.getRecommendInventorysByTags(topOneUserPortrait, "push" ,0, 3);

            List inventoryIds = new ArrayList<String>();
            for( Map<String, String> curMap : rsInventorys){
                String inventoryId = curMap.get("inventory_id");
                inventoryIds.add(inventoryId);
            }

            result.put("inventorys", StringUtils.join(inventoryIds,",") );
        }

        return result;
    }

}
