package fun.quzhi.shop.util;

import fun.quzhi.shop.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5工具
 */
public class MD5Utils {

    public static String getMD5Str(String str) throws  NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        return Base64.encodeBase64String(md5.digest(str.getBytes()));
    }

    public static String getPasswordMD5Str(String str, String salt) throws  NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        return Base64.encodeBase64String(md5.digest((str + salt).getBytes()));
    }

    public static void main(String[] args) {
        try {
           var val = MD5Utils.getPasswordMD5Str("abc", "111");
           System.out.println(val);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
