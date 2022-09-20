package uz.dostonbek.variantapplication.updater

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class UpdateApp : AsyncTask<String?, Int?, String?>() {
        private var mPDialog: ProgressDialog? = null
        private var mContext: Context? = null
        fun setContext(context: Activity) {
            mContext = context
            context.runOnUiThread {
                mPDialog = ProgressDialog(mContext)
                mPDialog!!.setMessage("Подождите пожалуйста... Скачаем вам удобства")
                mPDialog!!.isIndeterminate = true
                mPDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                mPDialog!!.setCancelable(false)
                mPDialog!!.show()
            }
        }

        override fun doInBackground(vararg arg0: String?): String? {
            try {
                val url = URL(arg0[0])
                val c = url.openConnection() as HttpURLConnection
                c.requestMethod = "GET"
                c.doOutput = true
                c.connect()
                val lenghtOfFile = c.contentLength
                val PATH = Objects.requireNonNull(mContext!!.getExternalFilesDir(null))?.absolutePath
                val file = File(PATH)
                val isCreate = file.mkdirs()
                val outputFile = File(file, "my_apk.apk")
                if (outputFile.exists()) {
                    val isDelete = outputFile.delete()
                }
                val fos = FileOutputStream(outputFile)
                val `is` = c.inputStream
                val buffer = ByteArray(1024)
                var len1: Int
                var total: Long = 0
                while (`is`.read(buffer).also { len1 = it } != -1) {
                    total += len1.toLong()
                    fos.write(buffer, 0, len1)
                    publishProgress((total * 100 / lenghtOfFile).toInt())
                }
                fos.close()
                `is`.close()
                if (mPDialog != null) mPDialog!!.dismiss()
                installApk()
            } catch (e: Exception) {
                Toast.makeText(
                    mContext,
                    "Ошибка при скачивание файла перенаправим на приложении",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("UpdateAPP", "Update error! " + e.message)
            }
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            if (mPDialog != null) mPDialog!!.show()
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            if (mPDialog != null) {
                mPDialog!!.isIndeterminate = false
                mPDialog!!.max = 100
                mPDialog!!.progress = values[0]?:0
            }
        }

        override fun onPostExecute(result: String?) {
            if (mPDialog != null) mPDialog!!.dismiss()
            if (result != null) Toast.makeText(
                mContext,
                "Download error: $result",
                Toast.LENGTH_LONG
            ).show() else Toast.makeText(mContext, "File Downloaded", Toast.LENGTH_SHORT).show()
        }

        private fun installApk() {
            try {
                val PATH = Objects.requireNonNull(mContext!!.getExternalFilesDir(null))?.absolutePath
                val file = File("$PATH/my_apk.apk")
                val intent = Intent(Intent.ACTION_VIEW)
                if (Build.VERSION.SDK_INT >= 24) {
                    val downloaded_apk = FileProvider.getUriForFile(
                        mContext!!,
                        mContext!!.applicationContext.packageName + ".provider",
                        file
                    )
                    intent.setDataAndType(downloaded_apk, "application/vnd.android.package-archive")
                    val resInfoList = mContext!!.packageManager.queryIntentActivities(
                        intent,
                        PackageManager.MATCH_DEFAULT_ONLY
                    )
                    for (resolveInfo in resInfoList) {
                        mContext!!.grantUriPermission(
                            mContext!!.applicationContext.packageName + ".provider",
                            downloaded_apk,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    }
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                    mContext!!.startActivity(intent)
                } else {
                    intent.action = Intent.ACTION_VIEW
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                    intent.setDataAndType(
                        Uri.fromFile(file),
                        "application/vnd.android.package-archive"
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                mContext!!.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }