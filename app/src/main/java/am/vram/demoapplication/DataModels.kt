package am.vram.demoapplication

import DataPreview
import java.text.SimpleDateFormat
import java.util.*

@DataPreview
data class PaymentHistoryItem(
    val _id: String,
    val amount: Int,
    val currentBalance: Int,
    val date: Long,
    val fromUser: User,
    val id: String,
    val oldBalance: Int,
    val toUser: User,
    val status: String = ""
) {
    var dateString = ""
        private set

    fun generateDate() {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US)
            val netDate = Date(date)
            dateString = sdf.format(netDate)
        } catch (_: Exception) {
        }
    }
}

data class User(
    val _id: String,
    val fullName: String,
    val hasProducts: Boolean,
    val id: String,
    val location: Location,
    val name: String,
    val surname: String,
    val username: String
)

data class Location(
    val lat: Int,
    val long: Int,
)
