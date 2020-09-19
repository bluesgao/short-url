package com.blues.shorturl.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonBizException extends RuntimeException {
    private int errCode;
    private String errMsg;


    public CommonBizException(int errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    public CommonBizException(Throwable cause, int errCode, String errMsg) {
        super(cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}