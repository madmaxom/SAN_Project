package mc.fhooe.at.drivingassistant.data

class AccData(x: Double, y: Double, z: Double) : IData() {

    private var x: Double? = null
    private var y: Double? = null
    private var z: Double? = null

    override fun toString(): String {
        return "X: $x; Y: $y; Z: $z\n"
    }
}