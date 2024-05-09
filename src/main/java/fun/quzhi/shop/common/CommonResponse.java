package fun.quzhi.shop.common;

import fun.quzhi.shop.exception.ShopExceptionEnum;

/**
 * 通用接口返回对象
 */
public class CommonResponse<T> {

    private static final int SUCCESS_CODE = 10000;

    private static final String SUCCESS_MESSAGE = "操作成功";

    private Boolean success;

    private Integer status;

    private String message;

    private T result;

    public CommonResponse() {
        this(true, SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public CommonResponse(Boolean success, Integer status, String message, T result) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public CommonResponse(Boolean success, Integer status, String message) {
        this.success = success;
        this.status = status;
        this.message = message;
    }

    public CommonResponse(Boolean success, Integer status) {
        this.success = success;
        this.status = status;
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>();
    }

    public static <T> CommonResponse<T> success(String message) {
        return new CommonResponse<>(true, SUCCESS_CODE, message);
    }

    public static <T> CommonResponse<T> success(T result) {
        return new CommonResponse<>(true, SUCCESS_CODE, SUCCESS_MESSAGE, result);
    }

    public static <T> CommonResponse<T> success(String message, T result) {
        return new CommonResponse<>(true, SUCCESS_CODE, message, result);
    }

    public static <T> CommonResponse<T> error(Integer code, String  message) {
        return new CommonResponse<>(false, code, message);
    }

    public static <T>  CommonResponse<T> error(ShopExceptionEnum ex) {
        return new CommonResponse<>(false, ex.getCode(), ex.getMessage());
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "success=" + success +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
