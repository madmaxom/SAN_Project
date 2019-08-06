package mc.fhooe.at.drivingassistant.mvp.base

import android.bluetooth.BluetoothDevice

interface BaseBluetoothInteractor : BaseInteractor {

    fun findPairedDevices()
    fun findAvailableDevices()
    fun enableBluetooth()
    fun bondDevice(device: BluetoothDevice)
}