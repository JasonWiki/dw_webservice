package com.angejia.dw.web_service.modules.user.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

//struts2 注解
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

//spring 注解
import org.springframework.stereotype.Controller;

import com.angejia.dw.web_service.core.base.action.BaseAction;

import com.angejia.dw.web_service.modules.user.service.UserPushService;

@Controller("userPushAction")

@Namespace("/user/user-push") // 定义命名空间

@ParentPackage("json-default") // 继承 struts2 json 包

public class UserPushAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UserPushAction.class);

    // 入参
    private String userIds;
    private String cityId;
    private Integer offset;
    private Integer limit = 3;

    @Autowired
    private UserPushService userPushService;
    
    
    // 保存 push 详细数据
    private Map<String, Object> userPush = new HashMap<String, Object>();

    // 获取用户 push 信息
    @Action(value="get-user-info", results={ @Result(type="json",params={"root", "userPush" }) })
    public String getUserInfo() {

        // 拆解 用户 ids
        String userIds = this.getUserIds();
        String cityId = this.getCityId();

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        
        if (userIds != null && cityId != null) {
            String[] userIdsArr = userIds.split(",");

            // 单个画像
            if (userIdsArr.length == 1) {
                Map<String, String> pushInfo = userPushService.getUserInfoByUserId(userIdsArr[0], cityId, 3);
                if ( !pushInfo.isEmpty() ) {
                    data.add(pushInfo);
                }

            // 批量画像
            } else if(userIdsArr.length > 1) {
                for (String userId: userIdsArr) {
                    Map<String, String> pushInfo = userPushService.getUserInfoByUserId(userId, cityId, 3);
                    if ( !pushInfo.isEmpty() ) {
                        data.add(pushInfo);
                    }
                }
            }
        }

        // 外层基本信息 
        Map<String, Object> baseResult = new HashMap<String, Object>();
        baseResult.put("userIds", userIds);
        baseResult.put("cityId", cityId);
        baseResult.put("rec", data);
        baseResult.put("total", Integer.toString(data.size()));
        userPush.put("userPushInfo",baseResult);
        return SUCCESS;
    }
    
    
    public Map<String, Object> getUserPush() {
        return userPush;
    }
    public void setUserPush(Map<String, Object> userPush) {
        this.userPush = userPush;
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
    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
