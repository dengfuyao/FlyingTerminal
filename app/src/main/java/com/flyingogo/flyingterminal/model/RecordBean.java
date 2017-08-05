package com.flyingogo.flyingterminal.model;

import java.util.List;
/**
 * 作者：dfy on 7/7/2017 14:40
 * <p>行车记录:
 * 邮箱：dengfuyao@163.com
 */
public class RecordBean {
    /**
     * state : 1
     * message : 操作成功
     * data : {"dataList":[{"id":3,"cardNo":"5970300233","bikeNo":"5970300176","borrowStation":"5970309001","borrowLock":"5970309014","borrowTime":1492485107000,"returnStation":"5970309001","returnLock":"5970309014","returnTime":1492485108000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0,"borrowTime1":"2017-04-18 11:11:47","returnTime1":"2017-04-18 11:11:48"},{"id":67,"cardNo":"5970300233","bikeNo":"5970300142","borrowStation":"5970307001","borrowLock":"5970307002","borrowTime":1492487493000,"returnStation":"5970307001","returnLock":"5970307002","returnTime":1492487493000,"cost":0,"state":"2","isnet":"0","projectId":3,"flag":1,"ridingMinute":0,"borrowTime1":"2017-04-18 11:51:33","returnTime1":"2017-04-18 11:51:33"},{"id":68,"cardNo":"5970300233","bikeNo":"5970300142","borrowStation":"5970307001","borrowLock":"5970307002","borrowTime":1492487506000,"returnStation":"5970307001","returnLock":"5970307002","returnTime":1492487508000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0,"borrowTime1":"2017-04-18 11:51:46","returnTime1":"2017-04-18 11:51:48"},{"id":197,"cardNo":"5970300233","bikeNo":"5970300067","borrowStation":"5970310001","borrowLock":"5970310006","borrowTime":1492505197000,"returnStation":"5970310001","returnLock":"5970310005","returnTime":1492505206000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.1,"borrowTime1":"2017-04-18 16:46:37","returnTime1":"2017-04-18 16:46:46"},{"id":198,"cardNo":"5970300233","bikeNo":"5970300067","borrowStation":"5970310001","borrowLock":"5970310005","borrowTime":1492505215000,"returnStation":"5970310001","returnLock":"5970310004","returnTime":1492505220000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.1,"borrowTime1":"2017-04-18 16:46:55","returnTime1":"2017-04-18 16:47:00"},{"id":199,"cardNo":"5970300233","bikeNo":"5970300067","borrowStation":"5970310001","borrowLock":"5970310004","borrowTime":1492505233000,"returnStation":"5970310001","returnLock":"5970310002","returnTime":1492505239000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.1,"borrowTime1":"2017-04-18 16:47:13","returnTime1":"2017-04-18 16:47:19"},{"id":224,"cardNo":"5970300233","bikeNo":"5970300032","borrowStation":"5970311001","borrowLock":"5970311003","borrowTime":1492506071000,"returnStation":"5970311001","returnLock":"5970311003","returnTime":1492506092000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.3,"borrowTime1":"2017-04-18 17:01:11","returnTime1":"2017-04-18 17:01:32"},{"id":226,"cardNo":"5970300233","bikeNo":"5970300032","borrowStation":"5970311001","borrowLock":"5970311003","borrowTime":1492506106000,"returnStation":"5970311001","returnLock":"5970311003","returnTime":1492506227000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":2,"borrowTime1":"2017-04-18 17:01:46","returnTime1":"2017-04-18 17:03:47"},{"id":229,"cardNo":"5970300233","bikeNo":"5970300160","borrowStation":"5970311001","borrowLock":"5970311005","borrowTime":1492506258000,"returnStation":"5970311001","returnLock":"5970311005","returnTime":1492506277000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.3,"borrowTime1":"2017-04-18 17:04:18","returnTime1":"2017-04-18 17:04:37"},{"id":230,"cardNo":"5970300233","bikeNo":"5970300032","borrowStation":"5970311001","borrowLock":"5970311003","borrowTime":1492506293000,"returnStation":"5970311001","returnLock":"5970311003","returnTime":1492506304000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.2,"borrowTime1":"2017-04-18 17:04:53","returnTime1":"2017-04-18 17:05:04"}],"currentPage":1,"rowCount":117,"pageCount":12,"pageSize":10,"begin":0}
     */
    public int state;
    public String message;
    /**
     * dataList : [{"id":3,"cardNo":"5970300233","bikeNo":"5970300176","borrowStation":"5970309001","borrowLock":"5970309014","borrowTime":1492485107000,"returnStation":"5970309001","returnLock":"5970309014","returnTime":1492485108000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0,"borrowTime1":"2017-04-18 11:11:47","returnTime1":"2017-04-18 11:11:48"},{"id":67,"cardNo":"5970300233","bikeNo":"5970300142","borrowStation":"5970307001","borrowLock":"5970307002","borrowTime":1492487493000,"returnStation":"5970307001","returnLock":"5970307002","returnTime":1492487493000,"cost":0,"state":"2","isnet":"0","projectId":3,"flag":1,"ridingMinute":0,"borrowTime1":"2017-04-18 11:51:33","returnTime1":"2017-04-18 11:51:33"},{"id":68,"cardNo":"5970300233","bikeNo":"5970300142","borrowStation":"5970307001","borrowLock":"5970307002","borrowTime":1492487506000,"returnStation":"5970307001","returnLock":"5970307002","returnTime":1492487508000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0,"borrowTime1":"2017-04-18 11:51:46","returnTime1":"2017-04-18 11:51:48"},{"id":197,"cardNo":"5970300233","bikeNo":"5970300067","borrowStation":"5970310001","borrowLock":"5970310006","borrowTime":1492505197000,"returnStation":"5970310001","returnLock":"5970310005","returnTime":1492505206000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.1,"borrowTime1":"2017-04-18 16:46:37","returnTime1":"2017-04-18 16:46:46"},{"id":198,"cardNo":"5970300233","bikeNo":"5970300067","borrowStation":"5970310001","borrowLock":"5970310005","borrowTime":1492505215000,"returnStation":"5970310001","returnLock":"5970310004","returnTime":1492505220000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.1,"borrowTime1":"2017-04-18 16:46:55","returnTime1":"2017-04-18 16:47:00"},{"id":199,"cardNo":"5970300233","bikeNo":"5970300067","borrowStation":"5970310001","borrowLock":"5970310004","borrowTime":1492505233000,"returnStation":"5970310001","returnLock":"5970310002","returnTime":1492505239000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.1,"borrowTime1":"2017-04-18 16:47:13","returnTime1":"2017-04-18 16:47:19"},{"id":224,"cardNo":"5970300233","bikeNo":"5970300032","borrowStation":"5970311001","borrowLock":"5970311003","borrowTime":1492506071000,"returnStation":"5970311001","returnLock":"5970311003","returnTime":1492506092000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.3,"borrowTime1":"2017-04-18 17:01:11","returnTime1":"2017-04-18 17:01:32"},{"id":226,"cardNo":"5970300233","bikeNo":"5970300032","borrowStation":"5970311001","borrowLock":"5970311003","borrowTime":1492506106000,"returnStation":"5970311001","returnLock":"5970311003","returnTime":1492506227000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":2,"borrowTime1":"2017-04-18 17:01:46","returnTime1":"2017-04-18 17:03:47"},{"id":229,"cardNo":"5970300233","bikeNo":"5970300160","borrowStation":"5970311001","borrowLock":"5970311005","borrowTime":1492506258000,"returnStation":"5970311001","returnLock":"5970311005","returnTime":1492506277000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.3,"borrowTime1":"2017-04-18 17:04:18","returnTime1":"2017-04-18 17:04:37"},{"id":230,"cardNo":"5970300233","bikeNo":"5970300032","borrowStation":"5970311001","borrowLock":"5970311003","borrowTime":1492506293000,"returnStation":"5970311001","returnLock":"5970311003","returnTime":1492506304000,"cost":0,"state":"2","isnet":"0","projectId":3,"ridingMinute":0.2,"borrowTime1":"2017-04-18 17:04:53","returnTime1":"2017-04-18 17:05:04"}]
     * currentPage : 1
     * rowCount : 117
     * pageCount : 12
     * pageSize : 10
     * begin : 0
     */
    public DataBean data;

