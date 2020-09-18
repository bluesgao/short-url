package com.blues.shorturl.service.impl;

import com.blues.shorturl.dao.UrlMappingMapper;
import com.blues.shorturl.entity.UrlMapping;
import com.blues.shorturl.service.UrlMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UrlMappingServiceImpl implements UrlMappingService {
    @Resource
    private UrlMappingMapper urlMappingMapper;

    @Override
    public int createUrlMapping(UrlMapping urlMapping) {
        return urlMappingMapper.insertSelective(urlMapping);
    }

    @Override
    public List<UrlMapping> queryByBizType(List<String> types) {
        if (CollectionUtils.isEmpty(types)) {
            return null;
        }
        return urlMappingMapper.selectByBizType(types);
    }

    @Override
    public UrlMapping queryByMd5(String md5) {
        if (StringUtils.isEmpty(md5)) {
            return null;
        }
        return urlMappingMapper.selectByMd5(md5);
    }

    @Override
    public List<UrlMapping> queryByCondition(UrlMapping urlMapping) {
        return urlMappingMapper.selectByCondition(urlMapping);
    }

    @Override
    public UrlMapping queryByKeyword(String keyword) {
        return urlMappingMapper.selectByKeyword(keyword);
    }
}
