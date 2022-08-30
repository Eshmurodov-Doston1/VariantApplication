package uz.dostonbek.variantapplication.models.getApplication.resApplication

import uz.dostonbek.variantapplication.models.getApplication.resApplication.Client
import uz.dostonbek.variantapplication.models.getApplication.resApplication.StatusName
import java.io.Serializable

data class OneApplication(
    val client: Client,
    val created_at: String,
    val id: Int,
    val status: Int,
    val status_name: StatusName,
    val token: String
):Serializable