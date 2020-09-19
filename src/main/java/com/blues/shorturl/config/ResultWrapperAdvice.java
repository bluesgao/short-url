package com.blues.shorturl.config;

import com.blues.shorturl.common.CommonResult;
import com.blues.shorturl.common.ResultCodeEnum;
import com.blues.shorturl.common.CommonBizException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@Slf4j
@RestControllerAdvice
public class ResultWrapperAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    //自动处理ShortUrlBizException，包装为CommonResult
    @ExceptionHandler(CommonBizException.class)
    public CommonResult handleException(CommonBizException ex) {
        log.error(ex.getMessage(), ex);
        return CommonResult.builder().code(ex.getErrCode()).msg(ex.getErrMsg()).build();
    }

    //仅当方法或类不是CommonResult才自动包装
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getParameterType() != CommonResult.class;
    }

    //自动包装外层CommonResult响应
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        CommonResult result = CommonResult.builder().code(ResultCodeEnum.SUCCESS.getCode()).msg(ResultCodeEnum.SUCCESS.getMsg()).data(body).build();
        if (body instanceof String) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return objectMapper.writeValueAsString(result);
        } else {
            return result;
        }
    }
}
