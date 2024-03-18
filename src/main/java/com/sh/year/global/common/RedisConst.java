package com.sh.year.global.common;

public enum RedisConst {

    MAIN_BY_PRICE("main:price"),
    MAIN_BY_LIKE("main:like"),
    MENU("menu:"),
    LIKE("menu:like:"),
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
