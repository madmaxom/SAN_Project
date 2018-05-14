package mc.fhooe.at.drivingassistant.parser

import mc.fhooe.at.drivingassistant.data.IData
import java.util.regex.Pattern

abstract class IMessageHandler {

    private val pattern: Pattern = Pattern.compile("\\\$[0-9]:.+\\*")

    abstract fun handle(message: String): IData

    fun extractCommand(message: String): String {
        val substring = message.substringAfter("$")
        return substring.substringBefore(":")
    }

    fun extractData(message: String): String {
        val substring = message.substringAfter(":")
        return substring.substringBefore("*")
    }

    fun valid(message: String): Boolean {
        val m = pattern.matcher(message)
        return m.find()
    }
}