    public static class DataBean {
        public int currentPage;
        public int rowCount;
        public int pageCount;
        public int pageSize;
        public int begin;
        /**
         * id : 3
         * cardNo : 5970300233
         * bikeNo : 5970300176
         * borrowStation : 5970309001
         * borrowLock : 5970309014
         * borrowTime : 1492485107000
         * returnStation : 5970309001
         * returnLock : 5970309014
         * returnTime : 1492485108000
         * cost : 0
         * state : 2
         * isnet : 0
         * projectId : 3
         * ridingMinute : 0
         * borrowTime1 : 2017-04-18 11:11:47
         * returnTime1 : 2017-04-18 11:11:48
         */

        public List<DataListBean> dataList;

        public static class DataListBean {
            public int    id;
            public String cardNo;
            public String bikeNo;
            public String borrowStation;
            public String borrowLock;
            public long   borrowTime;
            public String returnStation;
            public String returnLock;
            public long   returnTime;
            public int    cost;
            public String state;
            public String isnet;
            public int    projectId;
            public double    ridingMinute;
            public String borrowTime1;
            public String returnTime1;
            //
            public String borrow_Location;
            public String return_Location;

        }
    }
    /**
     * state : 1
     * message : 操作成功
     * data : {"dataList":[{"id":2,"cardNo":"0000000000","bikeNo":"5970300176","borrowStation":"7690101001","borrowLock":"7690101002","returnStation":"5970309001","returnLock":"5970309014","returnTime":1492485091000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":10,"cardNo":"0000000000","bikeNo":"5970300129","borrowStation":"7690101001","borrowLock":"7690101002","returnStation":"5970313001","returnLock":"5970313008","returnTime":1492485667000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":17,"cardNo":"0000000000","bikeNo":"5970300087","returnStation":"5970313001","returnLock":"5970313003","returnTime":1492485830000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":25,"cardNo":"0000000000","bikeNo":"5970300030","returnStation":"5970308001","returnLock":"5970308006","returnTime":1492486043000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":33,"cardNo":"0000000000","bikeNo":"5970300153","returnStation":"5970307001","returnLock":"5970307015","returnTime":1492486237000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":35,"cardNo":"0000000000","bikeNo":"5970300087","returnStation":"5970313001","returnLock":"5970313002","returnTime":1492486254000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":37,"cardNo":"0000000000","bikeNo":"5970300141","returnStation":"5970313001","returnLock":"5970313004","returnTime":1492486277000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":40,"cardNo":"0000000000","bikeNo":"5970300226","returnStation":"5970308001","returnLock":"5970308002","returnTime":1492486437000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":50,"cardNo":"0000000000","bikeNo":"5970300169","returnStation":"5970308001","returnLock":"5970308004","returnTime":1492486612000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":82,"cardNo":"0000000000","bikeNo":"5970300014","returnStation":"5970314001","returnLock":"5970314006","returnTime":1492489312000,"cost":0,"state":"5","isnet":"0","projectId":3}],"currentPage":1,"rowCount":1030,"pageCount":103,"pageSize":10,"begin":0}
     */

