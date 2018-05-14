package mc.fhooe.at.drivingassistant.data

class VelData(vel: Double) : IData() {

    private var vel: Double? = null

    override fun toString(): String {
        return "Temp: $vel\n"
    }
}
