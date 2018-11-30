package com.dudu.tools;

/**
 * @Author haien
 * @Description 错误响应类
 * @Date 2018/11/3
 **/
public enum ErrorCode {
    //实例
    ID_IS_NULL(400,"ID_IS_NULL","id为空！"), //用逗号分开
    ENT_CODE_IS_NULL(400,"ENT_CODE_IS_NULL","企业编码为空！"),
    ADMIN_ACCOUNT_IS_NULL(400,"ADMIN_ACCOUNT_IS_NULL","企业管理账号为空！"),
    UUIS_IS_NULL(400,"UUIS_IS_NULL","UUID为空！");

    //成员变量
    private int httpStatus; //状态码
    private String code;
    private String message;
    private int res_code; //响应码,其实也就是状态码

    //构造方法
    ErrorCode() {
    }
    ErrorCode(int httpStatus, String code, String message) {
        this.httpStatus = 200;
        this.code = code;
        this.message = message;
        this.res_code = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }
}























