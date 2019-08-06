package mc.fhooe.at.drivingassistant.bluetooth.requests

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.os.Handler
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothEventListener


class EnableBluetoothRequest(
    private val context: Context, private val eventListener: IBluetoothEventListener
) {

    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    fun enableBluetooth() {
        if (!bluetoothAdapter.isEnabled) {
            bluetoothAdapter.enable()
            Handler().postDelayed({
                eventListener.onEnable(bluetoothAdapter.isEnabled)
            }, 2000)
            return
        }
        bluetoothAdapter.disable()
        Handler().postDelayed({
            eventListener.onEnable(bluetoothAdapter.isEnabled)
        }, 2000)
    }
}