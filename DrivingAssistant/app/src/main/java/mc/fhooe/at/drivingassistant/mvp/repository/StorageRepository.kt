package mc.fhooe.at.drivingassistant.mvp.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import com.opencsv.CSVWriter
import mc.fhooe.at.drivingassistant.Logging
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class StorageRepository(var context: Context, username: String) {

    private var logFolder =
        File(
            Environment.getExternalStorageDirectory().toString() + "/drivingassistant/",
            "Log/"
        )
    private var csvWriter: CSVWriter? = null

    @SuppressLint("SimpleDateFormat")
    fun storeToFile(
        data: List<Array<String>>,
        character: String,
        user: String
    ): Boolean {
        createFolder(user, character)
        var file: File?
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val currentDateAndTime = sdf.format(Date())
        try {
            file = File(logFolder, currentDateAndTime)
            val mFileWriter = FileWriter(file, true)
            csvWriter = CSVWriter(mFileWriter)
            csvWriter?.writeAll(data)
            csvWriter?.close()
        } catch (e: IOException) {
            Logging.error(this.javaClass.simpleName, e.message.toString())
            return false
        }
        return true
    }

    private fun createFolder(name: String, character: String): Boolean {
        if (logFolder.exists() && logFolder.isDirectory) {
            return true
        }
        logFolder = File(logFolder.absolutePath + File.separator + name, character)
        logFolder.mkdirs()
        return false
    }

}