   /* public int state;
    public String message;
    *//**
     * dataList : [{"id":2,"cardNo":"0000000000","bikeNo":"5970300176","borrowStation":"7690101001","borrowLock":"7690101002","returnStation":"5970309001","returnLock":"5970309014","returnTime":1492485091000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":10,"cardNo":"0000000000","bikeNo":"5970300129","borrowStation":"7690101001","borrowLock":"7690101002","returnStation":"5970313001","returnLock":"5970313008","returnTime":1492485667000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":17,"cardNo":"0000000000","bikeNo":"5970300087","returnStation":"5970313001","returnLock":"5970313003","returnTime":1492485830000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":25,"cardNo":"0000000000","bikeNo":"5970300030","returnStation":"5970308001","returnLock":"5970308006","returnTime":1492486043000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":33,"cardNo":"0000000000","bikeNo":"5970300153","returnStation":"5970307001","returnLock":"5970307015","returnTime":1492486237000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":35,"cardNo":"0000000000","bikeNo":"5970300087","returnStation":"5970313001","returnLock":"5970313002","returnTime":1492486254000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":37,"cardNo":"0000000000","bikeNo":"5970300141","returnStation":"5970313001","returnLock":"5970313004","returnTime":1492486277000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":40,"cardNo":"0000000000","bikeNo":"5970300226","returnStation":"5970308001","returnLock":"5970308002","returnTime":1492486437000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":50,"cardNo":"0000000000","bikeNo":"5970300169","returnStation":"5970308001","returnLock":"5970308004","returnTime":1492486612000,"cost":0,"state":"5","isnet":"0","projectId":3},{"id":82,"cardNo":"0000000000","bikeNo":"5970300014","returnStation":"5970314001","returnLock":"5970314006","returnTime":1492489312000,"cost":0,"state":"5","isnet":"0","projectId":3}]
     * currentPage : 1
     * rowCount : 1030
     * pageCount : 103
     * pageSize : 10
     * begin : 0
     *//*

    public DataBean data;

    public static class DataBean {
        public int currentPage;
        public int rowCount;
        public int pageCount;
        public int pageSize;
        public int begin;
        *//**
         * id : 2
         * cardNo : 0000000000
         * bikeNo : 5970300176
         * borrowStation : 7690101001
         * borrowLock : 7690101002
         * returnStation : 5970309001
         * returnLock : 5970309014
         * returnTime : 1492485091000
         * cost : 0.0
         * state : 5
         * isnet : 0
         * projectId : 3
         *//*

        public List<DataListBean> dataList;

        public static class DataListBean {
            public int    id;
            public String cardNo;
            public String bikeNo;
            public String borrowStation;
            public String borrowLock;
            public String returnStation;
            public String returnLock;
            public long   returnTime;
            public double cost;
            public String state;
            public String isnet;
            public int    projectId;
            public String borrow_Location;
            public String borrowTime;
            public String    return_Location;
            public String ridingMinute;
        }
    }*/



}
