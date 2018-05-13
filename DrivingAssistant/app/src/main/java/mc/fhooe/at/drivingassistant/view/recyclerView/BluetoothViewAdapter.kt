package mc.fhooe.at.drivingassistant.view.recyclerView

import android.bluetooth.BluetoothDevice
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import mc.fhooe.at.drivingassistant.R
import mc.fhooe.at.drivingassistant.listener.OnItemClickListener

class BluetoothViewAdapter(
    private val bluetoothDevices: ArrayList<BluetoothDevice>, listen: OnItemClickListener
) : RecyclerView.Adapter<BluetoothViewAdapter.ViewHolder>() {

    private val listener: OnItemClickListener = listen

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(
            v
        )
    }

    override fun getItemCount(): Int {
        return bluetoothDevices.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (bluetoothDevices[position].name == null) {
            if (bluetoothDevices[position].address != null) {
                holder.deviceName.text = bluetoothDevices[position].address
                holder.bind(bluetoothDevices[position], listener)
            }
        } else {
            holder.deviceName.text = bluetoothDevices[position].name
            holder.bind(bluetoothDevices[position], listener)
        }
    }

    fun clear() {
        bluetoothDevices.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bluetoothDevice: BluetoothDevice, listener: OnItemClickListener) {
            itemView.setOnClickListener { listener.onItemClick(bluetoothDevice) }
        }

        val deviceName: TextView = itemView.findViewById(R.id.bluetoothDevice)
        val imageView: ImageView = itemView.findViewById(R.id.bluetoothImage)
    }

}