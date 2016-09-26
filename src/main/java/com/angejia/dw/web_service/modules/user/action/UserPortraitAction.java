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

@Controller("userPortraitAction")

@Namespace("/user/user-portrait") // 定义命名空间

@ParentPackage("json-default") // 继承 struts2 json 包

public class UserPortraitAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(UserPortraitAction.class);
    private static final int pageSize = Integer.MAX_VALUE;


    // 入参
    private String userIds;
    private String cityId;
    private Integer offset;


    @Autowired
    private UserPortraitService userPortraitService;

    // 保存客户画像数据
    private Map<String, Object> userPortrait = new HashMap<String, Object>();


    // 客户画像接口
    @Action(value="get-user-portrait", results={ @Result(type="json",params={"root", "userPortrait" }) })
    public String getUserPortraitByUserIds() {

        // 拆解 用户 ids
        String userIds = this.getUserIds();
        String cityId = this.getCityId();

        
        // 保存用户画像数据
        final List<Map<String, String>> usersPortrait = new ArrayList<Map<String, String>>();

        if (userIds != null && cityId != null) {
            String[] userIdsArr = userIds.split(",");

            System.out.println("用户数量: " + userIdsArr.length);

            

            // 单个画像
            if (userIdsArr.length == 1) {
                List<Map<String, String>> userPortrait = userPortraitService.getUserPortraitResult(userIdsArr[0], cityId);
                usersPortrait.addAll(userPortrait);

            // 批量画像
            } else if(userIdsArr.length > 1) {
                for (String userId: userIdsArr) {
                    List<Map<String, String>> userPortrait =  userPortraitService.getUserPortraitResult(userId, cityId);
                    if (userPortrait.isEmpty() == false && userPortrait.size() > 0) {
                        usersPortrait.add(userPortrait.get(0));
                    }
                }
            }

            // 推荐数据实体
        }

        // 外层基本信息 
        Map<String, Object> baseResult = new HashMap<String, Object>();
        baseResult.put("userId", userIds);
        baseResult.put("cityId", cityId);
        baseResult.put("total", Integer.toString(usersPortrait.size()));
        baseResult.put("rec", new HashMap<String, Object>() {
            {
                put("list", usersPortrait);  
            }
        } );

        userPortrait.put("userPortraitInfo",baseResult);
 
        return SUCCESS;
    }



    public String getUserIds() {
        return userIds;
    }
    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }


    public String getCityId() {
        return cityId;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }


    public Integer getOffset() {
        return offset;
    }
    public void setOffset(Integer offset) {
        this.offset = offset;
    }


    public Map<String, Object> getUserPortrait() {
        return userPortrait;
    }
    public void setUserPortrait(Map<String, Object> userPortrait) {
        this.userPortrait = userPortrait;
    }

}
