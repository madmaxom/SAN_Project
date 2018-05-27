package mc.fhooe.at.drivingassistant.activity.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_log.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import mc.fhooe.at.drivingassistant.R
import mc.fhooe.at.drivingassistant.data.AccData
import mc.fhooe.at.drivingassistant.data.LdrData
import mc.fhooe.at.drivingassistant.data.TempData
import mc.fhooe.at.drivingassistant.mvp.presenter.LogPresenter
import mc.fhooe.at.drivingassistant.mvp.view.LogView
import mc.fhooe.at.drivingassistant.util.PreferenceHelper
import mc.fhooe.at.drivingassistant.view.ConnectionDialog
import android.support.v4.content.res.ResourcesCompat
import android.graphics.drawable.Drawable
import android.support.design.R.attr.theme
import android.view.ContextThemeWrapper
import mc.fhooe.at.drivingassistant.data.DistanceData
import android.content.res.Resources.Theme
import android.graphics.Color
import com.devs.vectorchildfinder.VectorChildFinder
import mc.fhooe.at.drivingassistant.R.id.imageView
import com.devs.vectorchildfinder.VectorDrawableCompat.VFullPath






class LogFragment : Fragment(), LogView {

    val distance_3: Int = 30
    val distance_2: Int = 15
    val distance_1: Int = 5

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

        // Color Park Assistant image
        val wrapper = ContextThemeWrapper(context, R.style.Default)
        val drawable =
            ResourcesCompat.getDrawable(resources, R.drawable.park_assist_front, wrapper.theme)
        imageViewFront.setImageDrawable(drawable)
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

    override fun setParkAssistant(data: DistanceData) {
        val theme = resources.newTheme()
        val vector = VectorChildFinder(context, R.drawable.park_assist, imageView)

        when {
            data.distanceFront in (distance_2 + 1)..(distance_3 - 1) -> {
                theme.applyStyle(R.style.Distance_Far, false)
            }
            data.distanceFront in (distance_1 + 1)..(distance_2 - 1) -> {
                theme.applyStyle(R.style.Distance_Medium, false)
            }
            data.distanceFront in (0 + 1)..(distance_1 - 1) -> {
                theme.applyStyle(R.style.Distance_Close, false)
            }
            else -> {
                theme.applyStyle(R.style.Default, false)
            }
        }
        val drawable =
            ResourcesCompat.getDrawable(resources, R.drawable.park_assist_front, theme)
        imageViewFront.setImageDrawable(drawable)

        when {
            data.distanceBackM in (distance_2 + 1)..(distance_3 - 1) -> {
                val path1 = vector.findPathByName("Mid_Far")
                path1.fillColor = Color.GREEN
            }
            data.distanceBackM in (distance_1 + 1)..(distance_2 - 1) -> {
                val path1 = vector.findPathByName("Mid_Mid")
                path1.fillColor = Color.YELLOW
            }
            data.distanceBackM in (0 + 1)..(distance_1 - 1) -> {
                val path1 = vector.findPathByName("Mid_Close")
                path1.fillColor = Color.RED
            }
            else -> {
                var path1 = vector.findPathByName("Mid_Far")
                path1.fillColor = Color.BLACK
                path1 = vector.findPathByName("Mid_Mid")
                path1.fillColor = Color.BLACK
                path1 = vector.findPathByName("Mid_Close")
                path1.fillColor = Color.BLACK
            }
        }
        when {
            data.distanceBackL in (distance_2 + 1)..(distance_3 - 1) -> {
                val path1 = vector.findPathByName("Top_Far")
                path1.fillColor = Color.GREEN
            }
            data.distanceBackL in (distance_1 + 1)..(distance_2 - 1) -> {
                val path1 = vector.findPathByName("Top_Mid")
                path1.fillColor = Color.YELLOW
            }
            data.distanceBackL in (0 + 1)..(distance_1 - 1) -> {
                val path1 = vector.findPathByName("Top_Close")
                path1.fillColor = Color.RED
            }
            else -> {
                var path1 = vector.findPathByName("Top_Far")
                path1.fillColor = Color.BLACK
                path1 = vector.findPathByName("Top_Mid")
                path1.fillColor = Color.BLACK
                path1 = vector.findPathByName("Top_Close")
                path1.fillColor = Color.BLACK
            }
        }
        when {
            data.distanceBackR in (distance_2 + 1)..(distance_3 - 1) -> {
                val path1 = vector.findPathByName("Bot_Far")
                path1.fillColor = Color.GREEN
            }
            data.distanceBackR in (distance_1 + 1)..(distance_2 - 1) -> {
                val path1 = vector.findPathByName("Bot_Mid")
                path1.fillColor = Color.YELLOW
            }
            data.distanceBackR in (0 + 1)..(distance_1 - 1) -> {
                val path1 = vector.findPathByName("Bot_Close")
                path1.fillColor = Color.RED
            }
            else -> {
                var path1 = vector.findPathByName("Bot_Far")
                path1.fillColor = Color.BLACK
                path1 = vector.findPathByName("Bot_Mid")
                path1.fillColor = Color.BLACK
                path1 = vector.findPathByName("Bot_Close")
                path1.fillColor = Color.BLACK
            }
        }
        imageView.invalidate()
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
