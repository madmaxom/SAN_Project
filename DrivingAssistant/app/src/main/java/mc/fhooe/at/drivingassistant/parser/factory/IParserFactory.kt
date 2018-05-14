package mc.fhooe.at.drivingassistant.parser.factory

interface IParserFactory {

    fun create(command : String) : IParser

}