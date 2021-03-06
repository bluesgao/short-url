package com.blues.shorturl.service.impl;

import com.blues.shorturl.dao.AccessControlMapper;
import com.blues.shorturl.entity.AccessControl;
import com.blues.shorturl.service.AccessControlService;
import com.blues.shorturl.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private AccessControlMapper accessControlMapper;

    @PostConstruct
    private void loadingCache() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<AccessControl> accessControls = getData();
                for (AccessControl item : accessControls) {
                    aclCache.put(item.getBizType(), item.getToken());
                }
                log.info("loading-acl-cache size:{}", aclCache.size());
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    private List<AccessControl> getData() {
        List<AccessControl> accessControls = accessControlMapper.queryAll(new AccessControl());
        return accessControls;
    }


    @Override
    public boolean canVisit(String bizType, String token) {
        if (StringUtils.isEmpty(bizType) || StringUtils.isEmpty(token)) {
            return false;
        }
        String tempToken = aclCache.get(bizType);
        return (tempToken != null && tempToken.equals(token));
    }
}