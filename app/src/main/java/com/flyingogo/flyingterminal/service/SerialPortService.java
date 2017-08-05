package com.flyingogo.flyingterminal.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.model.ComBean;
import com.flyingogo.flyingterminal.utils.MyFunc;
import com.flyingogo.flyingterminal.utils.SerialHelper;

import java.io.IOException;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPortFinder;

/**
 * 作者：dfy on 1/8/2017 15:44
 * <p> //启动窗口的服务;
 * 邮箱：dengfuyao@163.com
 */

public class SerialPortService extends Service {
    private static final String PACKAGE_HEAD     = "5E";
    private static final String PACKAGE_EAD      = "5F";
    private static final int    DATA_TEMPERATURE = 5;  //温度字符串长度;
    private static final int DATA_PACKAGE_LENGTH = 7;  //卡号数据长度
    private static final int DATA_HEAD_LENGTH = 3;
    final                String PORT             = "/dev/ttyS2";
    final                String BAUDRATE         = "115200";
    private SerialControl         mControl;
    private SerialPortFinder      mSerialPortFinder;
    private LocalBroadcastManager mLocalBroadcastManager;

    // 3.温度数据
    // 5E 10 20 XX 5F
    private static String CHECK_CODE_TEMPERATURE = "5E 10 20";

    @Override
    public void onCreate() {
        mControl = new SerialControl();
        mControl.setPort(PORT);
        mControl.setBaudRate(BAUDRATE);
        OpenComPort(mControl);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
    }

    private static final String TAG = "SerialPortService";

    /**
     * 打开串口;
     *
     * @param ComPort
     */
    private void OpenComPort(SerialHelper ComPort) {
        try {
            ComPort.open();
        } catch (SecurityException e) {
            Log.e(TAG, "OpenComPort: ShowMessage(打开串口失败:没有串口读/写权限!);");
            ShowMessage("打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            Log.e(TAG, "OpenComPort: ShowMessage(打开串口失败:未知错误!");
            ShowMessage("打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            ShowMessage("打开串口失败:参数错误!");
            Log.e(TAG, "OpenComPort: ShowMessage(打开串口失败:参数错误!");
        }
    }

    private void ShowMessage(String sMsg) {
        Toast.makeText(this, sMsg, Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class SerialControl extends SerialHelper {

        //		public SerialControl(String sPort, String sBaudRate){
//			super(sPort, sBaudRate);
//		}
        public SerialControl() {

        }

        //串口接收到数据的时候会调用;
        @Override
        protected void onDataReceived(final ComBean comRecData) {
            //数据接收量大或接收时弹出软键盘，界面会卡顿,可能和6410的显示性能有关
            //直接刷新显示，接收数据量大时，卡顿明显，但接收与显示同步。
            //用线程定时刷新显示可以获得较流畅的显示效果，但是接收数据速度快于显示速度时，显示会滞后。
            //最终效果差不多-_-，线程定时刷新稍好一些。
            //DispQueue.AddQueue(ComRecData);//线程定时刷新显示(推荐)

            //  byte[] bRec = comRecData.bRec;5E 10 20 18 5F 温度
            String hexString = MyFunc.ByteArrToHex(comRecData.bRec).toString().trim();
            Log.e(TAG, "onDataReceived: rec = " + hexString + "长度 = " + hexString.length());
            if (hexString.length() >= 14) {
                if (isTemperature(hexString)) {
                    String[] split = hexString.split(" ");
                    if (split[DATA_TEMPERATURE - 1].equalsIgnoreCase(PACKAGE_EAD)) {
                        Log.e(TAG, "onDataReceived: 温度 = "+split[3] );
                        broadcastUpdateTemperature(split[3], Contants.Action.TEMPERATURE);
                    }
                } else if (hexString.length() >= 16) {
                    String card_no = getCard_No(hexString.split(" "));
                    if (!TextUtils.isEmpty(card_no)){
                        Log.e(TAG, "onDataReceived: cardNo = "+card_no );
                      broadCastUpCardNo(card_no,Contants.Action.CARD_NO);
                    }
                }
            }
           /*
            if (split[0].equalsIgnoreCase(PACKAGE_HEAD) && split.length >= DATA_TEMPERATURE) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < DATA_TEMPERATURE; i++) {
                    sb.append(split[i]);
                }
                if ()
            }
*/

        }

        private boolean isTemperature(String temperature) {
            Log.e(TAG, "isTemperature: substring = "+ temperature.substring(0, 8));
            if (temperature.substring(0, 8).equalsIgnoreCase(CHECK_CODE_TEMPERATURE)) {
                return true;
            }
            return false;
        }

        private String getCard_No(String[] split) {

            if (split!=null&&split.length>=DATA_PACKAGE_LENGTH+1){
               	for (int i = 0;i<split.length;i++){
                    if (split[i].equalsIgnoreCase("5E")&&split[i+DATA_PACKAGE_LENGTH]!=null&&split[i+DATA_PACKAGE_LENGTH].equalsIgnoreCase("5F")){
                        StringBuilder mSb = new StringBuilder();
                        for (int j = i+DATA_HEAD_LENGTH;j<i+DATA_PACKAGE_LENGTH;j++){
                            mSb.append(split[j]);
                        }
                        return mSb.toString();
                    }
                }
            }
            return  null;
        }


    }

    private void broadCastUpCardNo(String card_no, String action) {
        Intent intent = new Intent(action);
        intent.putExtra( Contants.CARD_NO,card_no);
        mLocalBroadcastManager.sendBroadcastSync(intent);
    }

    private void broadcastUpdateTemperature(String temperature, String action) {
        Intent intent = new Intent(action);

        intent.putExtra( Contants.TEMPERATURE,temperature);
        mLocalBroadcastManager.sendBroadcastSync(intent);
    }


    /**
     * 服务停止关闭串口;
     */
    @Override
    public void onDestroy() {
       CloseComPort(mControl);
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        CloseComPort(mControl);
        return super.onUnbind(intent);
    }

    private void CloseComPort(SerialHelper ComPort){
        if (ComPort!=null){
            ComPort.stopSend();
            ComPort.close();
        }
    }
}
