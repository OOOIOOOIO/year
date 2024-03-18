package com.sh.year.global.util.redis;

public enum RedisConst {

    ACCESS_TOKEN("user:token:access:"),
    REFRESH_TOKEN("user:token:refresh:");

    private final String prefix;

    RedisConst(String prefix) {
        this.prefix = prefix;
    }

    public String prefix() {
        return prefix;
    }
}
