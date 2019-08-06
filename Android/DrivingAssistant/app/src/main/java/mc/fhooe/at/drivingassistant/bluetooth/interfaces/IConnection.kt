package mc.fhooe.at.drivingassistant.bluetooth.interfaces

import android.bluetooth.BluetoothDevice

interface IConnection {

    fun connect(device : BluetoothDevice): Int
    fun disconnect()
    fun getData()
}