package uz.dostonbek.variantapplication.ui.lockView

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.itsxtt.patternlock.PatternLockView
import com.mikhaellopez.biometric.BiometricHelper
import com.mikhaellopez.biometric.BiometricPromptInfo
import com.mikhaellopez.biometric.BiometricType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.dostonbek.variantapplication.R
import uz.dostonbek.variantapplication.activitys.MainActivity
import uz.dostonbek.variantapplication.databinding.FragmentLockBinding
import uz.dostonbek.variantapplication.ui.baseFragment.BaseFragment
import uz.dostonbek.variantapplication.utils.AppConstant.EIGHT
import uz.dostonbek.variantapplication.utils.AppConstant.FIVE
import uz.dostonbek.variantapplication.utils.AppConstant.FOURE
import uz.dostonbek.variantapplication.utils.AppConstant.MINUS_ONE
import uz.dostonbek.variantapplication.utils.AppConstant.MINUS_TWO
import uz.dostonbek.variantapplication.utils.AppConstant.NINE
import uz.dostonbek.variantapplication.utils.AppConstant.ONE
import uz.dostonbek.variantapplication.utils.AppConstant.SEVEN
import uz.dostonbek.variantapplication.utils.AppConstant.SIX
import uz.dostonbek.variantapplication.utils.AppConstant.THREE
import uz.dostonbek.variantapplication.utils.AppConstant.TWO
import uz.dostonbek.variantapplication.utils.AppConstant.ZERO
import uz.dostonbek.variantapplication.utils.fetchResult
import uz.dostonbek.variantapplication.utils.startNewActivity
import uz.dostonbek.variantapplication.vm.authViewModel.AuthViewModel

@AndroidEntryPoint
class LockFragment : BaseFragment(R.layout.fragment_lock) {
    private val authViewModel: AuthViewModel by viewModels()
    private val binding: FragmentLockBinding by viewBinding()
    lateinit var listNumber:ArrayList<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            authViewModel.getUserData()

            Log.e("AccessToken", compositionRootAuth.mySharedPreferencesApp.accessToken.toString())
            launch {
                authViewModel.userData.fetchResult(compositionRootAuth.uiControllerApp,{ result->
                    authViewModel.getSharedPreference().userData = Gson().toJson(result)
                },{isClick ->

                })
            }
            // TODO: Lock
            authViewModel.getSharedPreference().passwordApp.toString()

            indicatorPatternLockView.setOnPatternListener(object:PatternLockView.OnPatternListener{
                override fun onComplete(ids: java.util.ArrayList<Int>): Boolean {
                    if (compositionRootAuth.mySharedPreferencesApp.passwordApp.equals("") || compositionRootAuth.mySharedPreferencesApp.passwordApp==null){
                        compositionRootAuth.dialogPassword {
                            if (it){
                                compositionRootAuth.mySharedPreferencesApp.passwordApp = ids.toString()
                                createMain()
                            }
                        }
                    }else{
                        if(compositionRootAuth.mySharedPreferencesApp.passwordApp.equals(ids.toString())){
                            createMain()
                        }
                    }
                 return true
                }
            })

            biometrick()
        }
    }

    fun createMain(){
        compositionRootAuth.mActivity.startNewActivity(MainActivity::class.java)
        compositionRootAuth.mActivity.finish()
    }

    private fun biometrick() {
        if (!authViewModel.getSharedPreference().passwordApp.equals("")){
            val biometricHelper = BiometricHelper(this@LockFragment)
            val biometricType: BiometricType = biometricHelper.getBiometricType()
            var promptInfo = BiometricPromptInfo(getString(R.string.fingerPrint),getString(R.string.fingerPrint1), confirmationRequired = true)
            biometricHelper.showBiometricPrompt(promptInfo,
                onError = { errorCode: Int, errString: CharSequence ->
                    // Do something when error
                }, onFailed = {
                    // Do something when failed
                }, onSuccess = { result: BiometricPrompt.AuthenticationResult ->
                    // Do something when success
                  compositionRootAuth.mActivity.startNewActivity(MainActivity::class.java)
                    compositionRootAuth.mActivity.finish()
                })
        }
    }



}