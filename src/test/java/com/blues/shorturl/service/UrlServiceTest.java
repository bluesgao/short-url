package com.blues.shorturl.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UrlServiceTest {
    @Resource
    private UrlService urlService;

    private StopWatch stopWatch;


    @Before
    public void setUp() throws Exception {
        stopWatch = new StopWatch("test url");
    }

    @Test
    public void genShortUrl() throws Exception {
        stopWatch.start();
        int THREAD_NUM = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 500; i++) {
                        String shorturl = null;
                        if (i%3==0){
                            shorturl = urlService.genShortUrl("kol","http://baidu.com/zc?name=123&&age=111&&activity=1&&tag=" + new Random().nextInt());
                        }else if(i%5==0){
                            shorturl = urlService.genShortUrl("activity","http://baidu.com/zc?name=123&&age=111&&activity=1&&tag=" + new Random().nextInt());
                        }else {
                            shorturl = urlService.genShortUrl("","http://baidu.com/zc?name=123&&age=111&&activity=1&&tag=" + new Random().nextInt());
                        }
                        log.info("short url:" + shorturl);
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

    @Test
    public void getOriginUrl() throws Exception{
        log.info("E4D->{}",urlService.getOriginUrl("fID"));

        Thread.sleep(1000 * 150);
    }
}