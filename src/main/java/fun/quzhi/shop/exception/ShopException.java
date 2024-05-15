package fun.quzhi.shop.exception;

/**
 * 统一异常
 */
public class ShopException extends RuntimeException {
    private final Integer code;
    private final String message;

    public ShopException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ShopException(ShopExceptionEnum exEnum) {
        this(exEnum.getCode(), exEnum.getMessage());
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
