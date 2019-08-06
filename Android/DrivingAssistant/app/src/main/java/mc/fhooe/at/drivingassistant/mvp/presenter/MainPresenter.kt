package mc.fhooe.at.drivingassistant.mvp.presenter

import android.bluetooth.BluetoothDevice
import android.content.Context
import mc.fhooe.at.drivingassistant.util.permissions.PermissionHelper
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothEventListener
import mc.fhooe.at.drivingassistant.listener.PermissionResponseListener
import mc.fhooe.at.drivingassistant.mvp.interactor.BluetoothInteractor
import mc.fhooe.at.drivingassistant.mvp.view.MainView
import mc.fhooe.at.drivingassistant.mvp.base.BasePresenter
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.IntentFilter


class MainPresenter(private var context: Context) : BasePresenter<MainView>,
    IBluetoothEventListener, PermissionResponseListener {

    private var bluetoothInteractor: BluetoothInteractor = BluetoothInteractor(context, this)
    private var view: MainView? = null
    private lateinit var device: BluetoothDevice

    override fun onAttach(view: MainView) {
        this.view = view
        PermissionHelper.askBluetoothPermission(context, this)
        context.registerReceiver(mReceiver, IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
    }

    override fun permissionGranted(granted: Boolean) {
        if (granted) {
            bluetoothInteractor.findPairedDevices()
            bluetoothInteractor.findAvailableDevices()
        }
    }

    override fun onDetach() {
        view = null
        cleanup()
    }

    override fun onEnable(enabled: Boolean) {
        view?.setBluetoothEnabled(enabled)
        if (enabled) {
            search()
        }
    }

    override fun onDiscovering(loading: Boolean) {
        view?.showLoadingRing(loading)
    }

    override fun onDiscovered(device: BluetoothDevice) {
        view?.addAvailableDevice(device)
    }

    override fun onDiscovered(availableDevices: ArrayList<BluetoothDevice>) {
        view?.setAvailableDevices(availableDevices)
        view?.showLoadingRing(false)
    }

    override fun onPairedDevicesFound(pairedDevices: ArrayList<BluetoothDevice>) {
        view?.setPairedDevices(pairedDevices)
    }

    fun enableBluetooth() {
        bluetoothInteractor.enableBluetooth()
    }

    fun search() {
        PermissionHelper.askBluetoothPermission(context, this)
    }

    fun cleanup() {
        bluetoothInteractor.cleanUp()
        try {
            context.unregisterReceiver(mReceiver)
        } catch (_: Exception) {

        }
    }

    fun bondDevice(device: BluetoothDevice) {
        this.device = device
        bluetoothInteractor.bondDevice(device)
    }

    override fun onAlreadyBonded(device: BluetoothDevice) {
        view?.connect(device)
    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action

            if (action == BluetoothDevice.ACTION_BOND_STATE_CHANGED) {
                val state =
                    intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR)

                when (state) {
                    BluetoothDevice.BOND_BONDING -> {
                    }

                    BluetoothDevice.BOND_BONDED -> {
                        view?.connect(device)
                    }

                    BluetoothDevice.BOND_NONE -> {
                    }
                }
            }
        }
    }
}