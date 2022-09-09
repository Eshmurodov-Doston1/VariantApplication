package uz.dostonbek.variantapplication.ui.mainView.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.dostonbek.variantapplication.R
import uz.dostonbek.variantapplication.activitys.AuthActivity
import uz.dostonbek.variantapplication.databinding.FragmentSettingsBinding
import uz.dostonbek.variantapplication.databinding.LogOutBinding
import uz.dostonbek.variantapplication.ui.baseFragment.BaseFragment
import uz.dostonbek.variantapplication.utils.fetchResult
import uz.dostonbek.variantapplication.utils.startNewActivity
import uz.dostonbek.variantapplication.vm.authViewModel.AuthViewModel

@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private val authViewModel: AuthViewModel by viewModels()
    private val binding: FragmentSettingsBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            switchButton.isChecked = authViewModel.getSharedPreference().theme == true

            switchButton.setOnCheckedChangeListener{ view, isChecked ->
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    authViewModel.getSharedPreference().theme = true
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    authViewModel.getSharedPreference().theme = false
                }
            }

            logout.setOnClickListener {
                var dialog = AlertDialog.Builder(requireContext(), R.style.BottomSheetDialogThem)
                val create = dialog.create()
                var logOutBinding = LogOutBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                create.setView(logOutBinding.root)
                logOutBinding.noBtn.setOnClickListener {
                    create.dismiss()
                }

                logOutBinding.okBtn.setOnClickListener {
                    authViewModel.logOut()
                    launch {
                        authViewModel.logOut.fetchResult(compositionRoot.uiControllerApp,{ result->
                            authViewModel.getSharedPreference().clear()
                           activity?.startNewActivity(AuthActivity::class.java)
                            activity?.finish()
                            create.dismiss()
                        },{isClick ->  })
                    }
                }
                create.show()
            }
        }
    }
}