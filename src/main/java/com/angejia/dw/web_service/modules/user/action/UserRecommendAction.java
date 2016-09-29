package com.angejia.dw.web_service.modules.user.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

// struts2 注解
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

// spring 注解
import org.springframework.stereotype.Controller;

import com.angejia.dw.web_service.core.base.action.BaseAction;
import com.angejia.dw.web_service.core.utils.array.ListUtil;
// Service
import com.angejia.dw.web_service.modules.user.service.UserRecommendService;

@Controller("userRecommendAction")

@Namespace("/user/recommend") // 定义命名空间

@ParentPackage("json-default") // 继承 struts2 json 包

public class UserRecommendAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(UserRecommendAction.class);
    private static final int pageSize = Integer.MAX_VALUE;


    // 入参
    private String userId;
    private String cityId;
    private String offset = "0";
    private String limit = "10";


    @Autowired
    private UserRecommendService userRecommendService;

    // 保存用户推荐房源数据
    private Map<String, Object> userRecommendInventorys = new HashMap<String, Object>();


    // 客户画像接口
    @Action(value="user-recommend-inventorys", results={ @Result(type="json",params={"root", "userRecommendInventorys" }) })
    public String userRecommendInventorys() {

        // 拆解 用户 ids
        String userId = this.getUserId();
        String cityId = this.getCityId();

        // 保存用户画像数据
        List<Map<String, String>> recommendInventorys = new ArrayList<Map<String, String>>();

        if (userId != null && cityId != null) {
            // 客户画像推荐房源
            List<Map<String, String>> userPortraitRecommendInventorys = userRecommendService.getUserPortraitRecommendInventorys(userId, cityId, 0 ,20);

            // UBCF 推荐房源数据
            List<Map<String, String>> userUBCFRecommendInventorys =  userRecommendService.getUserUBCFRecommendInventorys(userId, cityId, 0 , 40);

            // 
            for (int i=0; i <= userPortraitRecommendInventorys.size() -1 ; i++) {
                Map<String, String> upri =  userPortraitRecommendInventorys.get(i);

                Map<String, String> recMap = new HashMap<String, String>();
                recMap.put("inventory_rs_id", upri.get("inventory_id"));
                recMap.put("inventory_rs_pf", upri.get("search_from"));
                recMap.put("inventory_rs_sort", upri.get("inventory_rs_status"));
                recMap.put("type", "1");
                recommendInventorys.add(recMap);
            }

            for (int i=0; i <= userUBCFRecommendInventorys.size() -1 ; i++) {
                Map<String, String> upri =  userUBCFRecommendInventorys.get(i);

                Map<String, String> recMap = new HashMap<String, String>();
                recMap.put("inventory_rs_id", upri.get("inventory_id"));
                recMap.put("inventory_rs_pf", upri.get("relation_user_pf"));
                recMap.put("inventory_rs_sort", upri.get("inventory_rs_status"));
                recMap.put("type", "2");
                recommendInventorys.add(recMap);
            }

        }

        // 房源去重
        final List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        result.addAll(ListUtil.listMapValDistinct(recommendInventorys, "inventory_rs_id"));

        // 外层基本信息 
        Map<String, Object> baseResult = new HashMap<String, Object>();
        baseResult.put("userId", userId);
        baseResult.put("cityId", cityId);
        baseResult.put("total", Integer.toString(recommendInventorys.size()));
        baseResult.put("rec", new HashMap<String, Object>() {
            {
                put("list", result);  
            }
        } );

        userRecommendInventorys.put("userPortraitInfo",baseResult);
 
        return SUCCESS;
    }



    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getCityId() {
        return cityId;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }


    public String getOffset() {
        return offset;
    }
    public void setOffset(String offset) {
        this.offset = offset;
    }


    public Map<String, Object> getUserRecommendInventorys() {
        return userRecommendInventorys;
    }
    public void setUserRecommendInventorys(Map<String, Object> userRecommendInventorys) {
        this.userRecommendInventorys = userRecommendInventorys;
    }

}
