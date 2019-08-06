package mc.fhooe.at.drivingassistant.listener

import android.bluetooth.BluetoothDevice

interface OnItemClickListener {
    fun onItemClick(device : BluetoothDevice)
}