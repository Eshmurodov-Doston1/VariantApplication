package uz.dostonbek.variantapplication.models.auth.resAuth

data class ResAuth(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)