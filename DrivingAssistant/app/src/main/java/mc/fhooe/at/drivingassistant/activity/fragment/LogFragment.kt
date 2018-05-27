package mc.fhooe.at.drivingassistant.activity.fragment

import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_log.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import mc.fhooe.at.drivingassistant.App
import mc.fhooe.at.drivingassistant.R
import mc.fhooe.at.drivingassistant.mvp.presenter.LogPresenter
import mc.fhooe.at.drivingassistant.mvp.view.LogView
import mc.fhooe.at.drivingassistant.util.Animator
import mc.fhooe.at.drivingassistant.util.PreferenceHelper
import mc.fhooe.at.drivingassistant.view.ConnectionDialog
import mc.fhooe.at.drivingassistant.util.PreferenceHelper.get
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.widget.ImageButton
import mc.fhooe.at.drivingassistant.data.AccData
import mc.fhooe.at.drivingassistant.data.LdrData
import mc.fhooe.at.drivingassistant.data.TempData
import mc.fhooe.at.drivingassistant.listener.PermissionResponseListener
import mc.fhooe.at.drivingassistant.util.permissions.PermissionHelper


class LogFragment : Fragment(), LogView {

    private var presenter: LogPresenter? = null
    private var actionBar: ActionBar? = null
    private var dialog: ConnectionDialog? = null
    private var job: Job? = null
    private var batteryLevel: TextView? = null
    private var brightness: ImageButton? = null

    private val timeOut: Int = 10
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        presenter = activity?.let { LogPresenter(it) }
        prefs = context?.let { PreferenceHelper.defaultPrefs(it) }
    }

    //region View
    override fun onDetachView() {
        presenter?.onDetach()
    }

    override fun onAttachView() {
        presenter?.onAttach(this)
    }

    override fun initView() {
        actionBar = (activity as AppCompatActivity).supportActionBar
        val fm = activity?.supportFragmentManager
        dialog = ConnectionDialog()
        dialog?.show(fm, "Sample Fragment")
        displayCancelButtonAfterTimeout(timeOut)
        batteryLevel = actionBar?.customView?.findViewById(R.id.batteryLevel)
        brightness = actionBar?.customView?.findViewById(R.id.imageButton)
    }

    //endregion

    override fun onResume() {
        super.onResume()
        onAttachView()
    }

    override fun onPause() {
        super.onPause()
        onDetachView()
    }

    override fun onDetach() {
        super.onDetach()
        onDetachView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onAttachView()
        initView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LogFragment()
    }

    override fun connected() {
        job?.cancel()
        dialog?.dismiss()
    }

    override fun setAcc(data: AccData) {
        x.text = "X: ${data.x}"
        y.text = "Y: ${data.y}"
        z.text = "Z: ${data.z}"
    }

    override fun setTemp(data: TempData) {
        temp.text = data.temp.toString()
    }

    override fun setBrightImage(data: LdrData) {
        when (data.on) {
            true -> brightness?.setImageDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.not_bright
                )
            )
            false -> brightness?.setImageDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.bright
                )
            )
        }
    }

    override fun displayCancelButtonAfterTimeout(timeOut: Int) {
        var i = 0
        job = launch {
            while (isActive) {
                delay(1000)
                i = i.inc()
                if (dialog?.isVisible!!) {
                    if (i == timeOut) {
                        activity?.runOnUiThread({ dialog?.showButton() })
                        job?.cancel()
                    }
                } else {
                    job?.cancel()
                }
            }
        }
    }
}
