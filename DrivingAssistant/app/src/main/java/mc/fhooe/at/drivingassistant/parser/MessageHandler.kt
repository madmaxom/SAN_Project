package mc.fhooe.at.drivingassistant.parser

import mc.fhooe.at.drivingassistant.data.IData
import mc.fhooe.at.drivingassistant.parser.factory.ParserFactory

class MessageHandler : IMessageHandler() {

    private var factory: ParserFactory = ParserFactory()

    override fun handle(message: String): IData {
        if (valid(message)) {
            val parser = factory.create(extractCommand(message))
            return parser.parse(extractData(message))
        }
        return IData()
    }
}