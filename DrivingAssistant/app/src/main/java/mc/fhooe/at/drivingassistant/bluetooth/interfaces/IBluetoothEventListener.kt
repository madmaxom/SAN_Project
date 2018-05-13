package mc.fhooe.at.drivingassistant.bluetooth.interfaces

import android.bluetooth.BluetoothDevice

interface IBluetoothEventListener {
    fun onEnable(enabled : Boolean)
    fun onDiscovering(loading : Boolean)
    fun onDiscovered(availableDevices: ArrayList<BluetoothDevice>)
    fun onDiscovered(device: BluetoothDevice)
    fun onPairedDevicesFound(pairedDevices: ArrayList<BluetoothDevice>)
    fun onAlreadyBonded(device: BluetoothDevice)
//        fun onConnecting()
//        fun onConnected(isSuccess: Boolean)
//        fun onPairing()
//        fun onPaired()
//        fun onDisconnecting()
//        fun onDisconnected()
}
