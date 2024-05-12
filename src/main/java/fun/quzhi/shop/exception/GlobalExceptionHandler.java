package fun.quzhi.shop.exception;

import fun.quzhi.shop.common.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handelException(Exception e) {
        log.error("System Exception:", e);
        return CommonResponse.error(ShopExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(ShopException.class)
    @ResponseBody
    public Object handelShopBizException(ShopException e) {
        log.error("Shop Business Exception:", e);
        return CommonResponse.error(e.getCode(), e.getMessage());
    }
}
