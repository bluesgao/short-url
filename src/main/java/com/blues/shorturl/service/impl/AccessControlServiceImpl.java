package com.blues.shorturl.service.impl;

import com.alibaba.fastjson.JSON;
import com.blues.shorturl.dao.AccessControlDao;
import com.blues.shorturl.entity.AccessControl;
import com.blues.shorturl.service.AccessControlService;
import com.blues.shorturl.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 访问控制表(AccessControl)表服务实现类
 *
 * @author makejava
 * @since 2020-09-22 14:52:34
 */
@Slf4j
@Service
public class AccessControlServiceImpl implements AccessControlService {
    private ConcurrentHashMap<String, String> aclCache = new ConcurrentHashMap<>(1000);
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("loading-acl"));

    @Resource
    private AccessControlDao accessControlDao;

    @PostConstruct
    private void loadingAclCache() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<AccessControl> accessControls = getData();
                for (AccessControl item : accessControls) {
                    aclCache.put(item.getBizType(), item.getToken());
                }
                log.info("loading-acl-cache size:{} details:{}", aclCache.size(), JSON.toJSONString(aclCache));

            }
        }, 0, 60, TimeUnit.SECONDS);
    }

    private List<AccessControl> getData() {
        return accessControlDao.queryAll(null);
    }


    @Override
    public boolean canVisit(String bizType, String token) {
        return false;
    }
}