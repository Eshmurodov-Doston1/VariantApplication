package uz.dostonbek.variantapplication

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import net.gotev.uploadservice.BuildConfig
import net.gotev.uploadservice.UploadServiceConfig
import uz.dostonbek.variantapplication.utils.AppConstant.TESTCHANNEL
import javax.inject.Inject

@HiltAndroidApp
class App:Application(),Configuration.Provider{
    companion object{
        const val notificationChannelID = "TestChannel"
    }
    @Inject
    lateinit var workerFactory:HiltWorkerFactory


    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        UploadServiceConfig.initialize(
            context = this,
            defaultNotificationChannel = notificationChannelID,
            debug = BuildConfig.DEBUG
        )
    }



    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= 26) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(notificationChannelID, TESTCHANNEL, NotificationManager.IMPORTANCE_LOW)
            manager.createNotificationChannel(channel)
        }
    }


    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

}