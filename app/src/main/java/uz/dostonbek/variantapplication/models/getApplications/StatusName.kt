package uz.dostonbek.variantapplication.models.getApplications

import java.io.Serializable

data class StatusName(
    val id: Int,
    val status: String,
    val title: String
):Serializable