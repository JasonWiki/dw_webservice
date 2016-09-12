package com.angejia.dw.web_service.modules.broker.model;

import javax.persistence.Column;
// 注解将一个类声明为一个实体bean(即一个持久化POJO类)
import javax.persistence.Entity;
// 注解定义主键标识符的生成策略
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "demand")
public class demand implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer customerId;
    private Integer brokerId;
    // status = 1 , 表示有效的
    private Integer status;
    private Integer cityId;
    // 区域列表
    private String districtIds;
    // 版块列表
    private String blockIds;
    // 小区列表
    private String communityIds;
    // 价格区间 id
    private Integer priceId;
    // 预算 万为单位
    private Integer budget;
    // 户型列表
    private String bedrooms;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "buyer_uid", length = 20)
    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Column(name = "broker_uid", length = 20)
    public Integer getBrokerId() {
        return brokerId;
    }
    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    @Column(length = 4)
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(length = 9)
    public Integer getCityId() {
        return cityId;
    }
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Column(name = "district_id", length = 255)
    public String getDistrictIds() {
        return districtIds;
    }
    public void setDistrictIds(String districtIds) {
        this.districtIds = districtIds;
    }

    @Column(name = "block_id", length = 255)
    public String getBlockIds() {
        return blockIds;
    }
    public void setBlockIds(String blockIds) {
        this.blockIds = blockIds;
    }

    @Column(length = 255)
    public String getCommunityIds() {
        return communityIds;
    }
    public void setCommunityIds(String communityIds) {
        this.communityIds = communityIds;
    }

    @Column(length = 11)
    public Integer getPriceId() {
        return priceId;
    }
    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getBudget() {
        return budget;
    }
    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    @Column(length = 255)
    public String getBedrooms() {
        return bedrooms;
    }
    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

}
