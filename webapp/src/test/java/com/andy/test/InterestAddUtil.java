package com.andy.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * <p>Title:</p>
 * <p>Descriprion:</p>
 * <p>Company乐视控股（北京）有限公司</p>
 *
 * @author zhoushiqi
 * @date 2016/11/24.
 */
public class InterestAddUtil {
    private final static int ONEDAY = 24 * 60 * 60 * 1000;
    /**
     *
     * 获取计息
     * @param startTime  当前时间
     * @param bearingBase  计息基数
     * @param dateOfValueTime  起息日
     * @param orderFeeAmount   订单实际支付金额
     * @param addInterest   加息率
     * @return
     */
    public static long getAddInterest(long startTime, Byte bearingBase, long dateOfValueTime, long orderFeeAmount, String addInterest) {
        long bearingBaseLong = 360L;
        if (bearingBase == 1) {
            bearingBaseLong = 360;
        } else if (bearingBase == 2) {
            bearingBaseLong = 365;
        }
        long interestDays = (startTime - dateOfValueTime) / ONEDAY;
        BigDecimal lastMonthInterest = getProCustomerRvnBigDecimal(orderFeeAmount, bearingBaseLong, interestDays, addInterest);
        //从加息日到这月的加息
        interestDays = interestDays + 1;
        BigDecimal currentMonthInterest = getProCustomerRvnBigDecimal(orderFeeAmount, bearingBaseLong, interestDays, addInterest);
        BigDecimal value = currentMonthInterest.subtract(lastMonthInterest);
        return value.longValue();
    }

    //获取计息
    public static BigDecimal getProCustomerRvnBigDecimal(long orderFeeAmount, Long bearingBase, long interestDayMount, String addInterest) {
        BigDecimal numerator = BigDecimal.valueOf(interestDayMount * orderFeeAmount).multiply(new BigDecimal(addInterest));
        BigDecimal denominator = BigDecimal.valueOf(100 * bearingBase);
        BigDecimal value = numerator.divide(denominator, 0, BigDecimal.ROUND_DOWN);
        return value;
    }

    public static void main(String[] args) throws ParseException {
        InterestAddUtil interestAddUtil = new InterestAddUtil();
        long startTime= new SimpleDateFormat("yyyy-MM-dd").parse("2016-11-02").getTime();
        Byte bearingBase= 1;
        long dateOfValueTime=new SimpleDateFormat("yyyy-MM-dd").parse("2016-11-01").getTime();
        long orderFeeAmount=10000l;
        String addInterest="6";

        System.out.println(""+InterestAddUtil.getAddInterest(startTime,bearingBase,dateOfValueTime,orderFeeAmount,addInterest));
    }
}
