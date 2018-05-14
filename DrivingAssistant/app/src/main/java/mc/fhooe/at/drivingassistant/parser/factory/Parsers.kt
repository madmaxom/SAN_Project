package mc.fhooe.at.drivingassistant.parser.factory

import mc.fhooe.at.drivingassistant.data.AccData
import mc.fhooe.at.drivingassistant.data.IData
import mc.fhooe.at.drivingassistant.data.TempData
import mc.fhooe.at.drivingassistant.data.VelData

class Parsers {

    object AccParser : IParser {

        override fun parse(message: String): IData {
            val substring = message.split(";")
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

    object VelParser : IParser {

        override fun parse(message: String): IData {
            return VelData(message.toDouble())
        }
    }

}