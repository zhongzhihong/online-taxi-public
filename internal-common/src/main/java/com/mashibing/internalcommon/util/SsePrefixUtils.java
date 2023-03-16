package com.mashibing.internalcommon.util;

public class SsePrefixUtils {

    public static final String keywords = "$";

    public static String generatorSseKey(Long userId, String identity) {
        return userId + keywords + identity;
    }
}
