package com.mashibing.internalcommon.util;

public class RedisPrefixUtils {

    public static String VERIFICATIONCODEPREFIX = "VerificationCode-";

    public static String TOKENPREFIX = "token-";

    // 根据手机号，生成key
    public static String GeneratorKeyByPhone(String Phone, String identity) {
        return VERIFICATIONCODEPREFIX + identity + "-" + Phone;
    }

    // 根据token，生成key
    public static String GeneratorKeyByToken(String phone, String identity, String tokenType) {
        return TOKENPREFIX + phone + "-" + identity + "-" + tokenType;
    }

}
