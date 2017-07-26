package com.flyingogo.flyingterminal.module;

import java.util.List;

/**
 * 作者：dfy on 13/7/2017 14:28
 * <p>  站点信息bean;
 * 邮箱：dengfuyao@163.com
 */

public class Stationbean {
    /**
     * state : 1
     * message : 操作成功!
     * data : [{"stationNo":"0000001030","stationName":"中泰木槿路口站","stationAddress":"中泰木槿路口，治安岗停车点旁，社区活动公园旁,桂雅路小学对面","lat":22.803578,"lon":108.395047,"stationCount":30,"num":"0","returnCount":30,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000001039","stationName":"荣和山水美地站","stationAddress":"民族大道，荣和山水美地","lat":22.813191,"lon":108.401286,"stationCount":35,"num":"4","returnCount":31,"borrowCount":4,"exceptionCount":0},{"stationNo":"0000002029","stationName":"白云紫云巷口站","stationAddress":"白云紫云巷口，休闲广场前","lat":22.814707,"lon":108.39398,"stationCount":27,"num":"0","returnCount":27,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000002050","stationName":"东盟步行街站","stationAddress":"东盟步行街，格力公司门前","lat":22.808491,"lon":108.401549,"stationCount":30,"num":"0","returnCount":30,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000002052","stationName":"桂雅中老路口站","stationAddress":"桂雅中老路口、东盟公交站，桂雅路中石化加油站对面","lat":22.8012,"lon":108.395181,"stationCount":32,"num":"0","returnCount":32,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000002053","stationName":"五洲国际站","stationAddress":"中东路五洲国际旁，合作中柬路口","lat":22.807732,"lon":108.397826,"stationCount":38,"num":"2","returnCount":36,"borrowCount":2,"exceptionCount":0},{"stationNo":"0000300017","stationName":"民族大道中柬路口东南站","stationAddress":"民族影城大门前","lat":22.81274,"lon":108.399165,"stationCount":22,"num":"1","returnCount":21,"borrowCount":1,"exceptionCount":0},{"stationNo":"0000300026","stationName":"南宁规划展览馆站","stationAddress":"桂花凤岭南路口，南宁规划馆大门右侧","lat":22.798634,"lon":108.398502,"stationCount":39,"num":"0","returnCount":39,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000300028","stationName":"中泰中柬路口站","stationAddress":"中国银行前","lat":22.806596,"lon":108.394248,"stationCount":41,"num":"0","returnCount":41,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000300033","stationName":"云景月湾路口站","stationAddress":"环保厅对面","lat":22.816199,"lon":108.394993,"stationCount":31,"num":"0","returnCount":31,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000300052","stationName":"玉兰路小学站","stationAddress":"玉兰路林业新村一区大门右侧，玉兰路小学大门左侧，人行过街天桥底下","lat":22.825816,"lon":108.393507,"stationCount":35,"num":"0","returnCount":35,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000300074","stationName":"金菊路中站","stationAddress":"林业新村东区出入口右侧","lat":22.827902,"lon":108.394014,"stationCount":42,"num":"2","returnCount":40,"borrowCount":2,"exceptionCount":0},{"stationNo":"0000300079","stationName":"佛子凤凰岭路口站","stationAddress":"佛子岭路，在水一方侧门，公车站","lat":22.832962,"lon":108.403558,"stationCount":26,"num":"0","returnCount":26,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000300081","stationName":"佛子岭翠竹路口站","stationAddress":"佛子岭路，翠竹路口对面，荣和千千树售楼中心左边","lat":22.831349,"lon":108.39573,"stationCount":40,"num":"0","returnCount":40,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000300098","stationName":"月湾云景路口东站","stationAddress":"荣和公园尊府大门对面","lat":22.822445,"lon":108.404829,"stationCount":43,"num":"0","returnCount":43,"borrowCount":0,"exceptionCount":0},{"stationNo":"0000300160","stationName":"中柬民族路口学站","stationAddress":"中柬路，民族影城侧边","lat":22.812323,"lon":108.398996,"stationCount":45,"num":"2","returnCount":43,"borrowCount":2,"exceptionCount":0},{"stationNo":"0000300294","stationName":"民族中柬路口西北站","stationAddress":"桂冠电力小区，交通设计大厦左前方","lat":22.813319,"lon":108.398027,"stationCount":22,"num":"3","returnCount":21,"borrowCount":1,"exceptionCount":0},{"stationNo":"0000300430","stationName":"民族青秀路口东南站","stationAddress":"万象城前，地铁出入口C站旁","lat":22.812655,"lon":108.392758,"stationCount":23,"num":"2","returnCount":21,"borrowCount":2,"exceptionCount":0}]
     */

    public int state;
    public String message;
    /**
     * stationNo : 0000001030
     * stationName : 中泰木槿路口站
     * stationAddress : 中泰木槿路口，治安岗停车点旁，社区活动公园旁,桂雅路小学对面
     * lat : 22.803578
     * lon : 108.395047
     * stationCount : 30
     * num : 0
     * returnCount : 30
     * borrowCount : 0
     * exceptionCount : 0
     */

