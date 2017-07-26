package com.flyingogo.flyingterminal.contants;

/**
 * 作者：dfy on 7/7/2017 17:34
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public interface Contants {
    //http://192.168.31.197:8081/bus/userRegister.do?mobile=18682013146&smsMsg=6666
    //http://14.154.31.54
    String URL = "http://14.154.31.54:8081/bus/";
    String LOGIN = "userRegister.do?";
    String MOBILE = "18682013146";
    String SMSMSG = "6666";
    String URL_LOGIN = URL+LOGIN+"mobile="+MOBILE+"&smsMsg="+SMSMSG;   //登录

    //bus/bikeOpercteR/selectOpercteRecord.do?cardNo=5970300233";
   // http://192.168.31.197:8081/bus/bikeOpercteR/selectOpercteRecord.do?cardNo=5970300233
    String URL_RECORD = URL+"bikeOpercteR/selectOpercteRecord.do?cardNo=";
  //  String CARD_NO = "5970300233";
   // String BALANCE = URL+RECORD;   //卡号查询  卡号由用户自己输入;
        //http://192.168.31.197:8081/bus/bikeCardInfo/selectCardInfo.do?cardNo=5970100473
    String URL_BALANCE =URL+"bikeCardInfo/selectCardInfo.do?cardNo=";

    String CARD_NO = "card_no";

    String CORD_RECHARGE_BEAN = "cord_recharge_bean";
}

