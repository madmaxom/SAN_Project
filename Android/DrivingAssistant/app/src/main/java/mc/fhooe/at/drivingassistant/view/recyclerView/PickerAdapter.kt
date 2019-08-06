package mc.fhooe.at.drivingassistant.view.recyclerView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mc.fhooe.at.drivingassistant.R


class PickerAdapter(
    private val context: Context,
    private var dataList: List<String>?,
    private val recyclerView: RecyclerView?
) : RecyclerView.Adapter<PickerAdapter.TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view: View
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.picker_item_layout, parent, false)
        return PickerAdapter.TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.pickerTxt.text = dataList!![position]
//        holder.pickerTxt.setOnClickListener({
//            recyclerView?.smoothScrollToPosition(position)
//        })
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    fun swapData(newData: List<String>) {
        dataList = newData
        notifyDataSetChanged()
    }

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pickerTxt: TextView = itemView.findViewById(R.id.picker_item)
    }
}