package com.cd.mytestdemo.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.view.View;
import android.widget.TextView;
import com.cd.mytestdemo.R;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by lv.weihao on 2018/5/18.
 */
public class ConnectThread extends Thread {
    private final int BUFFER_SIZE = 1024;
    private BluetoothSocket bluetoothSocket;
    private boolean activeConnect;
    private InputStream inputStream;
    private OutputStream outputStream;
    private TextView mTvState;

    public ConnectThread(BluetoothSocket bluetoothSocket, boolean activeConnect, View view) {
        this.bluetoothSocket = bluetoothSocket;
        this.activeConnect = activeConnect;
        mTvState = view.findViewById(R.id.m_tv_state);
    }

    @Override
    public void run() {
       try {
            //如果是自动连接，则调用连接方法
           if (activeConnect) {
               bluetoothSocket.connect();
           }
           mTvState.post(new Runnable() {
               @Override
               public void run() {
                   mTvState.setText("连接成功");
               }
           });

           inputStream = bluetoothSocket.getInputStream();
           outputStream = bluetoothSocket.getOutputStream();

           byte[] buffer = new byte[BUFFER_SIZE];
           int bytes;
           while (true) {
               bytes = inputStream.read(buffer);
               if (bytes > 0) {
                   final byte[] data = new byte[bytes];
                   System.arraycopy(buffer, 0, data, 0, bytes);
                   mTvState.post(new Runnable() {
                       @Override
                       public void run() {
                            mTvState.setText(new String(data));
                       }
                   });
               }
           }

       } catch (Exception e) {
            e.printStackTrace();
       }
    }

}
