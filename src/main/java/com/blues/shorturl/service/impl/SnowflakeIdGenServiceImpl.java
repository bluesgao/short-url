package com.blues.shorturl.service.impl;

import com.blues.shorturl.service.IdGenService;
import com.blues.shorturl.util.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class SnowflakeIdGenServiceImpl implements IdGenService {
    private SnowflakeIdWorker snowflakeIdWorker;

    @Value("${snowflake_worker_id}")
    private Long workerId;
    @Value("${snowflake_datacenter_Id}")
    private Long datacenterId;

    @PostConstruct
    private void init() {
        snowflakeIdWorker = new SnowflakeIdWorker(workerId, datacenterId);
    }

    @Override
    public Long getId(String bizTag) {
        Long id = snowflakeIdWorker.nextId();
        log.info("SnowflakeIdGenService getId:{}", id);
        return id;
    }
}
