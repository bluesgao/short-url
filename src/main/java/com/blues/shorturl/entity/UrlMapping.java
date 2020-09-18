package com.blues.shorturl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * t_url_mapping
 *
 * @author
 */
@Data
public class UrlMapping implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 唯一标识
     */
    private String keyword;
    /**
     * 业务标识
     */
    private String bizType;
    /**
     * 原始url
     */
    private String originUrl;
    /**
     * 原始urlMd5
     */
    private String originUrlMd5;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
}