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
}
