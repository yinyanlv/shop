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
    PARAM_IS_NULL(10008, "参数不能为空"),
    NAME_EXISTS(10009, "名称已存在"),
    NEED_ADMIN(10010, "无管理员权限"),
    CRATE_FAILED(10011, "新增失败"),
    DELETE_FAILED(10012, "删除失败"),
    UPDATE_FAILED(10013, "更新失败"),
    REQUEST_PARAM_ERROR(10012, "参数错误"),
    UPLOAD_DIR_ERROR(10013, "上传文件夹创建失败"),
    UPLOAD_FILE_FAILED(10014, "上传文件失败"),
    PRODUCT_NOT_SALE(10015, "商品不可售"),
    PRODUCT_NOT_ENOUGH(10016, "商品库存不足"),
    CART_SELECTED_EMPTY(10017, "购物车已勾选的商品为空"),
    NO_ENUM(10018, "未找到对应的枚举类"),
    NO_ORDER(10019, "订单不存在"),
    NOT_YOUR_ORDER(10020, "订单不属于你"),
    WRONG_ORDER_STATUS(10021, "订单状态不符"),
    INVALID_EMAIL(10022, "邮箱地址不合法"),
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
