package com.blues.shorturl.service;

public interface UrlService {
    String genShortUrl(String bizType, String originUrl);

    String getOriginUrl(String keyword);
}
