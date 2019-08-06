package mc.fhooe.at.drivingassistant.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AlertDialog
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothBase
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothLowEnergy
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IConnection
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothEventListener
import mc.fhooe.at.drivingassistant.bluetooth.requests.BondRequest
import mc.fhooe.at.drivingassistant.bluetooth.requests.DiscoverRequest
import mc.fhooe.at.drivingassistant.bluetooth.requests.EnableBluetoothRequest
import mc.fhooe.at.drivingassistant.bluetooth.requests.PairedDevicesRequest


class BluetoothController private constructor(applicationContext: Context, eventListener: IBluetoothEventListener) :
     IBluetoothLowEnergy, IBluetoothBase {

    private var eventListener: IBluetoothEventListener? = null
    private var context: Context = applicationContext
    private var discoverRequest = DiscoverRequest(context, eventListener)
    private var pairedDevicesRequest = PairedDevicesRequest(context, eventListener)
    private var enableBluetoothRequest = EnableBluetoothRequest(context, eventListener)
    private var bondRequest : BondRequest = BondRequest(context, eventListener)

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: BluetoothController? = null

        fun getInstance(context: Context, eventListener: IBluetoothEventListener): BluetoothController =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildController(context, eventListener).also { INSTANCE = it }
            }

        private fun buildController(applicationContext: Context, eventListener: IBluetoothEventListener) =
            BluetoothController(applicationContext, eventListener)

    }

    override fun isCapableOfBLE() {
        if (!context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            AlertDialog.Builder(context).setTitle("Not compatible")
                .setMessage("Your phone does not support Bluetooth Low Energy")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }

    override fun getPairedDevices() {
        pairedDevicesRequest.discoverPairedDevices()
    }

    override fun getAvailableDevices() {
        discoverRequest.discoverAvailableDevices()
    }

    override fun enableBluetooth() {
        enableBluetoothRequest.enableBluetooth()
    }

    override fun bondDevice(device: BluetoothDevice){
        bondRequest.doBonding(device)
    }

    fun cleanUp() {
        discoverRequest.cleanup()
    }

}