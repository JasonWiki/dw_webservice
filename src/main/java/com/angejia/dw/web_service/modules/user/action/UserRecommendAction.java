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

    // 用户推荐房源接口, cbcf
    @Action(value = "user-recommend-inventorys", results = {
            @Result(type = "json", params = { "root", "userRecommendInventorys" }) })
    public String userRecommendInventorys() {
        // 拆解 用户 id
        String userId = this.getUserId();
        String cityId = this.getCityId();

        // 保存推荐数据
        List<Map<String, String>> recommendInventorys = new ArrayList<Map<String, String>>();
        final List<Map<String, String>> recommendMarketingInventories = new ArrayList<Map<String, String>>();
        final List<Map<String, String>> recommendInventories = new ArrayList<Map<String, String>>();

        if (userId != null && cityId != null) {
            // CBCF 推荐房源
            List<Map<String, String>> userCBCFRecommendInventorys = userRecommendService
                    .getUserCBCFRecommendInventorys(userId, cityId, 100);

            // 组合到推荐房源数据中
            for (int i = 0; i < userCBCFRecommendInventorys.size(); i++) {
                Map<String, String> userCBCF = userCBCFRecommendInventorys.get(i);
                final String inventoryId = userCBCF.get("inventory_id");

                Map<String, String> recMap = new HashMap<String, String>();
                recMap.put("inventory_rs_id", userCBCF.get("inventory_id"));
                recMap.put("type", "1");
                recommendInventories.add(new HashMap<String, String>() {
                    {
                        put("inventoryId", inventoryId);
                    }
                });
                recommendInventorys.add(recMap);
            }

            // CBCF 推荐营销房源
            List<Map<String, String>> cbcfRecommendMarketingInventorys = userRecommendService
                    .getUserCBCFRecommendMarketingInventorys(userId, cityId, 100);

            // 组合到推荐房源数据中
            for (int i = 0; i < cbcfRecommendMarketingInventorys.size(); i++) {
                Map<String, String> marketingInventory = cbcfRecommendMarketingInventorys.get(i);
                Map<String, String> recMap = new HashMap<String, String>();
                recMap.put("inventoryId", marketingInventory.get("inventory_id"));
                recommendMarketingInventories.add(recMap);
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
        });

        userRecommendInventorys.put("userInventoryInfo", baseResult);

        userRecommendInventorys.put("userId", userId);
        userRecommendInventorys.put("cityId", cityId);
        userRecommendInventorys.put("recommendInventories", new HashMap<String, Object>() {
            {
                put("total", recommendInventories.size());
                put("inventoryList", recommendInventories);
            }
        });
        userRecommendInventorys.put("recommendMarketingInventories", new HashMap<String, Object>() {
            {
                put("total", recommendMarketingInventories.size());
                put("inventoryList", recommendMarketingInventories);
            }
        });
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
