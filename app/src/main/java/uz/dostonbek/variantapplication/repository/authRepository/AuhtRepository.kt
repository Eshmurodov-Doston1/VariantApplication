package uz.dostonbek.variantapplication.repository.authRepository

import uz.dostonbek.variantapplication.models.auth.reqAuth.ReqAuth
import uz.dostonbek.variantapplication.network.registerApi.AuthService
import uz.dostonbek.variantapplication.utils.base.ResponseFetcher
import javax.inject.Inject

class AuhtRepository @Inject constructor(
    private val authService: AuthService,
    private val responseFetcher: ResponseFetcher.Base,
) {
    suspend fun authVariant(reqAuth: ReqAuth) = responseFetcher.getFlowResponseState(authService.login(reqAuth))

    suspend fun userData(token:String) = responseFetcher.getFlowResponseState(authService.getUserData(token))

    suspend fun logOut(token:String) = responseFetcher.getFlowResponseState(authService.logOut(token))
}