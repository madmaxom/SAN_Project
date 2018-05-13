package mc.fhooe.at.drivingassistant.activity.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_chart.*
import mc.fhooe.at.drivingassistant.R
import mc.fhooe.at.drivingassistant.activity.ChartActivity
import mc.fhooe.at.drivingassistant.data.LineDataWrapper
import mc.fhooe.at.drivingassistant.mvp.presenter.ChartPresenter
import mc.fhooe.at.drivingassistant.mvp.view.ChartView
import mc.fhooe.at.drivingassistant.view.recyclerView.ChartViewAdapter

class ChartFragment : Fragment(), ChartView, OnChartValueSelectedListener {

    private var presenter: ChartPresenter? = null
    private lateinit var chartViewAdapter: ChartViewAdapter
    private var chartDataList: ArrayList<LineDataWrapper> = ArrayList()

    //region View
    override fun onAttachView() {
        presenter?.onAttach(this)
    }

    override fun onDetachView() {
        presenter?.onDetach()
    }

    override fun initView() {
        chartViewAdapter = ChartViewAdapter(chartDataList)
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = chartViewAdapter
        if (chartDataList.size == 0) {
            chartDataList.add(LineDataWrapper(LineData(), "Acceleration", "Raw Data", 0))
            chartDataList.add(LineDataWrapper(LineData(), "Gyro", "Raw Data", 1))
            chartDataList.add(LineDataWrapper(LineData(), "Magnetometer", "Raw Data", 2))
            chartDataList.add(LineDataWrapper(LineData(), "Angle", "Angle to Surface", 3))
            chartDataList.add(LineDataWrapper(LineData(), "Force", "Raw pressure data", 4))
        }
        presenter?.chartDataWrapper = chartDataList
    }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        presenter = activity?.let { ChartPresenter(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onAttachView()
        initView()
    }

    override fun onDetach() {
        super.onDetach()
        onDetachView()
    }

    override fun onResume() {
        super.onResume()
        onAttachView()
    }

    override fun onPause() {
        super.onPause()
        onDetachView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChartFragment()
    }

    //region CHART

    override fun updateMagEntries(list: ArrayList<Entry>) {
        val data: LineData? = chartDataList[2].lineData
        for ((a, set: Entry) in list.withIndex()) {
            data?.addEntry(set, a)
        }
        data?.notifyDataChanged()
        chartViewAdapter.notifyDataSetChanged()
    }

    override fun updateAccelerationEntries(list: ArrayList<Entry>) {
        val data: LineData? = chartDataList[0].lineData
        for ((a, set: Entry) in list.withIndex()) {
            data?.addEntry(set, a)
        }
        data?.notifyDataChanged()
        chartViewAdapter.notifyDataSetChanged()
    }

    override fun updateGyroEntries(list: ArrayList<Entry>) {
        val data: LineData? = chartDataList[1].lineData
        for ((a, set: Entry) in list.withIndex()) {
            data?.addEntry(set, a)
        }
        data?.notifyDataChanged()
        chartViewAdapter.notifyDataSetChanged()
    }

    override fun updatePressureEntry(list: ArrayList<Entry>) {
        val data: LineData? = chartDataList[4].lineData
        for ((a, set: Entry) in list.withIndex()) {
            data?.addEntry(set, a)
        }
        data?.notifyDataChanged()
        chartViewAdapter.notifyDataSetChanged()
    }

    override fun updateAngleEntry(list: ArrayList<Entry>) {
        val data: LineData? = chartDataList[3].lineData
        for ((a, set: Entry) in list.withIndex()) {
            data?.addEntry(set, a)
        }
        data?.notifyDataChanged()
        chartViewAdapter.notifyDataSetChanged()
    }

    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

}
