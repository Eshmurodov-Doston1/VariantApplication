package uz.dostonbek.variantapplication.service

import android.app.*
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import uz.dostonbek.variantapplication.MainActivity
import uz.dostonbek.variantapplication.R
import uz.dostonbek.variantapplication.repository.stateMent.StateMentRepository
import uz.dostonbek.variantapplication.resourse.ResponseState
import uz.dostonbek.variantapplication.socket.SendSocketData
import uz.dostonbek.variantapplication.socket.connectSocket.ConnectSocket
import uz.dostonbek.variantapplication.socket.dataSocket.DataSocket
import uz.dostonbek.variantapplication.socket.socketMessage.SocketMessage
import uz.gxteam.variant.interceptor.MySharedPreference
import uz.gxteam.variant.utils.AppConstant
import uz.gxteam.variant.utils.AppConstant.CHANNEL_ID
import uz.gxteam.variant.utils.AppConstant.FOREGROUND_CODE
import uz.gxteam.variant.utils.AppConstant.ONE
import uz.gxteam.variant.utils.AppConstant.ZERO
import javax.inject.Inject

@AndroidEntryPoint
class MyForegroundService:Service() {
    @Inject
    lateinit var mySharedPreference: MySharedPreference
    @Inject
    lateinit var stateMentRepository: StateMentRepository

    lateinit var webSocketApp: WebSocket

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var channelId= "Applicaiton"
        var notificationChannel = NotificationChannel(
            channelId,
            channelId,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(notificationChannel)
        val notification = Notification.Builder(this, channelId)

        startForeground(1,notification.build())
        stopForeground(1)
        socketData()
        return super.onStartCommand(intent, flags, startId)
    }

    fun socketData(){
        if (!mySharedPreference.accessToken.equals(AppConstant.EMPTYTEXT)){
            var count=ZERO
            var gson = Gson()
            var client = OkHttpClient()
            try {
                val request: Request = Request.Builder().url(AppConstant.WEBSOCKET_URL).build()
                var listener = object: WebSocketListener(){
                    override fun onOpen(webSocket: WebSocket, response: Response) {
                        super.onOpen(webSocket, response)
                    }

                    override fun onMessage(webSocket: WebSocket, text: String) {
                        super.onMessage(webSocket, text)
                        val socketData = gson.fromJson(text, ConnectSocket::class.java)
                        if (count==ZERO){
                            val dataSocket = gson.fromJson(socketData.data, DataSocket::class.java)
                            GlobalScope.launch(Dispatchers.IO){
                                stateMentRepository.broadCastingAuth(SendSocketData("${AppConstant.CHAT_MEW_MESSAGE}.${mySharedPreference.oldToken}", dataSocket.socket_id),"${mySharedPreference.tokenType} ${mySharedPreference.accessToken}")
                                    .collect{ response->
                                        when(response){
                                            is ResponseState.Success->{
                                                webSocket.send(" {\"${AppConstant.WST_EVENT}\":\"${AppConstant.PUSHER_WST}:${AppConstant.SUBSCRIBE_WST}\",\"${AppConstant.WST_DATA}\":{\"${AppConstant.AUTH_WST}\":\"${response.data?.auth}\",\"${AppConstant.WST_CHANNEL}\":\"${AppConstant.CHAT_MEW_MESSAGE}.${mySharedPreference.oldToken}\"}}")
                                                count++
                                            }
                                            else -> {}
                                        }
                                    }
                            }
                        } else{
                            if (socketData.data!=null){
                                val messageSocket = gson.fromJson(socketData.data, SocketMessage::class.java)
                                GlobalScope.launch(Dispatchers.Main) {
                                    if (messageSocket.type== ONE){
                                        val alarmSound: Uri = RingtoneManager.getDefaultUri(
                                            RingtoneManager.TYPE_NOTIFICATION)
                                        var resultIntent =  Intent(applicationContext, MainActivity::class.java)
                                        var resultPendingIntent = PendingIntent.getActivity(applicationContext, ZERO, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                                        var notificationCompat =   NotificationCompat.Builder(applicationContext,CHANNEL_ID)
                                            .setSmallIcon(R.drawable.ic_baseline_message_24)
                                            .setContentTitle(applicationContext.getText(R.string.app_name))
                                            .setContentText(messageSocket.message)
                                            .setSound(alarmSound)
                                            .setContentIntent(resultPendingIntent)
                                            .setAutoCancel(true)
                                            .build()

                                        val notificationManager = applicationContext.getSystemService(
                                            NOTIFICATION_SERVICE
                                        ) as NotificationManager
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            val descriptionText = AppConstant.COMPANYNAME
                                            val importance = NotificationManager.IMPORTANCE_HIGH
                                            val channel = NotificationChannel(CHANNEL_ID,
                                                AppConstant.COMPANYNAME, importance).apply { description =
                                                descriptionText
                                            }
                                            notificationManager.createNotificationChannel(channel)
                                        }
                                        startForeground(FOREGROUND_CODE,notificationCompat)
                                        notificationManager.notify(FOREGROUND_CODE,notificationCompat)
                                    }
                                }
                            }
                            count++
                        }
                    }

                    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                        super.onMessage(webSocket, bytes)
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        super.onClosed(webSocket, code, reason)
                        webSocket.close(code,reason)
                    }

                    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                        super.onFailure(webSocket, t, response)
                    }
                }
                this.webSocketApp = client.newWebSocket(request, listener)
                client.dispatcher.executorService.shutdown()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}