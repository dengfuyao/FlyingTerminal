package com.flyingogo.flyingterminal.module;

/**
 * 作者：dfy on 8/7/2017 15:28
 * <p> 余额bean类:
 * 邮箱：dengfuyao@163.com
 */

public class BalanceBean {
    /**
     * state : 1
     * message : 操作成功
     * data : {"id":1,"account":"18682013146","deposit":222,"balance":222,"remark":"222","projectId":0}
     */

    public int state;
    public String message;
    /**
     * id : 1
     * account : 18682013146
     * deposit : 222
     * balance : 222
     * remark : 222
     * projectId : 0
     */

    public DataBean data;

    public static class DataBean {
        public int    id;
        public String account;
        public int    deposit;
        public int    balance;
        public String remark;
        public int    projectId;
    }
/*
    *//**
     * cardNo : 3960102129
     * cardType : 0
     * cardBalance : 50.0
     * cardState : 0
     *//*

    public String cardNo;   //卡号
    public String cardType;  //卡类型
    public double cardBalance;//余额
    public String cardState;  //状态 ;*/

}
