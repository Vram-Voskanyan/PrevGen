package am.vram.demoapplication

import DataPreview
import java.text.SimpleDateFormat
import java.util.*

@DataPreview
data class PriceHistoryItem(
    val _id: String,
    val amount: Int,
    val currentBalance: Int,
    val date: Long,
    val fromUser: FromUser,
    val id: String,
    val oldBalance: Int,
    val toUser: ToUser,
    val type: String = "aaa"
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

data class FromUser(
    val _id: String,
    val fullName: String,
    val hasProducts: Boolean,
    val id: String,
    val location: Location,
    val name: String,
    val surname: String,
    val username: String
)

data class ToUser(
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
