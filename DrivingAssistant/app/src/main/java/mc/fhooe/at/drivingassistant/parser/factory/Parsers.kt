package mc.fhooe.at.drivingassistant.parser.factory

import mc.fhooe.at.drivingassistant.bluetooth.BluetoothConstants
import mc.fhooe.at.drivingassistant.data.*

class Parsers {

    object AccParser : IParser {
        override fun parse(message: String): IData {
            val substring = message.split(BluetoothConstants.SEPERATOR)
            return AccData(
                substring[0].toDouble(),
                substring[1].toDouble(),
                substring[2].toDouble()
            )
        }
    }

    object TempParser : IParser {
        override fun parse(message: String): IData {
            return TempData(message.toDouble())
        }
    }

    object LdrParser : IParser {
        override fun parse(message: String): IData {
            return LdrData("1" == message)
        }
    }

    object DistanceParser : IParser {
        override fun parse(message: String): IData {
            val substring = message.split(BluetoothConstants.SEPERATOR)
            return DistanceData(
                substring[0].toInt(),
                substring[1].toInt(),
                substring[2].toInt(),
                substring[3].toInt()
            )
        }
    }

    object VelParser : IParser {
        override fun parse(message: String): IData {
            return VelData(message.toDouble())
        }
    }
}