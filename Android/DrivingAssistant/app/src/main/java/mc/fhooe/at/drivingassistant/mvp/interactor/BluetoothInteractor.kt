package mc.fhooe.at.drivingassistant.mvp.interactor

import android.bluetooth.BluetoothDevice
import android.content.Context
import mc.fhooe.at.drivingassistant.App
import mc.fhooe.at.drivingassistant.bluetooth.BluetoothController
import mc.fhooe.at.drivingassistant.bluetooth.interfaces.IBluetoothEventListener
import mc.fhooe.at.drivingassistant.mvp.base.BaseBluetoothInteractor
import mc.fhooe.at.drivingassistant.mvp.presenter.MainPresenter

class BluetoothInteractor(
    context: Context, mainPresenter: MainPresenter
) : BaseBluetoothInteractor {

    private val bluetoothController: BluetoothController = BluetoothController.getInstance(context, mainPresenter)

    override fun findPairedDevices() {
        bluetoothController.getPairedDevices()
    }

    override fun findAvailableDevices() {
        bluetoothController.getAvailableDevices()
    }

    override fun enableBluetooth() {
        bluetoothController.enableBluetooth()
    }

    override fun cleanUp(){
        bluetoothController.cleanUp()
    }

    override fun bondDevice(device: BluetoothDevice) {
        bluetoothController.bondDevice(device)
    }
}