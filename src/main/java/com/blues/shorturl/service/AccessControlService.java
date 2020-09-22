package com.blues.shorturl.service;

import com.blues.shorturl.entity.AccessControl;

import java.util.List;

/**
 * 访问控制表(AccessControl)表服务接口
 *
 * @author makejava
 * @since 2020-09-22 14:52:34
 */
public interface AccessControlService {
    /**
     * 是否有权限
     * @param bizType
     * @param token
     * @return
     */
    boolean canVisit(String bizType, String token);
}