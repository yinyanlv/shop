package fun.quzhi.shop.common;

import com.google.common.collect.Sets;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 常量
 */
@Component
public class Constant {
    public static final String SESSION_USER_KEY = "shop_user";

    public static final String EMAIL_FROM = "1761869346@qq.com";

    public static String FILE_UPLOAD_PATH;
    public static Integer IMAGE_SIZE = 500;
    public static Float IMAGE_OPACITY = 0.5f;
    public static String WATER_MARK_JPG = "water_mark.jpg";

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

    public static final String JWT_KEY = "quzhi-shop";
    public static final String JWT_HEADER_TOKEN_KEY = "x-token";
    public static final String USER_NAME = "username";
    public static final String USER_ID = "userId";
    public static final String USER_ROLE = "role";
    public static final Long JWT_EXPIRE_TIME =  60 * 1000 * 60 * 24 * 30L; //  30天，单位毫秒
}
