package com.blues.shorturl.service;

import com.blues.shorturl.entity.UrlMapping;

import java.util.List;

public interface UrlMappingService {
    int createUrlMapping(UrlMapping urlMapping);

    List<UrlMapping> queryUrlMapping(UrlMapping urlMapping);

    UrlMapping queryByKeyword(String keyword);

    List<UrlMapping> queryByBizType(List<String> types);

    UrlMapping queryByMd5(String md5);

}
