package mc.fhooe.at.drivingassistant

import android.app.Application

abstract class App : Application() {

    companion object {

        //region SHARED PREFS
        const val SHARED_PREFS_USER = "mc.fhooe.at.Users"
        //endregion

        const val MASK = "0000000000000000000000000000100000001100000000011100011111110000"

        //region INTENT ACTIONS
        const val ACTION_LEAVE_ACTIVITY = "ACTION_LEAVE_ACTIVITY"
        const val ACTION_SEND_DATA = "ACTION_SEND_DATA"
        const val ACTION_RECEIVE_DATA = "ACTION_RECEIVE_DATA"
        const val ACTION_COMMUNICATION_STARTED = "ACTION_COMM_STARTED"
        //endregion

        //region INTENT NAME
        const val INTENT_NAME_BLUETOOTH_DEVICE = "mc.fhooe.at.drivingassistant.bluetoothDevice"
        const val INTENT_NAME_BLUETOOTH_DATA_SEND = "mc.fhooe.at.drivingassistent.bluetoothDataSend"
        const val INTENT_NAME_BLUETOOTH_DATA_RECEIVE = "mc.fhooe.at.drivingassistent.bluetoothDataReceive"
        //endregion

    }

}