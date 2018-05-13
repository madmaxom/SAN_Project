package mc.fhooe.at.drivingassistant

import android.os.Debug
import android.util.Log

class Logging {

    companion object {

        fun error(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.e(tag, message)
            }
        }

        fun weird(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.w(tag, message)
            }
        }

        fun info(tag: String, message: String, header: Boolean = false) {
            if (BuildConfig.DEBUG) {
                when {
                    header -> Log.i(tag, "***********$message***********")
                    else -> Log.i(tag, message)
                }
            }
        }

        fun debug(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, message)
            }
        }

        fun everything(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.v(tag, message)
            }
        }

        fun wtf(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.wtf(tag, message)
            }
        }
    }
}