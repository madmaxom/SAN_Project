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
import mc.fhooe.at.drivingassistant.data.LineDataWrapper
import mc.fhooe.at.drivingassistant.mvp.base.BasePresenter
import mc.fhooe.at.drivingassistant.mvp.view.ChartView


class ChartPresenter(private var context: Context) : BasePresenter<ChartView> {

    private var view: ChartView? = null
    var chartDataWrapper: ArrayList<LineDataWrapper>? = null


    override fun onAttach(view: ChartView) {
        this.view = view
//        Thread(Runnable {
//            while (true) {
//                Thread.sleep(20)
//                val array: IntArray = IntArray(4)
//                array[0] = ThreadLocalRandom.current().nextInt(30000, 45000)
//                array[1] = ThreadLocalRandom.current().nextInt(30000, 45000)
//                array[2] = ThreadLocalRandom.current().nextInt(30000, 45000)
//                array[3] = ThreadLocalRandom.current().nextInt(30000, 45000)
//                val intent: Intent = Intent()
//                intent.action = App.DATA_SOLE
//                intent.putExtra("Sole", array)
//                LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
//            }
//        }).start()
    }

    override fun onDetach() {
        view = null
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
}