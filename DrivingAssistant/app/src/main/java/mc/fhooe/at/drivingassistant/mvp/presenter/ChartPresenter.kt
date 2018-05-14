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
                is AccData -> addAccelerationEntry(data)
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
        }

        view?.updateAccelerationEntries(list)
    }
}