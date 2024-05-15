package fun.quzhi.shop.exception;

import fun.quzhi.shop.common.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("System Exception:", e);
        return CommonResponse.error(ShopExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(ShopException.class)
    @ResponseBody
    public Object handleShopBizException(ShopException e) {
        log.error("Shop Business Exception:", e);
        return CommonResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Shop Method Argument Not Valid Exception:", e);
        return handleBindingResult(e.getBindingResult());
    }

    private CommonResponse handleBindingResult(BindingResult result) {
        List<String> list = null;

        if (result.hasErrors()) {
            list = new ArrayList<>();
            List<ObjectError> errors = result.getAllErrors();

            for (int i = 0; i < errors.size(); i++) {

                ObjectError objErr = errors.get(i);
                String msg = objErr.getDefaultMessage();

                list.add(msg);
            }
        }
        if (list == null || list.isEmpty()) {
            return CommonResponse.error(ShopExceptionEnum.REQUEST_PARAM_ERROR);
        }

        return CommonResponse.error(ShopExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());
    }
}
