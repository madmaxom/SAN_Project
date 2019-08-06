package mc.fhooe.at.drivingassistant.bluetooth

object BluetoothConstants {

    const val SERVICE_ID = "00001101-0000-1000-8000-00805f9b34fb"

    //region COMMUNICATION
    const val START_COMMUNICATION = "#"
    const val START_BYTE = "$"
    const val STOP_BYTE = "*"
    const val SEPERATOR = ";"
    const val ACK = "|"
    const val NACK = "?"

    const val CONNECT_COMMAND = "0:"
    const val ACC_COMMAND = "1:"
    const val TEMP_COMMAND = "2:"
    const val VEL_COMMAND = "3:"
    const val LDR_COMMAND = "4:"
    const val DIST_COMMAND = "5:"
    //endregion

}