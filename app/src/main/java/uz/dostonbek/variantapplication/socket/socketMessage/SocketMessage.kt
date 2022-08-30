package uz.dostonbek.variantapplication.socket.socketMessage

data class SocketMessage(
    val app_id: Int,
    val message: String,
    val token: String,
    val type: Int
)