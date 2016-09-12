package com.angejia.dw.web_service.modules.broker.model;

import java.math.BigDecimal;
import java.math.BigInteger;

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
public class DemandTb implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    // 需求单状态
    public static final byte DEMAND_STATUS = 1;

    private Integer id;
    private Long customerId;
    private Long brokerId;
    // status = 1 , 表示有效的
    private byte status;
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
    private BigDecimal budget;
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

    @Column(name = "buyer_uid")
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "broker_uid")
    public Long getBrokerId() {
        return brokerId;
    }
    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    @Column(length = 1)
    public byte getStatus() {
        return status;
    }
    public void setStatus(byte status) {
        this.status = status;
    }

    @Column(name = "city_id", length = 9)
    public Integer getCityId() {
        return cityId;
    }
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Column(name = "district_id")
    public String getDistrictIds() {
        return districtIds;
    }
    public void setDistrictIds(String districtIds) {
        this.districtIds = districtIds;
    }

    @Column(name = "block_id")
    public String getBlockIds() {
        return blockIds;
    }
    public void setBlockIds(String blockIds) {
        this.blockIds = blockIds;
    }

    @Column(name = "community_ids", length = 9)
    public String getCommunityIds() {
        return communityIds;
    }
    public void setCommunityIds(String communityIds) {
        this.communityIds = communityIds;
    }

    @Column(name = "price_id", length = 11)
    public Integer getPriceId() {
        return priceId;
    }
    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public BigDecimal getBudget() {
        return budget;
    }
    public void setBudget(BigDecimal budget) {
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
