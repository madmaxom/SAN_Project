package mc.fhooe.at.drivingassistant.data

class TempData(temp: Double) : IData() {

    private var temp: Double? = null

    override fun toString(): String {
        return "Temp: $temp\n"
    }
}