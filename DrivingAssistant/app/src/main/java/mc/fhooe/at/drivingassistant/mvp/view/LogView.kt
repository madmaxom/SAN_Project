package mc.fhooe.at.drivingassistant.mvp.view

import mc.fhooe.at.drivingassistant.data.AccData
import mc.fhooe.at.drivingassistant.data.LdrData
import mc.fhooe.at.drivingassistant.data.TempData
import mc.fhooe.at.drivingassistant.mvp.base.BaseView

interface LogView : BaseView {
    
    fun displayCancelButtonAfterTimeout(timeOut: Int)
    fun connected()
    fun setAcc(data: AccData)
    fun setTemp(data: TempData)
    fun setBrightImage(data: LdrData)
}