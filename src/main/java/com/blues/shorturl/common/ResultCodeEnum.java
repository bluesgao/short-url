package com.blues.shorturl.common;

public enum ResultCodeEnum {
    //这里是可以自己定义的，方便与前端交互即可
    ERROR(-1, "错误"),
    SUCCESS(0, "成功"),
    GET_ORIGIN_URL_ERROR(100001, "获取原始url失败"),
    ORIGIN_URL_NOT_EXIST(100002, "原始url不存在"),
    KEYWORD_NOT_EXIST(200001, "短码不存在"),
    GEN_SHORT_URL_ERROR(200002, "生成短码失败"),
    ID_LIMIT_ERROR(300001, "ID超过限制[916132832,56800235583]"),
    TOKEN_ERR(400001, "token错误,无访问权限"),
    ;

    private Integer code;
    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
