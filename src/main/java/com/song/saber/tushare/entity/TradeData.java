package waditu.tushare.entity;

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * Created by Raymond on 24/12/2016.
 */
public final class TradeData {
    public Date date;
    public Double open;
    public Double high;
    public Double close;
    public Double low;
    public Double volume;
    public Double price_change;
    public Double p_change;
    public Double ma5;
    public Double ma10;
    public Double ma20;
    public Double v_ma5;
    public Double v_ma10;
    public Double v_ma20;
    public Double turnover;

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
