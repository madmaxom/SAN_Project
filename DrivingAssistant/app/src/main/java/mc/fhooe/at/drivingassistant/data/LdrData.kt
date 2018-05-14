package mc.fhooe.at.drivingassistant.data

class LdrData(var on : Boolean) : IData() {

    override fun toString(): String {
        return "X: $on;\n"
    }
}