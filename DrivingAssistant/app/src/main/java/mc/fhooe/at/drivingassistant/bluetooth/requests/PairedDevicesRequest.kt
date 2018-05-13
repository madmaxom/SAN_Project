package mc.fhooe.at.drivingassistant.bluetooth.requests

import android.bluetooth.BluetoothAdapter
import android.content.Context
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothEventListener
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothRequest

class PairedDevicesRequest(
    private val context: Context, private val eventListener: IBluetoothEventListener
) {

    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    fun discoverPairedDevices() {
        eventListener.onPairedDevicesFound(ArrayList(bluetoothAdapter.bondedDevices))
    }

}