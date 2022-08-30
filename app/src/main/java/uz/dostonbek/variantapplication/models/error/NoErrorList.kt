package uz.dostonbek.variantapplication.models.error

import com.google.gson.annotations.SerializedName

data class NoErrorList(
    @SerializedName("errors")
    var errors:Error
)

