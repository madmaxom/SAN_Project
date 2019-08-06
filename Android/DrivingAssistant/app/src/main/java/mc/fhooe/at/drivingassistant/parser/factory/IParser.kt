package mc.fhooe.at.drivingassistant.parser.factory

import mc.fhooe.at.drivingassistant.data.IData

interface IParser {
    fun parse(message: String): IData
}