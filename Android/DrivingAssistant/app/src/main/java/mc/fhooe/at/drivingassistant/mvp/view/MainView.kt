package mc.fhooe.at.drivingassistant.mvp.view

import android.bluetooth.BluetoothDevice
import mc.fhooe.at.drivingassistant.mvp.base.BaseView


interface MainView : BaseView {

    fun setPairedDevices(items: ArrayList<BluetoothDevice>)
    fun setAvailableDevices(items: ArrayList<BluetoothDevice>)
    fun addAvailableDevice(device: BluetoothDevice)
    fun setBluetoothEnabled(enabled: Boolean)
    fun showLoadingRing(loading: Boolean)
    fun connect(device: BluetoothDevice)
}