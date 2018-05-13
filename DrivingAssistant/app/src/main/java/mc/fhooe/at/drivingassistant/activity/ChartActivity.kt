package mc.fhooe.at.drivingassistant.activity


import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_chart.*
import android.view.LayoutInflater
import android.widget.TextView
import mc.fhooe.at.drivingassistant.App
import mc.fhooe.at.drivingassistant.R
import mc.fhooe.at.drivingassistant.bluetooth.BluetoothService
import mc.fhooe.at.drivingassistant.mvp.presenter.ChartActivityPresenter
import mc.fhooe.at.drivingassistant.mvp.view.ChartActivityView
import mc.fhooe.at.drivingassistant.view.viewpager.PagerAdapter


class ChartActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener,
    ChartActivityView {

    private lateinit var device: BluetoothDevice
    private lateinit var presenter: ChartActivityPresenter
    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        device = intent?.extras?.getParcelable(App.INTENT_NAME_BLUETOOTH_DEVICE) as BluetoothDevice
        initView()
        presenter = ChartActivityPresenter(this)

        serviceIntent = Intent(this, BluetoothService::class.java)
        serviceIntent.putExtra(App.INTENT_NAME_BLUETOOTH_DEVICE, device)
        startService(serviceIntent)
        onAttachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun initView() {
        val inflater = LayoutInflater.from(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.customView = inflater.inflate(R.layout.actionbar_custom_layout, null)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.customView?.findViewById<TextView>(R.id.title)?.text = device.name
        supportActionBar?.elevation = 0F

        tab_layout.addTab(tab_layout.newTab().setText("Logs"))
        tab_layout.addTab(tab_layout.newTab().setText("Charts"))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = PagerAdapter(supportFragmentManager, tab_layout.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        pager.currentItem = tab.position
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        stopService(serviceIntent)
        super.onBackPressed()
    }

    override fun finishActivity() {
        stopService(serviceIntent)
        finish()
    }
}
