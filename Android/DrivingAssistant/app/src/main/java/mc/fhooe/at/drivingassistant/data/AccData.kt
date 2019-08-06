package mc.fhooe.at.drivingassistant.data

class AccData(var x: Double, var y: Double, var z: Double) : IData() {

    constructor(x: Double, y: Double) : this(x, y, 0.0){
        this.x = x
        this.y = y
    }

    override fun toString(): String {
        return "X: $x; Y: $y; Z: $z\n"
    }
}