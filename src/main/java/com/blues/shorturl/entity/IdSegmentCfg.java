package com.blues.shorturl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class IdSegmentCfg implements Serializable {
    private Long id;
    private String bizTag;
    private Long startId;
    private Integer step;
    private String description;
    private Long version;
    private Date createdTime;
    private Date updatedTime;
}
