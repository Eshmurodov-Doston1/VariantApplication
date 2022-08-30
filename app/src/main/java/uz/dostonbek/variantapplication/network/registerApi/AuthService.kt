package uz.dostonbek.variantapplication.network.registerApi

import android.service.autofill.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.dostonbek.variantapplication.models.auth.reqAuth.ReqAuth
import uz.dostonbek.variantapplication.models.auth.resAuth.ResAuth
import uz.dostonbek.variantapplication.models.logOut.LogOut
import uz.gxteam.variant.utils.AppConstant.ACCEPT
import uz.gxteam.variant.utils.AppConstant.AUTH_STR
import uz.gxteam.variant.utils.AppConstant.HEADER_CONTENT
import uz.gxteam.variant.utils.AppConstant.TYPETOKEN

interface AuthService {
    @POST("/api/login")
    suspend fun login(@Body reqLogin: ReqAuth): Response<ResAuth>

    @GET("/api/user/detail")
    suspend fun getUserData(
        @Header(AUTH_STR) token: String,
        @Header(ACCEPT) accespt: String = TYPETOKEN,
        @Header(HEADER_CONTENT) type: String = TYPETOKEN
    ):Response<uz.dostonbek.variantapplication.models.userData.UserData>
    @POST("/api/logout")
    suspend fun logOut(
        @Header(AUTH_STR) token: String,
        @Header(ACCEPT) accespt: String = TYPETOKEN,
        @Header(HEADER_CONTENT) type: String = TYPETOKEN
    ):Response<LogOut>
}