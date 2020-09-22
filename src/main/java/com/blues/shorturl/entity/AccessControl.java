package com.blues.shorturl.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 访问控制表(AccessControl)实体类
 *
 * @author makejava
 * @since 2020-09-22 14:52:34
 */
public class AccessControl implements Serializable {
    private static final long serialVersionUID = -80096832835109038L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 业务标识
    */
    private String bizType;
    /**
    * 访问标识
    */
    private String token;
    /**
    * 描述
    */
    private String description;
    /**
    * 创建时间
    */
    private Date createdTime;
    /**
    * 更新时间
    */
    private Date updatedTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}