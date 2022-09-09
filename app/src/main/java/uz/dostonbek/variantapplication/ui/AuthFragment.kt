package uz.dostonbek.variantapplication.ui

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.dostonbek.variantapplication.R
import uz.dostonbek.variantapplication.databinding.FragmentAuthBinding
import uz.dostonbek.variantapplication.models.auth.reqAuth.ReqAuth
import uz.dostonbek.variantapplication.ui.baseFragment.BaseFragment
import uz.dostonbek.variantapplication.utils.AppConstant.PHONE_UZB
import uz.dostonbek.variantapplication.utils.fetchResult
import uz.dostonbek.variantapplication.vm.authViewModel.AuthViewModel

@AndroidEntryPoint
class AuthFragment : BaseFragment(R.layout.fragment_auth) {
    private val binding: FragmentAuthBinding by viewBinding()
    private val authViewModel: AuthViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (!authViewModel.getSharedPreference().accessToken.equals("") && authViewModel.getSharedPreference().accessToken!=null){
              compositionRootAuth.screenNavigate.createLockView()
            }
            phoneNumber.requestFocus()

            phoneNumber.doAfterTextChanged {
                if (it.toString().trim().length==9){
                    password.requestFocus()
                }
            }


            login.setOnClickListener {
                val phoneNumber = "${PHONE_UZB}${phoneNumber.text.toString().trim()}"
                val password = password.text.toString().trim()
                if (phoneNumber.isEmpty()){
                    textInput.error = getString(R.string.no_phone)
                }else if (phoneNumber.length<9){
                    textInput.error = getString(R.string.error_phone)
                }else if (password.isEmpty()){
                    textInput1.error = getString(R.string.no_password)
                }else if (password.length<8){
                    textInput1.error = getString(R.string.password_length)
                } else if (phoneNumber.isNotEmpty() && password.isNotEmpty()){
                    authViewModel.authApp(ReqAuth(password,phoneNumber))
                        launch {
                            authViewModel.authVariant.fetchResult(compositionRootAuth.uiControllerApp, { result->
                                    compositionRootAuth.mySharedPreferencesApp.accessToken = result?.access_token
                                    compositionRootAuth.mySharedPreferencesApp.refreshToken = result?.refresh_token
                                    compositionRootAuth.mySharedPreferencesApp.tokenType = result?.token_type
                                    findNavController().navigate(R.id.lockFragment)
                                },{isClick ->  })
                            }
                    }
                }
            }
        }
}