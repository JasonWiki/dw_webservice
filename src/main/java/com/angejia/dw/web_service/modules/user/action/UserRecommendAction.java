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

// Service
import com.angejia.dw.web_service.modules.user.service.UserPortraitService;

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
    private UserPortraitService userPortraitService;

    // 保存用户推荐房源数据
    private Map<String, Object> userRecommendInventorys = new HashMap<String, Object>();


    // 客户画像接口
    @Action(value="user-recommend-inventorys", results={ @Result(type="json",params={"root", "userRecommendInventorys" }) })
    public String userRecommendInventorys() {

        // 拆解 用户 ids
        String userIds = this.getUserId();
        String cityId = this.getCityId();

        // 保存用户画像数据
        final List<Map<String, String>> recommendInventorys = new ArrayList<Map<String, String>>();

        if (userIds != null && cityId != null) {
            String[] userIdsArr = userIds.split(",");

            System.out.println("用户数量: " + userIdsArr.length);


            // 单个画像
            if (userIdsArr.length == 1) {
                List<Map<String, String>> userPortrait = userPortraitService.getUserPortraitResult(userIdsArr[0], cityId);
                recommendInventorys.addAll(userPortrait);

            // 批量画像
            } else if(userIdsArr.length > 1) {
                for (String userId: userIdsArr) {
                    List<Map<String, String>> userPortrait =  userPortraitService.getUserPortraitResult(userId, cityId);
                    if (userPortrait.isEmpty() == false && userPortrait.size() > 0) {
                        recommendInventorys.add(userPortrait.get(0));
                    }
                }
            }

        }
   
        // 外层基本信息 
        Map<String, Object> baseResult = new HashMap<String, Object>();
        baseResult.put("userId", userIds);
        baseResult.put("cityId", cityId);
        baseResult.put("total", Integer.toString(recommendInventorys.size()));
        baseResult.put("rec", new HashMap<String, Object>() {
            {
                put("list", recommendInventorys);  
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
