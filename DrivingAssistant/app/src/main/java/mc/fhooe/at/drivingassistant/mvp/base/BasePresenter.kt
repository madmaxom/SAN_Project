package mc.fhooe.at.drivingassistant.mvp.base

interface BasePresenter<in T : BaseView> {

    fun onAttach(view: T)
    fun onDetach()

}