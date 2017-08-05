package com.flyingogo.flyingterminal.model;

import java.text.SimpleDateFormat;

/**
 * 接收到的数据的bean
 */
public class ComBean {
		public byte[] bRec     =null;
		public String sRecTime ="";  //时间
		public String sComPort ="";  //串口号
		public ComBean(String sPort, byte[] buffer, int size){
			sComPort=sPort;
			bRec=new byte[size];
			for (int i = 0; i < size; i++)
			{
				bRec[i]=buffer[i];
			}
			SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
			sRecTime = sDateFormat.format(new java.util.Date()); 
		}
}