package fun.quzhi.shop.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
}
