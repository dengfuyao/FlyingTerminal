package com.flyingogo.flyingterminal.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：dfy on 25/7/2017 17:56
 * <p> 微信充值请求二维码;
 * 邮箱：dengfuyao@163.com
 */

public class RechargeCardBena implements Serializable{

    /**
     * resultType : {"state":1001,"message":"预订单生成"}
     * orderid : 20170725055611
     * cardNo : 5970100007
     * rechargeAmount : 50
     * imgurl : D:/wechat/balance top up/1500976571782.png
     * codeUrl : weixin://wxpay/bizpayurl?pr=ETC5CjH
     */

    public List<DataBean> data;

    public  class DataBean implements Serializable {
        /**
         * state : 1001
         * message : 预订单生成
         */
        public ResultTypeBean resultType;
        public String orderid;   //订单编号
        public String cardNo;
        public String rechargeAmount;
        public String imgurl;
        public String codeUrl;

        public class ResultTypeBean  implements Serializable {
            public String message;
            public int    state;
        }
    }
}
