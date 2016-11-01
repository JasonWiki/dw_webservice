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

        // 待 push 队列
        List<Map<String, String>> pushQueue = new ArrayList<Map<String, String>>();
        
        // push 队列长度
        Integer pushQueueLength = 5;

        // 获取客户画像
        List<Map<String, String>> userPortraitList = userPortraitService.getUserPortraitResult(userId, cityId);

       
        if ( !userPortraitList.isEmpty() ) {
            // 遍历画像
            for (int i = 0; i <= userPortraitList.size() - 1; i ++) {

                if (pushQueue.size() >= pushQueueLength) {
                    break;
                }
                //if (i > 5) break;

                // 当前画像
                Map<String, String> userPortrait  = userPortraitList.get(i);

                String userPortraitSortPoint = "push_user_portrait_sort_" + i;

                // 搜索推荐房源
                List<Map<String, String>> rsInventorys = userRecommendService.getRecommendInventorysByTags(userPortrait, userPortraitSortPoint, 0, limit + 20);

                // 当前画像搜索到的房源条数
                Integer rsInventorysSize = rsInventorys.size();
                // 候选房源最大选取条数
                Integer maxRsInventorysNum = 0 ;
 
                if (rsInventorysSize > maxRsInventorysNum ) {

                    Integer fromIndex = 0;
                    Integer toIndex = limit;

                    if ( rsInventorysSize <= limit) {
                        fromIndex = 0;
                        toIndex = rsInventorysSize;
                    } else {
                        // 随机抽取 limit 条房源数据返回
                        fromIndex = IntegerUtil.generateRandom(0 , rsInventorysSize - limit + 1) ;
                        toIndex = fromIndex + limit ;
                    }

                    //System.out.println(rsInventorysSize);
                    //System.out.println("fromIndex: " + fromIndex + ", toIndex: " + toIndex);

                    // 截取一段范围的房源数据
                    List<Map<String, String>>  rangeInventorys = rsInventorys.subList(fromIndex, toIndex);
                    //List<Map<String, String>>  rangeInventorys = rsInventorys;

                    // 单个 Push 信息
                    Map<String, String> userPushInfo = new HashMap<String, String>();

                    // 加载当前客户画像
                    userPushInfo.putAll(userPortrait);

                    List inventoryIds = new ArrayList<String>();
                    for( Map<String, String> curMap : rangeInventorys){
                        String inventoryId = curMap.get("inventory_id");
                        inventoryIds.add(inventoryId);
                    }

                    userPushInfo.put("inventorys", StringUtils.join(inventoryIds,",") );
                    userPushInfo.put("userPortraitSortPoint", userPortraitSortPoint);

                    pushQueue.add(userPushInfo);
                    //break;
                } 
            }

        }


        // 最终推荐结果
        Map<String, String> result = new HashMap<String, String>();

        // 队列随机推荐
        Integer pushListSize = pushQueue.size();
        if (pushListSize > 0) {
            result = pushQueue.get(IntegerUtil.generateRandom(0 , pushListSize));
        }
 
        return result;
    }

}
