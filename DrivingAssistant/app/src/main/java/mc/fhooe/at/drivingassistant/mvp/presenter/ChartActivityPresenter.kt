package mc.fhooe.at.drivingassistant.mvp.presenter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import mc.fhooe.at.drivingassistant.App
import mc.fhooe.at.drivingassistant.Logging
import mc.fhooe.at.drivingassistant.mvp.base.BasePresenter
import mc.fhooe.at.drivingassistant.mvp.view.ChartActivityView


class ChartActivityPresenter(private var context: Context) : BasePresenter<ChartActivityView> {


    private var view: ChartActivityView? = null

    private val finishActivityReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            view?.finishActivity()
        }
    }

    override fun onAttach(view: ChartActivityView) {
        this.view = view
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(finishActivityReceiver, IntentFilter(App.ACTION_LEAVE_ACTIVITY))
    }

    override fun onDetach() {
        view = null
        try {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(finishActivityReceiver)
        } catch (ex: IllegalArgumentException) {
            Logging.error(javaClass.simpleName, ex.message.toString())
        }
    }
}