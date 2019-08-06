package mc.fhooe.at.drivingassistant.util

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View


class Animator {

    companion object {

        fun animate(view : View){
            val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                PropertyValuesHolder.ofFloat("scaleY", 1.1f)
            )
            scaleDown.duration = 2000

            scaleDown.repeatCount = ObjectAnimator.INFINITE
            scaleDown.repeatMode = ObjectAnimator.REVERSE
            scaleDown.interpolator = FastOutSlowInInterpolator()

            scaleDown.start()
        }
    }

}