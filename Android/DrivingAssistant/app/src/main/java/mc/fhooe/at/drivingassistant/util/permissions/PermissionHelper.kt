package mc.fhooe.at.drivingassistant.util.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import mc.fhooe.at.drivingassistant.listener.PermissionResponseListener


class PermissionHelper {

    companion object : IPermissionHelper {
        override fun askStoragePermission(context: Context,permissionResponseListener: PermissionResponseListener) {
            when (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PackageManager.PERMISSION_DENIED ->
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(context as Activity,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 7)
                    }
                PackageManager.PERMISSION_GRANTED -> {
                    permissionResponseListener.permissionGranted(true)
                    return
                }
            }
            permissionResponseListener.permissionGranted(false)
        }

        override fun askBluetoothPermission(context: Context, permissionResponseListener: PermissionResponseListener) {
            when (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                PackageManager.PERMISSION_DENIED ->
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(context as Activity,
                                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 7)
                    }
                PackageManager.PERMISSION_GRANTED -> {
                    permissionResponseListener.permissionGranted(true)
                    return
                }
            }
            permissionResponseListener.permissionGranted(false)
        }
    }
}
