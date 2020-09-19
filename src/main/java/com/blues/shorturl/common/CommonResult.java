package com.blues.shorturl.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResult<T> {
    private int code;
    private String msg;
    private T data;
}
