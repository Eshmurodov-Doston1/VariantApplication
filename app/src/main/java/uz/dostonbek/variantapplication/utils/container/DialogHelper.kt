package uz.dostonbek.variantapplication.utils.container

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import uz.dostonbek.variantapplication.R
import uz.dostonbek.variantapplication.databinding.ErrorDialogBinding
import uz.gxteam.variant.utils.AppConstant.CLIENT_CODE_END
import uz.gxteam.variant.utils.AppConstant.CLIENT_CODE_START
import uz.gxteam.variant.utils.AppConstant.NO_INTERNET
import uz.gxteam.variant.utils.AppConstant.SERVER_CODE_END
import uz.gxteam.variant.utils.AppConstant.SERVER_CODE_START
import uz.gxteam.variant.utils.AppConstant.UN_AUTHORIZATION
import uz.gxteam.variant.utils.AppConstant.ZERO
import uz.dostonbek.variantapplication.utils.gone
import uz.dostonbek.variantapplication.utils.textApp
import uz.gxteam.variant.interceptor.MySharedPreference

class DialogHelper(
    private val context:Context,
    private val inflater: LayoutInflater,
    private val activity:AppCompatActivity,
    private val navController: NavController
) {


    fun errorDialog(
        listException: List<String>,
        code:Int,
        mySharedPreferences: MySharedPreference?,
        click:(click:Boolean)->Unit
    ){
        var dialog = AlertDialog.Builder(context, R.style.BottomSheetDialogThem)
        val create = dialog.create()
        val errorDialog = ErrorDialogBinding.inflate(inflater)
        create.setView(errorDialog.root)
        when(code){
            -1->{
                var errorMessage = ""
                listException.onEach {
                    errorMessage+="$it\n"
                }
                errorDialog.title.text = errorMessage
                errorDialog.okBtn.gone()
                errorDialog.closeBtn.setPadding(ZERO,ZERO,ZERO,ZERO)
                errorDialog.closeBtn.text = context.getString(R.string.again)

                errorDialog.closeBtn.setOnClickListener {
                    click.invoke(true)
                    create.dismiss()
                }
            }
            NO_INTERNET->{
                errorDialog.lottie.setAnimation(R.raw.no_intenet)
                errorDialog.title.textApp(context.getString(R.string.no_internet_connection))
                errorDialog.okBtn.gone()
                errorDialog.closeBtn.setPadding(ZERO,ZERO,ZERO,ZERO)
                errorDialog.closeBtn.text = context.getString(R.string.again)
                errorDialog.closeBtn.setOnClickListener {
                    click.invoke(true)
                    create.dismiss()
                }
            }
            in CLIENT_CODE_START..CLIENT_CODE_END->{
               if (code!= UN_AUTHORIZATION){
                   var errorMessage = ""
                   listException.onEach {
                       errorMessage+="$it\n"
                   }
                   errorDialog.title.textApp(errorMessage)
                   errorDialog.okBtn.gone()
                   errorDialog.closeBtn.setPadding(ZERO,ZERO,ZERO,ZERO)
                   errorDialog.closeBtn.text = context.getString(R.string.again)
                   errorDialog.closeBtn.setOnClickListener {
                       click.invoke(true)
                       create.dismiss()
                   }
               }else{
                  navController.navigate(R.id.authFragment)
                   mySharedPreferences?.clear()
               }
            }
            UN_AUTHORIZATION->{
                navController.navigate(R.id.authFragment)
                mySharedPreferences?.clear()
            }
            in SERVER_CODE_START..SERVER_CODE_END->{
                errorDialog.title.textApp(context.getString(R.string.server_error))
                errorDialog.okBtn.gone()
                errorDialog.closeBtn.setPadding(ZERO,ZERO,ZERO,ZERO)
                errorDialog.closeBtn.text = context.getString(R.string.again)
                errorDialog.closeBtn.setOnClickListener {
                    click.invoke(true)
                    create.dismiss()
                }
            }
            1001->{
                errorDialog.title.textApp(listException[0])
                errorDialog.closeBtn.setOnClickListener {
                    click.invoke(false)
                    create.dismiss()
                }
                errorDialog.okBtn.setOnClickListener {
                    click.invoke(true)
                    create.dismiss()
                }
            }
            else->{
                errorDialog.closeBtn.setOnClickListener {
                    create.dismiss()
                }
                errorDialog.okBtn.setOnClickListener {
                    create.dismiss()
                }
            }
        }

        create.show()
    }













}