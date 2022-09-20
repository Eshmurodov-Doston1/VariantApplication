package uz.dostonbek.variantapplication.activitys

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.github.javiersantos.appupdater.AppUpdaterUtils
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.github.javiersantos.appupdater.objects.Update
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import uz.dostonbek.variantapplication.ListenerActivity
import uz.dostonbek.variantapplication.R
import uz.dostonbek.variantapplication.databinding.ActivityAuthBinding
import uz.dostonbek.variantapplication.updater.DownloadController
import uz.dostonbek.variantapplication.updater.UpdateApp
import uz.dostonbek.variantapplication.utils.container.AppCompositionRoot
import uz.dostonbek.variantapplication.utils.uiController.UiController
import uz.dostonbek.variantapplication.vm.authViewModel.AuthViewModel
import kotlin.coroutines.CoroutineContext
@AndroidEntryPoint
class AuthActivity : AppCompatActivity(),UiController,CoroutineScope, ListenerActivity {
    private val binding:ActivityAuthBinding  by viewBinding()
    private val authViewModel: AuthViewModel by viewModels()
    private val updateUrl = "http://ideaservice.uz/update.json"
    lateinit var appCompositionRoot: AppCompositionRoot
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        updateApplicationVersion()
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_auth) as NavHostFragment
        appCompositionRoot = AppCompositionRoot(this,navHostFragment.navController,this,authViewModel.mySharedPreferenceApp)
        systemUI()
    }



    private fun updateApplicationVersion() {
        PermissionX.init(this)
            .permissions(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "Пожалуйста, разрешите, иначе мы не сможем вам помочь", "OK", "Cancel")
            }.request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    val downloadController = DownloadController(this)
                    downloadController.enqueueDownload(updateUrl)
                    //all permissions already granted or just granted
                    val appUpdaterUtils = AppUpdaterUtils(this)
                        .setUpdateJSON(updateUrl)
                        .setUpdateFrom(UpdateFrom.JSON)
                        .withListener(object: AppUpdaterUtils.UpdateListener{
                            override fun onSuccess(update: Update?, isUpdateAvailable: Boolean?) {
                                Log.e("Latest Version", update!!.latestVersion)
                                Log.e("Latest Version Code", update.latestVersionCode.toString())
                                Log.e("Release notes", update.releaseNotes)
                                Log.e("URL", update.urlToDownload.toString())
                                Log.e("Is update available?", isUpdateAvailable.toString())


                                if (isUpdateAvailable == true) {
                                    val alertDialog = AlertDialog.Builder(this@AuthActivity)
                                    alertDialog.setTitle("Новая версия! " + update.latestVersion)
                                    alertDialog.setMessage(update.releaseNotes)
                                    alertDialog.setPositiveButton("Продолжить") { dialog: DialogInterface?, which: Int ->
                                        val atualizaApp = UpdateApp()
                                        atualizaApp.setContext(this@AuthActivity)
                                        atualizaApp.execute(update.urlToDownload.toString())
                                    }
                                    alertDialog.setNegativeButton("Отменить") { dialog: DialogInterface, which: Int ->
                                        // splashPresenter.getCurrentAuth()
                                        dialog.cancel()
                                    }
                                    alertDialog.show()
                                } else {
                                    // splashPresenter.getCurrentAuth()
                                }
                            }

                            override fun onFailed(error: AppUpdaterError?) {
                                Toast.makeText(this@AuthActivity, R.string.no_internet, Toast.LENGTH_LONG).show()
                                Log.e("AppUpdater Error", "Something went wrong")
                            }

                        })
                    appUpdaterUtils.start()
                } else {
                    startActivity(
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",packageName, null))
                    )
                }
            }

    }





    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.fragment_auth).navigateUp()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main.immediate

    override fun showProgress() {
        appCompositionRoot.loadingView(true)
    }

    override fun hideProgress() {
        appCompositionRoot.loadingView(false)
    }

    override fun error(errorCode: Long, errorMessage: String, onClick: (isClick: Boolean) -> Unit) {
        appCompositionRoot.errorDialog(errorMessage, errorCode.toInt(), authViewModel.mySharedPreferenceApp,onClick)
    }

    private fun systemUI() {
        if (authViewModel.getSharedPreference().theme==true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
            window.statusBarColor = ContextCompat.getColor(this, R.color.statusbar_color)
            window.navigationBarColor =ContextCompat.getColor(this, R.color.statusbar_color)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor =ContextCompat.getColor(this, R.color.statusbar_color)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.statusbar_color)
        }
    }

    override fun uploadLoadingShow() {

    }

    override fun uploadLoadingHide() {

    }
}