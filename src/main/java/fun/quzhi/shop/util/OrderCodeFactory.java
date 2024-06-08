package fun.quzhi.shop.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成订单号
 */
public class OrderCodeFactory {
    private static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    private static int getRandom() {
        Random r = new Random();
        // 随机5位数
        return (int)(r.nextDouble() * 90000) + 10000;
    }

    public static String getOrderCode() {
        return getDateTime() + getRandom();
    }
}
