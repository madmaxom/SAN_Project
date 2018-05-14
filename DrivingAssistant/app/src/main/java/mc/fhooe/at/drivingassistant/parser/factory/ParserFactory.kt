package mc.fhooe.at.drivingassistant.parser.factory

import mc.fhooe.at.drivingassistant.bluetooth.BluetoothConstants
import mc.fhooe.at.drivingassistant.parser.NoParserException

class ParserFactory : IParserFactory {

    override fun create(command: String): IParser {
        when(command){
            BluetoothConstants.ACC_COMMAND ->{
                return Parsers.AccParser
            }
            BluetoothConstants.TEMP_COMMAND -> {
                return Parsers.TempParser
            }
            BluetoothConstants.VEL_COMMAND -> {
                return Parsers.VelParser
            }
        }
        throw NoParserException("No Parser defined for the provided data")
    }

}