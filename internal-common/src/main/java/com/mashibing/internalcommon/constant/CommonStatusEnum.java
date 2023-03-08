package com.mashibing.internalcommon.constant;

public enum CommonStatusEnum {

    /**
     * 成功
     */
    SUCCESS(1, "success"),

    /**
     * 失败
     */
    FAIL(2, "fail"),

    /**
     * 验证码错误提示：1000-1099
     */
    VERIFICATION_CODE_ERROR(1099, "验证码不正确"),

    /**
     * Token类提示：1100-1199
     */
    TOKEN_ERROR(1199, "token错误"),

    /**
     * 用户提示：1200-1299
     */
    USER_NOT_EXIST(1200, "用户不存在"),

    /**
     * 计价规则:1300-1399
     */
    PRICE_RULE_EMPTY(1300, "计价规则不存在"),
    PRICE_RULE_EXISTS(1301, "计价规则已存在，不允许添加"),
    PRICE_RULE_NOT_EDIT(1302, "计价规则没有变化"),
    PRICE_RULE_CHANGED(1303, "计价规则有变化"),
    ;

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
