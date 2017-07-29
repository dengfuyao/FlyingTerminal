package com.flyingogo.flyingterminal.utils;

/**
 * 作者：dfy on 11/7/2017 17:54
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class URLUtils {
    // http://14.154.31.54:8081/bus/bikeOpercteR/excuseException.do?cardNo=0000000000

    //http://192.168.31.76:8081/fjbike/bike/queryBikeStationByCoord.do?longitude=&latitude=&distance=100&lonDistance=1000
    //http://
 static String Url = "http://123.207.25.39:8280/fjbike/";  //外网主地址;
    //static String Url = "http://192.168.31.16:8080/fjbike/";
    /**
     * 异常卡处理
     *
     * @param card_no
     * @return
     */

    public static String getExceptionCardUrl(String card_no) {
        String url = Url + "bikeOpercteR/excuseException.do?"
                + "cardNo="
                + card_no;

        return url;
    }

    public static String getExceptionMobileUrl(String mobile) {
        String url = Url + "bikeOpercteR/excuseException.do?"
                + "mobile="
                + mobile;

        return url;
    }
    // TODO: 11/7/2017  创建URL接口:

    /**
     * 余额查询的URL
     *
     * @param card_no 卡号
     * @return
     */
    public static String getBalanceCardUrl(String card_no) {
        String url = Url + "bikeCardInfo/selectCardInfo.do?"
                + "cardNo="
                + card_no;
        return url;

    }
//http://localhost:8081/bus/userBalance.do?mobile=18682013146

    /**
     * 通过手机号码查询余额;
     *
     * @param moblile
     * @return
     */
    public static String getBalanceMobileUrl(String moblile) {
        String url = Url + "userBalance.do?"
                + "mobile="
                + moblile;
        return url;

    }

    //url:http://192.168.31.197:8081/bus/bikeOpercteR/selectOpercteRecord.do?cardNo=5970300233&pageNum=4&pageSize=5

    /**
     * @param pageNum  页数
     * @param pageSize 每页显示条数;
     * @param card_no  卡号;
     * @return 查询骑行记录的URL;
     */

    public static String getRecordCardUrl(String pageNum, String pageSize, String card_no) {
        String url = Url + "bikeOpercteR/selectOpercteRecord.do?"
                + "cardNo=" + card_no
                + "&pageNum=" + pageNum
                + "&pageSize=" + pageSize;
        return url;

    }
    //http://localhost:8081/bus/bikeOprec/selectOprec.do?mobile=18682013146

    public static String getRecordMobileUrl(String pageNum, String pageSize, String mobile) {
        String url = Url + "bikeOprec/selectOprec.do?"
                + "mobile=" + mobile
                + "&pageNum=" + pageNum
                + "&pageSize=" + pageSize;
        return url;

    }

    //http://14.154.31.54:8081/bus/bike/queryBikeStationByCoord.do?
    // longitude=116.426905&latitude=25.053924&distance=1&lonDistance=1

    public static String getStationUrl(String longitude, String latitude, String distance, String lonDistance) {
        String url = Url + "bike/queryBikeStationByCoord.do?"
                + "longitude=" + longitude
                + "&latitude=" + latitude
                + "&distance=" + distance
                + "&lonDistance=" + lonDistance;
        return url;

    }
    //充值请求  post
    //http://192.168.31.16:8080/fjbike/weChatPayment.do?cardNo=&rechargeAmount=&paymentMode=
    //http://192.168.31.16:8080/fjbike/weChatPayment.do?cardNo=&rechargeType=&rechargeAmount=&paymentMode=
//http://123.207.252.36:8280/fjbike/weChatPayment.do?cardNo=5970100007&rechargeType=1&rechargeAmount=50
// http://123.207.252.36:8280/fjbike/weChatPayment.do?cardNo=5970100007&rechargeType=1&rechargeAmount=50
    /**
     * @param card_no        充值卡号
     * @param rechangeAmpunt 充值金额
     * @param rechargeType   充值类型  1.APP充值, 2.pc 充值  3.管理系统
     * @return
     */
    public static String getWeiChatRechargeURL(String card_no, double rechangeAmpunt, int rechargeType) {
        String url = Url + "weChatPayment.do?cardNo="
                + card_no
                + "&rechargeType=" + rechargeType
                + "&rechargeAmount=" + rechangeAmpunt;
        return url;

    }
   // http://123.207.25.39:8280/fjbike/alipayPayment.do?cardNo=5970100007&rechargeType=1&rechargeAmount=0.01

    public static String getAliPayRechargeURL(String card_no, double rechangeAmpunt, int rechargeType){
        String url = Url+"alipayPayment.do?cardNo="
                + card_no
                +"&rechargeType="+rechargeType
                +"&rechargeAmount="+rechangeAmpunt;
        return url;
    }

    //http://123.207.25.39:8280/fjbike/weChatPayment/payIsSuccess.do?indentId=2017072705260541297

    public static String getWeCharResultUrl(String indentId){
        return Url+"weChatPayment/payIsSuccess.do?indentId="+indentId;
    }


}
