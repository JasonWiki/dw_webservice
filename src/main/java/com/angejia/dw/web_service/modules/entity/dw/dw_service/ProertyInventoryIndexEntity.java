package com.angejia.dw.web_service.modules.entity.dw.dw_service;

import java.util.List;

import javax.persistence.Column;
//注解将一个类声明为一个实体bean(即一个持久化POJO类)
import javax.persistence.Entity;
//注解定义主键标识符的生成策略
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "proerty_inventory_index")
public class ProertyInventoryIndexEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final Byte STATUS = 2;    // 有效
    public static final Byte SURVEY_STATUS = 2; // 已实勘
    
    private Long inventoryId;
    private Integer cityId;
    private Integer districtId;
    private Integer blockId;
    private Integer communityId;
    private Byte bedrooms;
    private Integer price;
    private Byte priceTier;
    private Character inventoryType;
    private Byte inventoryTypeId;
    private Byte isReal;
    private Byte status;
    private Byte surveyStatus;


    @Id
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
    public Byte getBedrooms() {
        return bedrooms;
    }
    public void setBedrooms(Byte bedrooms) {
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
    public Byte getPriceTier() {
        return priceTier;
    }
    public void setPriceTier(Byte priceTier) {
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
    public Byte getInventoryTypeId() {
        return inventoryTypeId;
    }
    public void setInventoryTypeId(Byte inventoryTypeId) {
        this.inventoryTypeId = inventoryTypeId;
    }


    @Column(name = "is_real")
    public Byte getIsReal() {
        return isReal;
    }
    public void setIsReal(Byte isReal) {
        this.isReal = isReal;
    }


    @Column()
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }


    @Column(name = "survey_status")
    public Byte getSurveyStatus() {
        return surveyStatus;
    }
    public void setSurveyStatus(Byte surveyStatus) {
        this.surveyStatus = surveyStatus;
    }
    
    
    
    // 以下参数都是以分号分割的 ids, 例如 1:2:3
    private Integer[] districtIds;
    private Integer[] blockIds;
    private Integer[] communityIds;
    private Byte[] bedroomsIds;
    private Byte[] priceTierIds;

    // 最大最小价格
    private Integer priceMin;
    private Integer priceMax;
    
    
    @Transient
    public Integer[] getDistrictIds() {
        return districtIds;
    }
    public void setDistrictIds(Integer[] districtIds) {
        this.districtIds = districtIds;
    }


    @Transient
    public Integer[] getBlockIds() {
        return blockIds;
    }
    public void setBlockIds(Integer[] blockIds) {
        this.blockIds = blockIds;
    }


    @Transient
    public Integer[] getCommunityIds() {
        return communityIds;
    }
    public void setCommunityIds(Integer[] communityIds) {
        this.communityIds = communityIds;
    }


    @Transient
    public Byte[] getBedroomsIds() {
        return bedroomsIds;
    }
    public void setBedroomsIds(Byte[] bedroomsIds) {
        this.bedroomsIds = bedroomsIds;
    }


    @Transient
    public Byte[] getPriceTierIds() {
        return priceTierIds;
    }
    public void setPriceTierIds(Byte[] priceTierIds) {
        this.priceTierIds = priceTierIds;
    }


    @Transient
    public Integer getPriceMin() {
        return priceMin;
    }
    public void setPriceMin(Integer priceMin) {
        this.priceMin = priceMin;
    }


    @Transient
    public Integer getPriceMax() {
        return priceMax;
    }
    public void setPriceMax(Integer priceMax) {
        this.priceMax = priceMax;
    }

}
