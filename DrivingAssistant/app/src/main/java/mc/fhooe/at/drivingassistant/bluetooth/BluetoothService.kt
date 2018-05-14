package mc.fhooe.at.drivingassistant.bluetooth

import android.app.Service
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import mc.fhooe.at.drivingassistant.App
import mc.fhooe.at.drivingassistant.Logging
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import mc.fhooe.at.drivingassistant.bluetooth.BluetoothConstants.CONNECT_COMMAND
import mc.fhooe.at.drivingassistant.bluetooth.BluetoothConstants.START_BYTE
import mc.fhooe.at.drivingassistant.bluetooth.BluetoothConstants.START_COMMUNICATION
import mc.fhooe.at.drivingassistant.bluetooth.BluetoothConstants.STOP_BYTE
import mc.fhooe.at.drivingassistant.data.IData
import mc.fhooe.at.drivingassistant.parser.MessageHandler


class BluetoothService : Service() {

    private var device: BluetoothDevice? = null

    private var binder: LocalBinder = LocalBinder()
    private var context: Context = this

    private var socket: BluetoothSocket? = null
    private var bluetoothThread: ConnectedThread? = null

    inner class LocalBinder : Binder() {
        internal val service: BluetoothService
            get() = BluetoothService()
    }

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        close()
        return super.onUnbind(intent)
    }

    fun close() {
        socket?.close()
        stopSelf()
    }

    override fun onDestroy() {
        socket?.close()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        device = intent?.extras?.getParcelable(App.INTENT_NAME_BLUETOOTH_DEVICE)
        if (device != null) {
            connect(device)
        } else {
            broadcastUpdate(App.ACTION_LEAVE_ACTIVITY)
            stopSelf()
        }
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(sendDataReceiver, IntentFilter(App.ACTION_SEND_DATA))
        return START_STICKY
    }

    private fun connect(device: BluetoothDevice?) {
        socket =
                device?.createRfcommSocketToServiceRecord(UUID.fromString(BluetoothConstants.SERVICE_ID))
        socket?.connect()
        bluetoothThread = ConnectedThread(socket)
        bluetoothThread?.start()
        if (socket?.isConnected!!) {
            bluetoothThread?.write(START_BYTE + CONNECT_COMMAND + START_COMMUNICATION + STOP_BYTE)
        }
    }

    private inner class ConnectedThread(socket: BluetoothSocket?) : Thread() {
        private var inStream: InputStream? = null
        private var outStream: OutputStream? = null
        private var messageHandler : MessageHandler? = null

        init {
            inStream = socket?.inputStream
            outStream = socket?.outputStream
            messageHandler = MessageHandler()
        }

        override fun run() {
            val buffer = ByteArray(256)
            var bytes: Int?
            val message = StringBuilder()

            while (true) {
                try {
                    bytes = inStream?.read(buffer)
                    if (bytes != null) {
                        message.append(String(buffer, 0, bytes))
                        Logging.everything(javaClass.simpleName, message.toString())
                        if (message.contains(START_COMMUNICATION)) {
                            broadcastUpdate(App.ACTION_COMMUNICATION_STARTED)
                        } else {
                            val data = messageHandler?.handle(message.toString())
                            data?.let { broadcastUpdate(App.ACTION_RECEIVE_DATA, it) }
                        }
                        message.delete(0, message.length - 1)
                    }
                } catch (e: IOException) {
                    interrupt()
                    broadcastUpdate(App.ACTION_LEAVE_ACTIVITY)
                    break
                }
            }
        }

        fun write(input: String) {
            val msgBuffer = input.toByteArray()
            try {
                outStream?.write(msgBuffer)
            } catch (e: IOException) {
                Logging.error(this.javaClass.simpleName, e.message.toString())
                broadcastUpdate(App.ACTION_LEAVE_ACTIVITY)
            }

        }
    }

    //************************************************************************************
    //region BROADCASTS

    private val sendDataReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val data = intent.extras.getString(App.INTENT_NAME_BLUETOOTH_DATA_SEND)
            bluetoothThread?.write(data)
        }
    }

    private fun broadcastUpdate(action: String) {
        val intent = Intent(action)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun broadcastUpdate(action: String, data: String) {
        val intent = Intent(action)
        intent.putExtra(App.INTENT_NAME_BLUETOOTH_DATA_RECEIVE, data)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun broadcastUpdate(action: String, data: IData) {
        val intent = Intent(action)
        intent.putExtra(App.INTENT_NAME_BLUETOOTH_DATA_RECEIVE, data)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    //endregion
    //************************************************************************************

    //************************************************************************************
    //region LOGGING DATA

    //endregion
    //************************************************************************************
}