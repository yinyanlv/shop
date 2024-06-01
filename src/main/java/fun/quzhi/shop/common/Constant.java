package fun.quzhi.shop.common;

import com.google.common.collect.Sets;
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
}
