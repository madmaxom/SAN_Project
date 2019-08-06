package mc.fhooe.at.drivingassistant.activity

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import mc.fhooe.at.drivingassistant.App
import mc.fhooe.at.drivingassistant.R
import mc.fhooe.at.drivingassistant.listener.OnItemClickListener
import mc.fhooe.at.drivingassistant.mvp.presenter.MainPresenter
import mc.fhooe.at.drivingassistant.mvp.view.MainView
import mc.fhooe.at.drivingassistant.view.recyclerView.BluetoothViewAdapter
import mc.fhooe.at.drivingassistant.view.recyclerView.NoScrollLayoutManager
import mc.fhooe.at.drivingassistant.view.recyclerView.SimpleDividerItemDecoration

class MainActivity : AppCompatActivity(), MainView, OnItemClickListener, View.OnClickListener {

    private lateinit var presenter: MainPresenter
    private lateinit var pairedAdapter: BluetoothViewAdapter
    private lateinit var availableAdapter: BluetoothViewAdapter
    private var bluetoothDevices: ArrayList<BluetoothDevice> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)

        onAttachView()
        initView()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun initView() {
        availableDevices.layoutManager = NoScrollLayoutManager(this, LinearLayout.VERTICAL, false, false)
        availableDevices.addItemDecoration(SimpleDividerItemDecoration(this))
        availableAdapter = BluetoothViewAdapter(bluetoothDevices, this)
        availableDevices.adapter = availableAdapter

        pairedDevices.layoutManager = NoScrollLayoutManager(this, LinearLayout.VERTICAL, false, false)
        pairedDevices.addItemDecoration(SimpleDividerItemDecoration(this))
        pairedAdapter = BluetoothViewAdapter(bluetoothDevices, this)

        bluetoothSwitch.isChecked = BluetoothAdapter.getDefaultAdapter().isEnabled
        bluetoothSwitch.setOnClickListener(this)
        searchButton.setOnClickListener(this)
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.bluetoothSwitch -> {
                presenter.enableBluetooth()
            }
            R.id.searchButton -> {
                if (searchText.text == "STOP") {
                    presenter.search()
                } else {
                    bluetoothDevices.clear()
                    availableAdapter.notifyDataSetChanged()
                    presenter.search()
                }
            }
        }
    }

    override fun setPairedDevices(items: ArrayList<BluetoothDevice>) {
        pairedAdapter = BluetoothViewAdapter(items, this)
        pairedDevices.adapter = pairedAdapter
    }

    override fun setAvailableDevices(items: ArrayList<BluetoothDevice>) {
        for (device in items) {
            if (device !in bluetoothDevices) {
                if (device.name != null) {
                    bluetoothDevices.add(device)
                    availableAdapter.notifyItemInserted(availableAdapter.itemCount - 1)
                }
            }
        }
    }

    override fun setBluetoothEnabled(enabled: Boolean) {
        if (!enabled) {
            availableAdapter.clear()
            pairedAdapter.clear()
        }
    }

    override fun addAvailableDevice(device: BluetoothDevice) {
        if (device !in bluetoothDevices) {
            if (device.name != null) {
                bluetoothDevices.add(device)
                availableAdapter.notifyItemInserted(availableAdapter.itemCount - 1)
            }
            else{
                if(device.address != null){
                    bluetoothDevices.add(device)
                    availableAdapter.notifyItemInserted(availableAdapter.itemCount)
                }
            }
        }
    }

    override fun onItemClick(device: BluetoothDevice) {
        presenter.bondDevice(device)
    }

    override fun connect(device: BluetoothDevice) {
        presenter.cleanup()
        val intent = Intent(this, ChartActivity::class.java)
        intent.putExtra(App.INTENT_NAME_BLUETOOTH_DEVICE, device)
        startActivity(intent)
    }

    override fun showLoadingRing(loading: Boolean) {
        when (loading) {
            false -> {
                searchText.text = "SEARCH"
                progressBar.visibility = View.GONE
                searchImage.visibility = View.VISIBLE
            }
            true -> {
                searchText.text = "STOP"
                searchImage.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        }
    }

}