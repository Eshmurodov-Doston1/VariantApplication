package uz.dostonbek.variantapplication.interceptor

import android.content.Context
import android.util.Log
import com.google.common.net.HttpHeaders
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import org.json.JSONObject
import uz.dostonbek.variantapplication.BuildConfig
import uz.dostonbek.variantapplication.models.auth.resAuth.ResAuth
import uz.gxteam.variant.interceptor.MySharedPreference
import uz.dostonbek.variantapplication.utils.AppConstant.ACCEPT
import uz.dostonbek.variantapplication.utils.AppConstant.EMPTYTEXT
import uz.dostonbek.variantapplication.utils.AppConstant.REFRESHTOKENT_STR
import uz.dostonbek.variantapplication.utils.AppConstant.REFRESHTOKEN_ADD_URL
import uz.dostonbek.variantapplication.utils.AppConstant.SUCCESS_CODE
import uz.dostonbek.variantapplication.utils.AppConstant.TYPETOKEN
import java.net.HttpURLConnection
import javax.inject.Inject

/** TokentInternceptor this is class if responce code 401 refresh token send api
 *and update SharedPreference accessToken,refreshToken,tokenType and get apida data inn app **/

class TokenInterceptor @Inject constructor(
    private val mySharedPreference: MySharedPreference,
    @ApplicationContext private val context: Context) :Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val oldResponce = chain.proceed(oldRequest)
        val responseBody = oldResponce.body
        try {
            if (oldResponce.code== HttpURLConnection.HTTP_UNAUTHORIZED){
                var modifiedRequest: Request?
                val client = OkHttpClient()
                val params = JSONObject()
                params.put(REFRESHTOKENT_STR, mySharedPreference.refreshToken)
                val body: RequestBody = RequestBody.create(responseBody.contentType(),params.toString())
                val nRequest = Request.Builder()
                    .post(body)
                    .addHeader(ACCEPT,TYPETOKEN)
                    .url("${BuildConfig.BASE_URL}${REFRESHTOKEN_ADD_URL}")
                    .build()
                val response = client.newCall(nRequest).execute()
                if (response.code == SUCCESS_CODE) {
                    val jsonData = response.body.string()
                    val gson = Gson()
                    val resAuth: ResAuth = gson.fromJson(jsonData, ResAuth::class.java)
                    mySharedPreference.accessToken = resAuth.access_token
                    mySharedPreference.refreshToken = resAuth.refresh_token
                    mySharedPreference.tokenType = resAuth.token_type
                    Log.e("ResponseData", resAuth.toString())
                    modifiedRequest = oldRequest.newBuilder()
                        .header(HttpHeaders.AUTHORIZATION, "${mySharedPreference.tokenType} ${mySharedPreference.accessToken}")
                        .build()
                    oldResponce.close()
                    return chain.proceed(modifiedRequest)
                }else{
                    mySharedPreference.clear()
                    Log.e("Error", response.body.string())
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return oldResponce
    }
}


