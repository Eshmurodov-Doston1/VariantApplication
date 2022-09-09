package uz.dostonbek.variantapplication.errors.uploadError

import uz.gxteam.variant.errors.uploadError.Errors

data class ErrorUpload(
    val errors: Errors,
    val status: Int
)