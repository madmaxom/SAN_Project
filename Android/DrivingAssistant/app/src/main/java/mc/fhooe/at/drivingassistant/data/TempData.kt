package mc.fhooe.at.drivingassistant.data

class TempData(var temp: Double? = null) : IData() {

    override fun toString(): String {
        return "Temp: $temp\n"
    }
}