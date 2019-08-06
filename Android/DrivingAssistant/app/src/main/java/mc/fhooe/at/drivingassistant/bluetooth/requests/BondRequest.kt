package mc.fhooe.at.drivingassistant.bluetooth.requests

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.Handler
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothEventListener

class BondRequest(
    private val context: Context,
    private val eventListener: IBluetoothEventListener
) {

    fun doBonding(device: BluetoothDevice) {
        if(device.bondState == BluetoothDevice.BOND_NONE){
            device.createBond()
        }else if(device.bondState == BluetoothDevice.BOND_BONDED){
            eventListener.onAlreadyBonded(device)
        }
    }
}