package fun.quzhi.shop.model.vo;

import java.util.Date;

/**
 * 订单统计VO
 */
public class OrderStatisticsVO {

    private Date day;

    private Integer amount;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
