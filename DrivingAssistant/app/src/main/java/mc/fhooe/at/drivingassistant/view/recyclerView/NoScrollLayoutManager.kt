package mc.fhooe.at.drivingassistant.view.recyclerView

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class NoScrollLayoutManager(context: Context?) : LinearLayoutManager(context) {

    constructor(context: Context, @RecyclerView.Orientation orientation: Int,
                reverseLayout: Boolean, scrollingEnabled : Boolean) : this(context) {
        setOrientation(orientation)
        setReverseLayout(reverseLayout)
        setScrollEnabled(scrollingEnabled)
    }

    private var isScrollEnabled: Boolean = true

    private fun setScrollEnabled(flag: Boolean) {
        isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }
}