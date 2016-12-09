package com.angejia.dw.web_service.modules.entity.dw.dm_db;

import java.io.Serializable;

public class BrokerUserMateInventoryRsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer brokerId;
    private Integer userId;
    private Integer cityId;
    private Integer rsCnt; // 推荐条数
    private String rsRrom; // 推荐来源

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getRsCnt() {
        return rsCnt;
    }

    public void setRsCnt(Integer rsCnt) {
        this.rsCnt = rsCnt;
    }

    public String getRsRrom() {
        return rsRrom;
    }

    public void setRsRrom(String rsRrom) {
        this.rsRrom = rsRrom;
    }
}
