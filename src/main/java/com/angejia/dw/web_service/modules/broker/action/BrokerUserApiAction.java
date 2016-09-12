package com.angejia.dw.web_service.modules.broker.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.springframework.beans.factory.annotation.Autowired;

// 定义命名空间
@Namespace("/broker")

// 继承 struts2 json 包
@ParentPackage("json-default")

// 设置返回资源
@Result(
        // 返回资源类型为 json
        type = "json", 
        // 设置返回资源的数据变量
        params = { "root", "result" }
)

public class BrokerUserApiAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private static final int pageSize = Integer.MAX_VALUE;

    private Integer userId;
    private Integer brokerId;
    private Integer page;
    private Map<String, Object> result = new HashMap<String, Object>();

    // 测试
    @Action(value="list", results={@Result(location="list.jsp")})
    public String list() {
        return SUCCESS;
    }

    // 顾问智能配盘接口
    @Action("broker-user-mate-inventory")
    public String brokerUserMateInventory() {
        
        
        System.out.println(this.getUserId());
        System.out.println(this.getBrokerId());

        result.put("userId", this.getUserId());
        result.put("brokerId", this.getBrokerId());
        result.put("historySqls", "show tables;");
        return this.output("ok", "123");
        //return SUCCESS;
    }


    private String output(String status, String msg) {
        result.put("status", status);
        if (msg != null) {
            result.put("msg", msg);
        }
        return SUCCESS;
    }



    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getBrokerId() {
        return brokerId;
    }
    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
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
