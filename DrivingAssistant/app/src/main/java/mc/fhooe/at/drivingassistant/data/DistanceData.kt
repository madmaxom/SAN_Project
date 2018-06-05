package mc.fhooe.at.drivingassistant.data

class DistanceData(
    var distanceFront: Int,
    var distanceBackM: Int,
    var distanceBackL: Int,
    var distanceBackR: Int
) : IData() {

    override fun toString(): String {
        return "Front: $distanceFront; BackM: $distanceBackM; BackL: $distanceBackL; BackR: $distanceBackR;\n"
    }
}