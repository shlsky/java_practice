package com.imdada.aopm.common;

/**
 * @author Song Hongling
 * @date 2019/10/31
 */
public enum ResultStatusEnum {
    OK(0),
    FAIL(1),
    EXCEPTION(2);
    private int status;

    private ResultStatusEnum(int status) {
        this.status = status;
    }
}
