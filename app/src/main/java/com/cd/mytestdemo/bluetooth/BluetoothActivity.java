package com.cd.mytestdemo.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.cd.mytestdemo.R;
import com.cd.mytestdemo.common.view.util.PopUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by lv.weihao on 2018/5/18.
 */
public class BluetoothActivity extends AppCompatActivity {

    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_btn_open)
    Button mBtnOpen;
    @BindView(R.id.m_btn_close)
    Button mBtnClose;
    @BindView(R.id.m_btn_find)
    Button mBtnFind;
    @BindView(R.id.m_btn_find_close)
    Button mBtnFindClose;
    @BindView(R.id.m_btn_send)
    Button mBtnSend;
    private BluetoothAdapter bluetoothAdapter;
    private CommonRecycleAdapter commonRecycleAdapter;
    private List<BluetoothDevice> mList;

    private static final UUID BT_UUID = UUID.fromString("02001101-0001-1000-8080-00805F9BA9BA");

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //避免重复添加已经绑定过的设备
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    //此处是列表的adapter
                    commonRecycleAdapter.add(device);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                PopUtil.showToast("开始搜索");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                PopUtil.showToast("搜索结束");
            }
        }
    };
    private ConnectThread conntectThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);

        mList = new ArrayList<>();
        commonRecycleAdapter = new CommonRecycleAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(commonRecycleAdapter);
        commonRecycleAdapter.setOnItemClick(new CommonRecycleAdapter.OnItemClick() {
            @Override
            public void onItemClick(View v, int postion) {
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                BluetoothDevice device = commonRecycleAdapter.getData(postion);
                connectDevice(v, device);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
    }

    private void openBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            PopUtil.showToast("当前设备不支持蓝牙功能");
        }
        if (!bluetoothAdapter.isEnabled()) {
            /* Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(i);*/
            bluetoothAdapter.enable();
        }
        //开启被其他蓝牙发现的功能
        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            //设置为一直开启
            i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            startActivity(i);
        }

        getHasConnect();
    }

    private void startSearch() {
        bluetoothAdapter.startDiscovery();
    }

    private void stopSearch() {
        bluetoothAdapter.cancelDiscovery();
    }

    private void getHasConnect() {
        Set<BluetoothDevice> pariedDevice = bluetoothAdapter.getBondedDevices();
        if (pariedDevice.size() > 0) {
            for (BluetoothDevice device : pariedDevice) {
                commonRecycleAdapter.add(device);
            }
        }
    }

    private void connectDevice(View view, BluetoothDevice device) {
        try {
            //创建socket
            BluetoothSocket bluetoothSocket = device.createRfcommSocketToServiceRecord(BT_UUID);
            conntectThread = new ConnectThread(bluetoothSocket, true, view);
            conntectThread.start();
        } catch (Exception e) {

        }
    }

    @OnClick({R.id.m_btn_open, R.id.m_btn_close, R.id.m_btn_find, R.id.m_btn_find_close, R.id.m_btn_send, R.id.m_recycler_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_btn_open:
                openBluetooth();
                break;
            case R.id.m_btn_close:
                break;
            case R.id.m_btn_find:
                startSearch();
                break;
            case R.id.m_btn_find_close:
                stopSearch();
                break;
            case R.id.m_btn_send:
                break;
            case R.id.m_recycler_view:
                break;
        }
    }
}
