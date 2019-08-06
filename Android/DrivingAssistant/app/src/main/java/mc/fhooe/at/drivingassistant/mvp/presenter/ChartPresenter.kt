package mc.fhooe.at.drivingassistant.mvp.presenter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import mc.fhooe.at.drivingassistant.App
import mc.fhooe.at.drivingassistant.Logging
import mc.fhooe.at.drivingassistant.R
import mc.fhooe.at.drivingassistant.data.AccData
import mc.fhooe.at.drivingassistant.data.DistanceData
import mc.fhooe.at.drivingassistant.data.LineDataWrapper
import mc.fhooe.at.drivingassistant.data.TempData
import mc.fhooe.at.drivingassistant.mvp.base.BasePresenter
import mc.fhooe.at.drivingassistant.mvp.view.ChartView


class ChartPresenter(private var context: Context) : BasePresenter<ChartView> {

    private var view: ChartView? = null
    var chartDataWrapper: ArrayList<LineDataWrapper>? = null

    private val dataReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val data = intent.extras.get(App.INTENT_NAME_BLUETOOTH_DATA_RECEIVE)
            when(data){
                is AccData -> {
                    addAccelerationEntry(data)
                }
                is DistanceData -> {
                    addDistanceEntry(data)
                }
            }
        }
    }

    override fun onAttach(view: ChartView) {
        this.view = view
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(dataReceiver, IntentFilter(App.ACTION_RECEIVE_DATA))
    }

    override fun onDetach() {
        view = null
        try {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(dataReceiver)
        } catch (ex: Exception) {
        }
    }

    private fun createSet(label: String, colorId: Int): LineDataSet {
        val set = LineDataSet(null, label)
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.color = ContextCompat.getColor(context, colorId)
        set.lineWidth = 1.5f
        set.fillAlpha = 65
        set.fillColor = ContextCompat.getColor(context, colorId)
        set.highLightColor = Color.rgb(244, 117, 117)
        set.setDrawValues(false)
        set.setDrawCircles(false)
        return set
    }

    fun addDistanceEntry(streamData: DistanceData) {
        val list: ArrayList<Entry> = ArrayList()
        val data = chartDataWrapper?.get(1)?.lineData
        var set = data?.getDataSetByIndex(0)
        if (set == null) {
            set = createSet("Front", R.color.chartColor1)
            data?.addDataSet(set)
        }

        var set1 = data?.getDataSetByIndex(1)
        if (set1 == null) {
            set1 = createSet("B Middle", R.color.chartColor2)
            data?.addDataSet(set1)
        }

        var set2 = data?.getDataSetByIndex(2)
        if (set2 == null) {
            set2 = createSet("B Left", R.color.chartColor3)
            data?.addDataSet(set2)
        }

        var set3 = data?.getDataSetByIndex(3)
        if (set3 == null) {
            set3 = createSet("B Right", R.color.chartColor4)
            data?.addDataSet(set3)
        }

        if (data != null) {
            list.add(
                Entry(
                    data.getDataSetByIndex(0).entryCount.toFloat(),
                    streamData.distanceFront.toFloat()
                )
            )
            list.add(
                Entry(
                    data.getDataSetByIndex(1).entryCount.toFloat(),
                    streamData.distanceBackM.toFloat()
                )
            )
            list.add(
                Entry(
                    data.getDataSetByIndex(2).entryCount.toFloat(),
                    streamData.distanceBackL.toFloat()
                )
            )
            list.add(
                Entry(
                    data.getDataSetByIndex(3).entryCount.toFloat(),
                    streamData.distanceBackR.toFloat()
                )
            )
        }

        view?.updateDistanceEntry(list)
    }

    fun addAccelerationEntry(streamData: AccData) {
        val list: ArrayList<Entry> = ArrayList()
        val data = chartDataWrapper?.get(0)?.lineData
        var set = data?.getDataSetByIndex(0)
        if (set == null) {
            set = createSet("X", R.color.chartColor1)
            data?.addDataSet(set)
        }

        var set1 = data?.getDataSetByIndex(1)
        if (set1 == null) {
            set1 = createSet("Y", R.color.chartColor2)
            data?.addDataSet(set1)
        }

        var set2 = data?.getDataSetByIndex(2)
        if (set2 == null) {
            set2 = createSet("Z", R.color.chartColor3)
            data?.addDataSet(set2)
        }

        if (data != null) {
            list.add(
                Entry(
                    data.getDataSetByIndex(0).entryCount.toFloat(),
                    streamData.x.toFloat()
                )
            )
            list.add(
                Entry(
                    data.getDataSetByIndex(1).entryCount.toFloat(),
                    streamData.y.toFloat()
                )
            )
            list.add(
                Entry(
                    data.getDataSetByIndex(2).entryCount.toFloat(),
                    streamData.z.toFloat()
                )
            )
        }

        view?.updateAccelerationEntries(list)
    }
}