package mc.fhooe.at.drivingassistant.mvp.presenter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import mc.fhooe.at.drivingassistant.App
import mc.fhooe.at.drivingassistant.data.AccData
import mc.fhooe.at.drivingassistant.data.LdrData
import mc.fhooe.at.drivingassistant.data.TempData
import mc.fhooe.at.drivingassistant.mvp.base.BasePresenter
import mc.fhooe.at.drivingassistant.mvp.view.LogView

class LogPresenter(private var context: Context) : BasePresenter<LogView> {

    private var view: LogView? = null

    private val commStartedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            view?.connected()
        }
    }

    private val dataReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            var data = intent.extras.get(App.INTENT_NAME_BLUETOOTH_DATA_RECEIVE)
            when(data){
                is AccData -> view?.setAcc(data)
                is TempData -> view?.setTemp(data)
                is LdrData -> view?.setBrightImage(data)
            }
        }
    }

    override fun onAttach(view: LogView) {
        this.view = view
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(commStartedReceiver, IntentFilter(App.ACTION_COMMUNICATION_STARTED))
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(dataReceiver, IntentFilter(App.ACTION_RECEIVE_DATA))
    }

    override fun onDetach() {
        view = null
        try {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(commStartedReceiver)
            LocalBroadcastManager.getInstance(context).unregisterReceiver(dataReceiver)
        } catch (ex: Exception) {
        }
    }

}