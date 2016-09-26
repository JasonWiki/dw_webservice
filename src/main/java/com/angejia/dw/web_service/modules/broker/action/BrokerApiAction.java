package com.angejia.dw.web_service.modules.broker.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.angejia.dw.web_service.modules.broker.service.BrokerUserMateInventoryService;

@Controller("brokerApiAction")
// 定义命名空间
@Namespace("/broker")
// 继承 struts2 json 包
@ParentPackage("json-default")
// 全局设置返回资源
@Result(
        // 返回资源类型为 json
        type = "json", 
        // 设置返回资源的数据变量
        params = { "root", "result" }
)
public class BrokerApiAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(BrokerApiAction.class);
    private static final int pageSize = Integer.MAX_VALUE;

    @Autowired
    private BrokerUserMateInventoryService brokerUserMateInventoryService;

    private String userId;
    private String brokerId;
    private String cityId;
    private Integer offset;

    private Map<String, Object> result = new HashMap<String, Object>();

    // 测试
    @Action(value="list", results={@Result(location="list.jsp")})
    public String list() {
        return SUCCESS;
    }

    // 顾问智能配盘接口
    @Action("broker-user-mate-inventory")
    public String brokerUserMateInventory() {

        Long brokerId = Long.valueOf(this.getBrokerId());
        Long userId = Long.valueOf(this.getUserId());
        Long cityId = Long.valueOf(this.getCityId());

        System.out.println("顾问接口 action:  顾问 id: " + brokerId + " 用户 id: " + userId + " 城市 id: " + cityId);

        // 顾问配盘数据
        List<Map<String, String>> brokerUserMateInventorys = brokerUserMateInventoryService.getBrokerUserMateInventory(brokerId, userId, cityId);

        // 外层基本信息 
        Map<String, Object> baseResult = new HashMap<String, Object>();
        baseResult.put("brokerId", this.getBrokerId());
        baseResult.put("userId", this.getUserId());
        baseResult.put("cityId", this.getCityId()); 
        baseResult.put("total", Integer.toString(brokerUserMateInventorys.size()));

        // 推荐实体数据
        Map<String, Object> dataResult = new HashMap<String, Object>();
        dataResult.put("list", brokerUserMateInventorys);

        // 组装
        baseResult.put("rec", dataResult);

        result.put("brokerMateInventoryInfo",baseResult);

        return SUCCESS;
    }


    private String output(String status, String msg) {
        result.put("status", status);
        if (msg != null) {
            result.put("msg", msg);
        }
        return SUCCESS;
    }



    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrokerId() {
        return brokerId;
    }
    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
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

    /**
     * 处理 json 格式数据
     * @param result
     */
    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
    public Map<String, Object> getResult() {
        return result;
    }


}
