package mc.fhooe.at.drivingassistant.mvp.view

import com.github.mikephil.charting.data.Entry
import mc.fhooe.at.drivingassistant.mvp.base.BaseView

interface ChartView : BaseView {

    fun updateAccelerationEntries(list: ArrayList<Entry>)
    fun updateDistanceEntry(list: ArrayList<Entry>)
}