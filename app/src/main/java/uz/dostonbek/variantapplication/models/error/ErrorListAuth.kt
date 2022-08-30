package uz.dostonbek.variantapplication.models.error

import com.google.gson.annotations.SerializedName

data class ErrorListAuth(
    @SerializedName("errors")
    val errors:List<Error>
)
