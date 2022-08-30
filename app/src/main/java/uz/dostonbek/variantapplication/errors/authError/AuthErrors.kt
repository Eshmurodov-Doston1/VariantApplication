package uz.dostonbek.variantapplication.errors.authError

import com.google.gson.annotations.SerializedName
import uz.gxteam.variant.utils.AppConstant.AUTHERRORFIELD

data class AuthErrors(
    @SerializedName(AUTHERRORFIELD)
    val errors: Any?,
)