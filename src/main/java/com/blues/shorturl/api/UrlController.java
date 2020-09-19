package com.blues.shorturl.api;

import com.blues.shorturl.common.CommonBizException;
import com.blues.shorturl.common.ResultCodeEnum;
import com.blues.shorturl.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class UrlController {
    @Resource
    private UrlService urlService;

    @GetMapping("/genShortUrl.action")
    public String genShortUrl(@RequestParam String bizType, @RequestParam String originUrl) {
        String shortUrl = null;
        try {
            shortUrl = urlService.genShortUrl(bizType, originUrl);
        } catch (Exception e) {
            log.error("genShortUrl bizType:{}, originUrl:{},error:{}", bizType, originUrl, e);
        }
        if (StringUtils.isEmpty(shortUrl)) {
            throw new CommonBizException(ResultCodeEnum.GEN_SHORT_URL_ERROR.getCode(), ResultCodeEnum.GEN_SHORT_URL_ERROR.getMsg());
        }
        return shortUrl;
    }

    @GetMapping("/getOriginUrl.action")
    public String getOriginUrl(@RequestParam String keyword) {
        String originUrl = null;
        try {
            originUrl = urlService.getOriginUrl(keyword);
        } catch (Exception e) {
            throw new CommonBizException(e, ResultCodeEnum.GET_ORIGIN_URL_ERROR.getCode(), ResultCodeEnum.GET_ORIGIN_URL_ERROR.getMsg());
        }
        if (StringUtils.isEmpty(originUrl)) {
            throw new CommonBizException(ResultCodeEnum.KEYWORD_NOT_EXIST.getCode(), ResultCodeEnum.KEYWORD_NOT_EXIST.getMsg());
        }
        return originUrl;
    }
}
