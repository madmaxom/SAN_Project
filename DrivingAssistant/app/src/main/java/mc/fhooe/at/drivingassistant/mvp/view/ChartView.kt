package mc.fhooe.at.drivingassistant.mvp.view

import com.github.mikephil.charting.data.Entry
import mc.fhooe.at.drivingassistant.mvp.base.BaseView

interface ChartView : BaseView {

    fun updateMagEntries(list: ArrayList<Entry>)
    fun updateAccelerationEntries(list: ArrayList<Entry>)
    fun updateGyroEntries(list: ArrayList<Entry>)
    fun updateAngleEntry(list: ArrayList<Entry>)
    fun updatePressureEntry(list: ArrayList<Entry>)
}