package uz.dostonbek.variantapplication.vm.authViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.dostonbek.variantapplication.models.auth.reqAuth.ReqAuth
import uz.dostonbek.variantapplication.models.auth.resAuth.ResAuth
import uz.dostonbek.variantapplication.models.logOut.LogOut
import uz.dostonbek.variantapplication.models.userData.UserData
import uz.dostonbek.variantapplication.repository.authRepository.AuhtRepository
import uz.dostonbek.variantapplication.resourse.ResponseState
import uz.gxteam.variant.interceptor.MySharedPreference
import uz.gxteam.variant.utils.AppConstant.NO_INTERNET
import uz.gxteam.variant.utils.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuhtRepository,
    private val networkHelper: NetworkHelper,
    private val mySharedPreference: MySharedPreference
):ViewModel() {
    val mySharedPreferenceApp get() = mySharedPreference
    // TODO: auth Variant
    val authVariant:StateFlow<ResponseState<ResAuth?>> get() = _authVariant
    private var _authVariant = MutableStateFlow<ResponseState<ResAuth?>>(ResponseState.Loading)

    // TODO: User Data
    val userData:StateFlow<ResponseState<UserData?>> get() = _userData
    private var _userData = MutableStateFlow<ResponseState<UserData?>>(ResponseState.Loading)

    // TODO: LogOut
    val logOut:StateFlow<ResponseState<LogOut?>> get() = _logOut
    private var _logOut = MutableStateFlow<ResponseState<LogOut?>>(ResponseState.Loading)


    fun authApp(reqAuth: ReqAuth) = viewModelScope.launch {
        Log.e("ResponseData", "Loading.....")
            if (networkHelper.isNetworkConnected()){
                _authVariant.emit(ResponseState.Loading)
                Log.e("ResponseData", "Loading.....")
                try {
                    authRepository.authVariant(reqAuth).collect { response->
                        Log.e("ResponseData", response.toString())
                        _authVariant.emit(response)
                    }
                }catch (e:Exception){
                    _authVariant.emit(ResponseState.Error( e.hashCode(), e.message))
                }
            }else{
                _authVariant.emit(ResponseState.Error(NO_INTERNET))
            }
        }


    fun getSharedPreference():MySharedPreference{
        return mySharedPreference
    }

    fun getUserData() = viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                try {
                  authRepository.userData("${mySharedPreference.tokenType} ${mySharedPreference.accessToken}")
                        .collect{ response-> _userData.emit(response) }
                }catch (e:Exception){
                    _userData.emit(ResponseState.Error(e.hashCode(),e.message))
                }


            }else{
                _userData.emit(ResponseState.Error(NO_INTERNET))
            }
        }


    fun logOut() = viewModelScope.launch {
                if (networkHelper.isNetworkConnected()){
                    try {
                        authRepository.logOut("${mySharedPreference.tokenType} ${mySharedPreference.accessToken}")
                            .collect{response->
                                _logOut.emit(response)
                            }
                    }catch (e:Exception){
                        _logOut.emit(ResponseState.Error(e.hashCode(),e.message))
                    }
                }else{
                    _logOut.emit(ResponseState.Error(NO_INTERNET))
                }


        }
}