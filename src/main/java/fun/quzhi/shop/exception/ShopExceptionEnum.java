package fun.quzhi.shop.exception;

/**
 * 异常枚举
 */
public enum ShopExceptionEnum {

    COMMON_ERROR(10001, "操作失败"),
    NEED_USERNAME(10002, "用户名不能为空"),
    NEED_PASSWORD(10003, "密码不能为空"),
    USERNAME_EXISTS(10004, "用户名已存在"),
    REGISTER_ERROR(10004, "创建用户失败，请重试"),
    LOGIN_ERROR(10005, "登录失败"),
    NEED_LOGIN(10006, "用户未登录"),
    UPDATE_USER_ERROR(10007, "更新用户信息失败"),
    NEED_ADMIN(10007, "无管理员权限"),
    SYSTEM_ERROR(20000, "系统异常");


    Integer code;

    String message;

    ShopExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
