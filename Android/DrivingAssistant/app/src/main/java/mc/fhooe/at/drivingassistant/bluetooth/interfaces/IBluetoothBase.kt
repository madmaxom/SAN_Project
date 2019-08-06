package mc.fhooe.at.drivingassistant.bluetooth.interfaces

import android.bluetooth.BluetoothDevice

interface IBluetoothBase {

    fun getPairedDevices()
    fun getAvailableDevices()
    fun enableBluetooth()
    fun bondDevice(device: BluetoothDevice)
}