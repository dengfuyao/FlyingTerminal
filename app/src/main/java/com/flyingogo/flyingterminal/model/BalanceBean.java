package com.flyingogo.flyingterminal.model;

/**
 * 作者：dfy on 8/7/2017 15:28
 * <p> 余额bean类:
 * 邮箱：dengfuyao@163.com
 */

public class BalanceBean {
    /**
     * state : 1
     * message : 操作成功
     * data : {"id":2941,"cardNo":"5970100007","cardType":"0","cardDeposit":300,"cardBalance":0.84,"accountId":"13859033705","issuer":"管理员","cardState":"0","activeTime":1488902400000,"createDate":1488902400000,"projectId":1,"lastFlag":"0"}
     */

    public int      state;
    public String   message;
    /**
     * id : 2941
     * cardNo : 5970100007
     * cardType : 0
     * cardDeposit : 300
     * cardBalance : 0.84
     * accountId : 13859033705
     * issuer : 管理员
     * cardState : 0
     * activeTime : 1488902400000
     * createDate : 1488902400000
     * projectId : 1
     * lastFlag : 0
     */

    public DataBean data;

    public static class DataBean {
        public int    id;
        public String cardNo;
        public String cardType;
        public int    cardDeposit;
        public double cardBalance;
        public String accountId;
        public String issuer;
        public String cardState;
        public long   activeTime;
        public long   createDate;
        public int    projectId;
        public String lastFlag;

    }
}
    /**
     * state : 1
     * message : 操作成功
     * data : {"id":1,"account":"18682013146","deposit":222,"balance":222,"remark":"222","projectId":0}
     */
/*
    public int state;
    public String message;
    *//**
     * id : 1
     * account : 18682013146
     * deposit : 222
     * balance : 222
     * remark : 222
     * projectId : 0
     *//*

    public DataBean data;

    public static class DataBean {
        public int    id;
        public String account;
        public int    deposit;
        public int    balance;
        public String remark;
        public int    projectId;
    }*/



