//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mh.FlatForm.enums;

public enum ResultEnum {
    FULL(1, "完整"),
    DEFECT(0, "缺版本号"),
    NULL(-1, "不存在");

    private int code;
    private String field;

    private ResultEnum(int code, String field) {
        this.code = code;
        this.field = field;
    }

    public int getCode() {
        return this.code;
    }

    public String getField() {
        return this.field;
    }
}
