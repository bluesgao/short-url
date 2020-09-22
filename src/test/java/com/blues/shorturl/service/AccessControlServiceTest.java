package com.blues.shorturl.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessControlServiceTest {

    @Resource
    private AccessControlService aclService;

    private StopWatch stopWatch;


    @Before
    public void setUp() throws Exception {
        stopWatch = new StopWatch("test acl");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void canVisit() {
        boolean b = aclService.canVisit("order", "11");
        log.info("canVisit:" + b);

    }
}