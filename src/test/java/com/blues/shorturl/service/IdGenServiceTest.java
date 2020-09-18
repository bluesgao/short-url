package com.blues.shorturl.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IdGenServiceTest {
    @Resource
    private IdGenService idGenService;

    private StopWatch stopWatch;


    @Before
    public void setUp() throws Exception {
        stopWatch = new StopWatch("test order");
    }

    @Test
    public void getId() throws Exception {
        stopWatch.start();
        int THREAD_NUM = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 5000; i++) {
                        Long id = idGenService.getId("order");
                        log.info("id:" + id);
//                        try {
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            });
        }
        //关闭线程池
        Thread.sleep(1000 * 30);
        //executorService.shutdown();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }
}