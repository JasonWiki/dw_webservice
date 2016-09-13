package com.angejia.dw.web_service.modules.broker.model;

import java.math.BigDecimal;

import javax.persistence.Column;
// 注解将一个类声明为一个实体bean(即一个持久化POJO类)
import javax.persistence.Entity;
// 注解定义主键标识符的生成策略
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 客户 id 与 用户 id 关系表
 * @author jack
 */
@Entity
@Table(name = "broker_customer_bind_user")
public class BrokerCustomerBindUser implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    // 有效
    public static final byte IS_ACTIVE = 1;

    private Integer id;
    private Integer brokerCustomerId;
    private Integer userId;
    // is_active = 1 , 表示有效的
    private byte isActive;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "broker_customer_id")
    public Integer getBrokerCustomerId() {
        return brokerCustomerId;
    }
    public void setBrokerCustomerId(Integer brokerCustomerId) {
        this.brokerCustomerId = brokerCustomerId;
    }

    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "is_active")
    public byte getIsActive() {
        return isActive;
    }
    public void setIsActive(byte isActive) {
        this.isActive = isActive;
    }
}
