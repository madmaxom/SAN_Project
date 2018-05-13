package mc.fhooe.at.drivingassistant.view.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import mc.fhooe.at.drivingassistant.activity.fragment.LogFragment
import mc.fhooe.at.drivingassistant.activity.fragment.ChartFragment

class PagerAdapter(fm: FragmentManager, numOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    private var count: Int = 0

    init {
        this.count = numOfTabs
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
                val tab1 = LogFragment.newInstance()
                tab1
            }
            1 -> {
                val tab2 = ChartFragment.newInstance()
                tab2
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return count
    }
}