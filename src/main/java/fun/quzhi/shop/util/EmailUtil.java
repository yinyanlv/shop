package fun.quzhi.shop.util;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public static String genVerifyCode() {
        List<String> list = Arrays.asList(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"});
        Collections.shuffle(list);
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += list.get(i);
        }
        return result;
    }

}
