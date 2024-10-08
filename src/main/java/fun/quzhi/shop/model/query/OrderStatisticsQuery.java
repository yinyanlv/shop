package fun.quzhi.shop.model.query;

import java.util.Date;

/**
 * 订单量统计查询
 */
public class OrderStatisticsQuery {

    private Date startDate;

    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
