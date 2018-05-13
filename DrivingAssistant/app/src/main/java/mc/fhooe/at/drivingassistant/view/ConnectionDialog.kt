package mc.fhooe.at.drivingassistant.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import mc.fhooe.at.drivingassistant.App
import mc.fhooe.at.drivingassistant.R


class ConnectionDialog : DialogFragment(), View.OnClickListener {

    private lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.dialog_custom_layout, container, false)
        dialog.setTitle("Simple Dialog")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        button = rootView.findViewById(R.id.cancelButton)
        button.setOnClickListener(this)
        return rootView
    }

    override fun onClick(p0: View?) {
        val intent = Intent(App.ACTION_LEAVE_ACTIVITY)
        context?.let { LocalBroadcastManager.getInstance(it).sendBroadcast(intent) }
    }

    fun showButton() {
        button.visibility = View.VISIBLE
    }
}