package mc.fhooe.at.drivingassistant.bluetooth.requests

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothEventListener
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothRequest

class DiscoverRequest(
    private val context: Context,
    private val eventListener: IBluetoothEventListener
) : IBluetoothRequest {

    private var discoveredDevices: ArrayList<BluetoothDevice> = ArrayList()
    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


    fun discoverAvailableDevices() {
        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
        } else {
            discoveredDevices.clear()
            bluetoothAdapter.startDiscovery()
            registerReceiver()
            eventListener.onDiscovering(true)
        }
    }

    private val discoverReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            if (BluetoothDevice.ACTION_FOUND == intent.action) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                addDiscoveredDevice(device)
                eventListener.onDiscovered(device)
            }
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == intent.action) {
                eventListener.onDiscovered(discoveredDevices)
                cleanup()
            }
        }
    }

    private fun addDiscoveredDevice(discoveredDevice: BluetoothDevice) {
        if (discoveredDevice in discoveredDevices && discoveredDevice.bondState != BluetoothDevice.BOND_BONDED) {
            return
        }

        discoveredDevices.add(discoveredDevice)
    }

    private fun registerReceiver() {
        context.registerReceiver(discoverReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        context.registerReceiver(
            discoverReceiver,
            IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        )
    }

    override fun cleanup() {
        try {
            eventListener.onDiscovering(false)
            bluetoothAdapter.cancelDiscovery()
            context.unregisterReceiver(discoverReceiver)
            context.unregisterReceiver(discoverReceiver)
        }catch (e : IllegalArgumentException)
        {

        }
    }

}