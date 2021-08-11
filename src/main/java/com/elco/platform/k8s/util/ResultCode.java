package com.elco.platform.k8s.util;

/**
 * @author kay
 * @date 2020/11/23
 */
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    //400开头的为请求错误
    UNAUTHENTICATION(401, "暂未登录或token已经过期"),
    UNAUTHORIZATION(403, "无操作权限"),
    BADARGUMENT(405, "参数错误"),

    //500开头的是系统处理错误
    FAILED(500, "系统内部错误"),
    // 需要友好提示用户，比如验证码错误，密码错误等提示
    FriendlyError(501, "业务验证错误，需要友好提示");

    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
