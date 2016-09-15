package com.angejia.dw.web_service.modules.entity.dw.dw_service;

import javax.persistence.Column;
//注解将一个类声明为一个实体bean(即一个持久化POJO类)
import javax.persistence.Entity;
//注解定义主键标识符的生成策略
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


//@Entity
//@Table(name = "proerty_inventory_index")
public class ProertyInventoryIndexEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long inventoryId;
    private Integer cityId;
    private Integer districtId;
    private Integer blockId;
    private Integer communityId;
    private byte bedrooms;
    private Integer price;
    private byte priceTier;
    private Character inventoryType;
    private byte inventoryTypeId;
    private byte isReal;
    private byte status;
    private byte surveyStatus;


    @Column(name = "inventory_id")
    public Long getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }


    @Column(name = "city_id", length = 10)
    public Integer getCityId() {
        return cityId;
    }
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }


    @Column(name = "district_id")
    public Integer getDistrictId() {
        return districtId;
    }
    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }


    @Column(name = "block_id")
    public Integer getBlockId() {
        return blockId;
    }
    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }


    @Column(name = "community_id")
    public Integer getCommunityId() {
        return communityId;
    }
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }


    @Column()
    public byte getBedrooms() {
        return bedrooms;
    }
    public void setBedrooms(byte bedrooms) {
        this.bedrooms = bedrooms;
    }


    @Column()
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }


    @Column(name = "price_tier")
    public byte getPriceTier() {
        return priceTier;
    }
    public void setPriceTier(byte priceTier) {
        this.priceTier = priceTier;
    }


    @Column(name = "inventory_type")
    public Character getInventoryType() {
        return inventoryType;
    }
    public void setInventoryType(Character inventoryType) {
        this.inventoryType = inventoryType;
    }


    @Column(name = "inventory_type_id")
    public byte getInventoryTypeId() {
        return inventoryTypeId;
    }
    public void setInventoryTypeId(byte inventoryTypeId) {
        this.inventoryTypeId = inventoryTypeId;
    }


    @Column(name = "is_real")
    public byte getIsReal() {
        return isReal;
    }
    public void setIsReal(byte isReal) {
        this.isReal = isReal;
    }


    @Column(length = 2)
    public byte getStatus() {
        return status;
    }
    public void setStatus(byte status) {
        this.status = status;
    }


    @Column(name = "survey_status")
    public byte getSurveyStatus() {
        return surveyStatus;
    }
    public void setSurveyStatus(byte surveyStatus) {
        this.surveyStatus = surveyStatus;
    }
}
