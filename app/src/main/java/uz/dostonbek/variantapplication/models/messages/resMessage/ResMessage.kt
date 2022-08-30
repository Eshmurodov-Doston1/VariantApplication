package uz.dostonbek.variantapplication.models.messages.resMessage

import uz.dostonbek.variantapplication.models.messages.resMessage.Message

data class ResMessage(
    val messages: List<Message>,
    val status: Int
)