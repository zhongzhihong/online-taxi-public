package com.mashibing.internalcommon.constant;

public enum CommonStatusEnum {
    SUCCESS(1, "success"),
    FAIL(2, "fail"),
    VERIFICATION_CODE_ERROR(1099, "验证码不正确"),
    TOKEN_ERROR(1199, "token错误");

    private int code;
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
