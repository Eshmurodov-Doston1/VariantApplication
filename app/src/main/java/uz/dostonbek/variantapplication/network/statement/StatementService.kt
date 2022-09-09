package uz.dostonbek.variantapplication.network.statement

import retrofit2.Response
import retrofit2.http.*
import uz.dostonbek.variantapplication.models.getApplication.reqApplication.SendToken
import uz.dostonbek.variantapplication.models.getApplications.Applications
import uz.dostonbek.variantapplication.models.messages.reqMessage.ReqMessage
import uz.dostonbek.variantapplication.models.messages.resMessage.ResMessage
import uz.dostonbek.variantapplication.models.sendMessage.resMessage.ResMessageUser
import uz.dostonbek.variantapplication.models.sendMessage.sendMessage.SendMessageUser
import uz.dostonbek.variantapplication.models.uploaCategory.UploadCategory
import uz.dostonbek.variantapplication.models.uploadPhotos.UploadPhotoData
import uz.dostonbek.variantapplication.socket.SendSocketData
import uz.dostonbek.variantapplication.socket.resSocet.ResSocket
import uz.dostonbek.variantapplication.utils.AppConstant.ACCEPT
import uz.dostonbek.variantapplication.utils.AppConstant.AUTH_STR
import uz.dostonbek.variantapplication.utils.AppConstant.HEADER_CONTENT
import uz.dostonbek.variantapplication.utils.AppConstant.TYPETOKEN

interface StatementService {
    @POST("/api/chat/get/applications")
    suspend fun getApplications(
        @Header(AUTH_STR) token: String,
        @Header(ACCEPT) accespt: String = TYPETOKEN,
        @Header(HEADER_CONTENT) type: String = TYPETOKEN
    ): Response<Applications>


    @POST("/api/chat/application")
    suspend fun getApplication(
        @Body sendToken: SendToken,
        @Header(AUTH_STR) token: String,
        @Header(ACCEPT) accespt: String = TYPETOKEN
    ):Response<uz.dostonbek.variantapplication.models.appliction.Application>

    @POST("api/broadcasting/auth")
    suspend fun authBroadCasting(
        @Body sendSocketData: SendSocketData,
        @Header(AUTH_STR) token: String,
        @Header(ACCEPT) accespt: String = TYPETOKEN
    ):Response<ResSocket>

    //AllMessage
    @POST("/api/chat/join")
    suspend fun getAllMessage(
        @Body reqMessage: ReqMessage,
        @Header(AUTH_STR) token: String,
        @Header(ACCEPT) accespt: String = TYPETOKEN
    ):Response<ResMessage>

    //sendMEssage
    @POST("/api/chat/send/message")
    suspend fun sendMessageChat(
        @Body sendMessageUser: SendMessageUser,
        @Header(AUTH_STR) token: String,
        @Header(ACCEPT) accespt: String = TYPETOKEN
    ):Response<ResMessageUser>

 //UploadImages
    @POST("/api/chat/get/photos")
    suspend fun getUploadPhotos(
     @Body sendToken: SendToken,
     @Header(AUTH_STR) token: String,
     @Header(ACCEPT) accespt: String = TYPETOKEN
    ):Response<UploadPhotoData>

 //UploadImages
    @GET("/api/chat/get/status/{id}")
    suspend fun getUploadFileCategory(
     @Path("id") id:Int,
     @Header(AUTH_STR) token: String,
     @Header(ACCEPT) accespt: String = TYPETOKEN
    ):Response<UploadCategory>



}