    public List<DataBean> data;

    public static class DataBean {
        public String stationNo;
        public String stationName;
        public String stationAddress;
        public double lat;
        public double lon;
        public int    stationCount;
        public String num;
        public int    returnCount;
        public int    borrowCount;
        public int    exceptionCount;
    }


    /**
     * state : 1
     * message : 操作成功!
     * data : [{"stationNo":"0000001030","stationName":"中泰木槿路口站","stationAddress":"中泰木槿路口，治安岗停车点旁，社区活动公园旁,桂雅路小学对面","lat":22.803578,"lon":108.395047,"stationCount":30,"num":"0"},{"stationNo":"0000001039","stationName":"荣和山水美地站","stationAddress":"民族大道，荣和山水美地","lat":22.813191,"lon":108.401286,"stationCount":35,"num":"4"},{"stationNo":"0000002029","stationName":"白云紫云巷口站","stationAddress":"白云紫云巷口，休闲广场前","lat":22.814707,"lon":108.39398,"stationCount":27,"num":"0"},{"stationNo":"0000002050","stationName":"东盟步行街站","stationAddress":"东盟步行街，格力公司门前","lat":22.808491,"lon":108.401549,"stationCount":30,"num":"0"},{"stationNo":"0000002052","stationName":"桂雅中老路口站","stationAddress":"桂雅中老路口、东盟公交站，桂雅路中石化加油站对面","lat":22.8012,"lon":108.395181,"stationCount":32,"num":"0"},{"stationNo":"0000002053","stationName":"五洲国际站","stationAddress":"中东路五洲国际旁，合作中柬路口","lat":22.807732,"lon":108.397826,"stationCount":38,"num":"2"},{"stationNo":"0000300017","stationName":"民族大道中柬路口东南站","stationAddress":"民族影城大门前","lat":22.81274,"lon":108.399165,"stationCount":22,"num":"1"},{"stationNo":"0000300026","stationName":"南宁规划展览馆站","stationAddress":"桂花凤岭南路口，南宁规划馆大门右侧","lat":22.798634,"lon":108.398502,"stationCount":39,"num":"0"},{"stationNo":"0000300028","stationName":"中泰中柬路口站","stationAddress":"中国银行前","lat":22.806596,"lon":108.394248,"stationCount":41,"num":"0"},{"stationNo":"0000300033","stationName":"云景月湾路口站","stationAddress":"环保厅对面","lat":22.816199,"lon":108.394993,"stationCount":31,"num":"0"},{"stationNo":"0000300052","stationName":"玉兰路小学站","stationAddress":"玉兰路林业新村一区大门右侧，玉兰路小学大门左侧，人行过街天桥底下","lat":22.825816,"lon":108.393507,"stationCount":35,"num":"0"},{"stationNo":"0000300074","stationName":"金菊路中站","stationAddress":"林业新村东区出入口右侧","lat":22.827902,"lon":108.394014,"stationCount":42,"num":"2"},{"stationNo":"0000300079","stationName":"佛子凤凰岭路口站","stationAddress":"佛子岭路，在水一方侧门，公车站","lat":22.832962,"lon":108.403558,"stationCount":26,"num":"0"},{"stationNo":"0000300081","stationName":"佛子岭翠竹路口站","stationAddress":"佛子岭路，翠竹路口对面，荣和千千树售楼中心左边","lat":22.831349,"lon":108.39573,"stationCount":40,"num":"0"},{"stationNo":"0000300098","stationName":"月湾云景路口东站","stationAddress":"荣和公园尊府大门对面","lat":22.822445,"lon":108.404829,"stationCount":43,"num":"0"},{"stationNo":"0000300160","stationName":"中柬民族路口学站","stationAddress":"中柬路，民族影城侧边","lat":22.812323,"lon":108.398996,"stationCount":45,"num":"2"},{"stationNo":"0000300294","stationName":"民族中柬路口西北站","stationAddress":"桂冠电力小区，交通设计大厦左前方","lat":22.813319,"lon":108.398027,"stationCount":22,"num":"3"},{"stationNo":"0000300430","stationName":"民族青秀路口东南站","stationAddress":"万象城前，地铁出入口C站旁","lat":22.812655,"lon":108.392758,"stationCount":23,"num":"2"}]
     */
/*
    public int state;
    public String message;
    *//**
     * stationNo : 0000001030
     * stationName : 中泰木槿路口站
     * stationAddress : 中泰木槿路口，治安岗停车点旁，社区活动公园旁,桂雅路小学对面
     * lat : 22.803578
     * lon : 108.395047
     * stationCount : 30
     * num : 0
     *//*

    public List<DataBean> data;

    public static class DataBean {
        public String stationNo;
        public String stationName;
        public String stationAddress;
        public double lat;
        public double lon;
        public int    stationCount;
        public String num;
    }

 */

}
