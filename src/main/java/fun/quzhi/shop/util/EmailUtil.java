package fun.quzhi.shop.util;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

/**
 * Email工具
 */
public class EmailUtil {
    public static boolean isValidEmail(String email){
        boolean isValid = true;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            e.printStackTrace();
            isValid = false;
        }
        return isValid;
    }
}
