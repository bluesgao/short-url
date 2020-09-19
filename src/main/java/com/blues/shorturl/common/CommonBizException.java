package com.blues.shorturl.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortUrlBizException extends RuntimeException {
    private int errCode;
    private String errMsg;


    public ShortUrlBizException(int errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    public ShortUrlBizException(Throwable cause, int errCode, String errMsg) {
        super(cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}