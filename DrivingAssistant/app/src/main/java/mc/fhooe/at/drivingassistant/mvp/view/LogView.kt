package mc.fhooe.at.drivingassistant.mvp.view

import mc.fhooe.at.drivingassistant.mvp.base.BaseView

interface LogView : BaseView {
    
    fun displayCancelButtonAfterTimeout(timeOut: Int)
    fun connected()
}