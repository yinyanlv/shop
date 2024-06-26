package fun.quzhi.shop.common;

import com.google.common.collect.Sets;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 常量
 */
@Component
public class Constant {
    public static final String SESSION_USER_KEY = "shop_user";

    public static String FILE_UPLOAD_PATH;

    @Value("${app.file-upload-path}")
    public void setFileUploadPath(String fileUploadPath) {
        FILE_UPLOAD_PATH = fileUploadPath;
    }

    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price asc", "price desc");
    }

    public interface SaleStatus {
        int NOT_SALE = 0; // 商品下架
        int SALE = 1; // 商家上架
    }

    public interface Cart {
        int UN_CHECKED = 0; // 未选中
        int CHECKED = 1; // 选中
    }

    public enum OrderStatusEnum {
        CANCELLED(0,"用户已取消"),
        UNPAID(10,"未付款"),
        PAID(20, "已付款"),
        DELIVERED(30, "已发货"),
        FINISHED(40, "交易完成");

        private int code;
        private String name;

        OrderStatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static OrderStatusEnum getByCode(int code) {
            for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new ShopException(ShopExceptionEnum.NO_ENUM);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
