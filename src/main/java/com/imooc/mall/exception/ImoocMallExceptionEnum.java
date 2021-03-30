package com.imooc.mall.exception;

/**
 *      異常枚舉
 */
public enum ImoocMallExceptionEnum {

    NEED_USER_NAME(10001,"用戶名不能為空"),
    NEED_PASSWORD(10002,"密碼不能為空"),
    PASSWORD_TOO_SHORT(10003,"密碼長度不能小於8位"),
    NAME_EXISTED(10004,"不允許重名"),
    INSERT_FAILED(10005,"插入失敗，請重試"),
    WRONG_PASSWORD(10006,"密碼錯誤"),
    NEED_LOGIN(10007,"用戶未登入"),
    UPDATE_FAIL(10008,"更新失敗"),
    NEED_ADMIN(10009,"無管理員權限"),
    PARA_NOT_NULL(10010,"參數不得為空"),
    CREATE_FAILED(10011,"新增失敗"),
    REQUEST_PARAM_ERROR(10012,"參數錯誤"),
    DELETE_FAIL(10013,"刪除失敗"),
    SYSTEM_ERROR(20000,"系統異常");


    /**
     * 異常碼
     */
    Integer code;
    /**
     * 異常訊息
     */
    String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
