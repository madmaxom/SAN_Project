package mc.fhooe.at.drivingassistant.view.recyclerView

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import mc.fhooe.at.drivingassistant.R
import mc.fhooe.at.drivingassistant.data.LineDataWrapper


class ChartViewAdapter(lineData: ArrayList<LineDataWrapper>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: ArrayList<LineDataWrapper> = lineData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        when (viewType) {
            0 -> return ChartViewAdapter.ViewHolder1(v)
            1 -> return ChartViewAdapter.ViewHolder2(v)
        }
        return ChartViewAdapter.ViewHolder1(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> initLayoutOne(holder as ViewHolder1, position)
            1 -> initLayoutTwo(holder as ViewHolder2, position)
            else -> {
            }
        }
    }

    private fun initLayoutTwo(viewHolder2: ViewHolder2, position: Int) {
        viewHolder2.header.text = items[position].header
        viewHolder2.subHeader.text = items[position].subheader
        viewHolder2.lineChart.data = items[position].lineData

        val lineChart = viewHolder2.lineChart
//        lineChart.setOnChartValueSelectedListener(this)
        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setDrawGridBackground(false)
        lineChart.setPinchZoom(true)
        lineChart.setBackgroundColor(Color.WHITE)

        val l = lineChart.legend
        l.form = Legend.LegendForm.LINE
        l.textColor = Color.BLACK

        val xl = lineChart.xAxis
        xl.isEnabled = false

        val leftAxis = lineChart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.axisMaximum = lineChart.data.yMax
        leftAxis.axisMinimum = lineChart.data.yMin
        leftAxis.setDrawGridLines(true)

        val rightAxis = lineChart.axisRight
        rightAxis.isEnabled = false

        lineChart.notifyDataSetChanged()
        if (lineChart.data.dataSetCount > 0) {
            lineChart.setVisibleXRangeMaximum(30F)
        }
        lineChart.moveViewToX(lineChart.data.entryCount.toFloat())
    }

    private fun initLayoutOne(viewHolder1: ViewHolder1, position: Int) {
        viewHolder1.header.text = items[position].header
        viewHolder1.subHeader.text = items[position].subheader
        viewHolder1.lineChart.data = items[position].lineData

        val lineChart = viewHolder1.lineChart
//        lineChart.setOnChartValueSelectedListener(this)
        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setDrawGridBackground(false)
        lineChart.setPinchZoom(true)
        lineChart.setBackgroundColor(Color.WHITE)

        val l = lineChart.legend
        l.form = Legend.LegendForm.LINE
        l.textColor = Color.BLACK

        val xl = lineChart.xAxis
        xl.isEnabled = false

        val leftAxis = lineChart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.axisMaximum = lineChart.data.yMax
        leftAxis.axisMinimum = lineChart.data.yMin
        leftAxis.setDrawGridLines(true)

        val rightAxis = lineChart.axisRight
        rightAxis.isEnabled = false

        lineChart.notifyDataSetChanged()
        if (lineChart.data.dataSetCount > 0) {
            lineChart.setVisibleXRangeMaximum(15F)
        }
        lineChart.moveViewToX(lineChart.data.entryCount.toFloat())
    }

    class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val header: TextView = itemView.findViewById(R.id.headingText)
        val subHeader: TextView = itemView.findViewById(R.id.subHeadingText)
        val lineChart: LineChart = itemView.findViewById(R.id.lineChart)
    }

    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val header: TextView = itemView.findViewById(R.id.headingText)
        val subHeader: TextView = itemView.findViewById(R.id.subHeadingText)
        val lineChart: LineChart = itemView.findViewById(R.id.lineChart)
    }

}