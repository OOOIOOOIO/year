package com.sh.year.global.common;

public enum ResponseConst {

    SUCCESS("success"),
    FAIL("fail");

    private final String value;

    ResponseConst(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
