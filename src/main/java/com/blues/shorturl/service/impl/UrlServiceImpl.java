package com.blues.shorturl.service.impl;

import com.alibaba.fastjson.JSON;
import com.blues.shorturl.common.CommonBizException;
import com.blues.shorturl.common.ResultCodeEnum;
import com.blues.shorturl.entity.UrlMapping;
import com.blues.shorturl.service.IdGenService;
import com.blues.shorturl.service.UrlMappingService;
import com.blues.shorturl.service.UrlService;
import com.blues.shorturl.util.BASE62;
import com.blues.shorturl.util.MD5;
import com.blues.shorturl.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UrlServiceImpl implements UrlService {
    @Resource
    UrlMappingService urlMappingService;
    private ConcurrentHashMap<String, UrlMapping> urlCache = new ConcurrentHashMap<>(1000);
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("loading-url"));
    //@Resource(name = "snowflakeIdGenServiceImpl")
    @Resource(name = "dbIdGenServiceImpl")
    private IdGenService idGenService;
    @Value("${short_url_prefix}")
    private String shortUrlPrefix;

    private static long floor = 916132832L;
    private static long ceiling = 56800235583L;

    @PostConstruct
    private void loadingCache() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<UrlMapping> urlMappings = getData();
                for (UrlMapping item : urlMappings) {
                    urlCache.put(item.getKeyword(), item);
                }
                log.info("loading-url-cache size:{}", urlCache.size());

            }
        }, 0, 60, TimeUnit.SECONDS);
    }

    private List<UrlMapping> getData() {
        List<String> types = new ArrayList<>();
        types.add("kol");
        types.add("activity");
        types.add("sales");
        return urlMappingService.queryByBizType(types);
    }

    @Override
    public String genShortUrl(String bizType, String originUrl) {
        if (StringUtils.isEmpty(originUrl)) {
            return null;
        }
        //1,判断长链接是否存在
        UrlMapping oldUrlMapping = urlMappingService.queryByMd5(MD5.tomd5(originUrl));
        if (oldUrlMapping != null && StringUtils.isEmpty(oldUrlMapping.getKeyword())) {
            return shortUrlPrefix + oldUrlMapping.getKeyword();
        }
        //2，生成唯一id
        Long id = idGenService.getId("short_url");

        //短链长度6位	约 568亿	 916132832 - 56800235583
        if (id < floor || id > ceiling) {
            log.error("ID超过限制{}",id);
            throw new CommonBizException(ResultCodeEnum.ID_LIMIT_ERROR.getCode(), ResultCodeEnum.ID_LIMIT_ERROR.getMsg());
        }
        //3,将唯一id转换为62进制
        String keyword = BASE62.encode(id);

        //4，生成长链接md5
        String md5 = MD5.tomd5(originUrl);

        //5,入库
        UrlMapping createObj = new UrlMapping();
        createObj.setOriginUrl(originUrl);
        createObj.setKeyword(keyword);
        createObj.setOriginUrlMd5(md5);
        createObj.setBizType(StringUtils.isEmpty(bizType) ? "normal" : bizType);
        createObj.setDescription("");
        createObj.setCreator("");
        int row = urlMappingService.createUrlMapping(createObj);

        //6，返回keyword
        if (row == 1) {
            return shortUrlPrefix + createObj.getKeyword();
        }
        return null;
    }

    @Override
    public String getOriginUrl(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }
        UrlMapping urlMapping = null;
        if (urlCache.containsKey(keyword.trim())) {
            urlMapping = urlCache.get(keyword.trim());
            if (urlMapping != null && !StringUtils.isEmpty(urlMapping.getOriginUrl())) {
                log.info("urlCache keyword:{},urlMapping:{}", keyword, JSON.toJSONString(urlMapping));
                return urlMapping.getOriginUrl();
            }
        }

        urlMapping = urlMappingService.queryByKeyword(keyword);
        if (urlMapping != null && !StringUtils.isEmpty(urlMapping.getOriginUrl())) {
            if (!urlMapping.getBizType().equals("normal")) {
                urlCache.put(urlMapping.getKeyword(), urlMapping);
            }
            return urlMapping.getOriginUrl();
        }
        return null;
    }
}
