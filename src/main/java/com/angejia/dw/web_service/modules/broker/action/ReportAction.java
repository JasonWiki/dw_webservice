package com.angejia.dw.web_service.modules.broker.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/broker")
@ResultPath("/")
public class ReportAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private static final int pageSize = Integer.MAX_VALUE;

    private Integer id;
    private Integer status;
    private Integer ownerId;
    private String keyword;
    private Integer page;

    @Action(value="", results={@Result(location="list", type="redirect")})
    public String index() {
        return SUCCESS;
    }

    @Action(value="list", results={@Result(location="list.jsp")})
    public String list() {

        return SUCCESS;
    }

    @Action(value="add", results={@Result(location="add.jsp")})
    public String add() {

        return SUCCESS;
    }

    @Action(value = "edit", results = {
        @Result(location = "add.jsp"),
        @Result(name = "error", location = "list", type = "redirect")
    })
    public String edit() {

        return SUCCESS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
