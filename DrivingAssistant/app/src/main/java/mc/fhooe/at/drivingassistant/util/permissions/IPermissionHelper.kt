package mc.fhooe.at.drivingassistant.util.permissions

import android.content.Context
import mc.fhooe.at.drivingassistant.listener.PermissionResponseListener

interface IPermissionHelper {

        fun askBluetoothPermission(context: Context, permissionResponseListener: PermissionResponseListener)
        fun askStoragePermission(context: Context, permissionResponseListener: PermissionResponseListener)
}