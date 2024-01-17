package am.vram.demoapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HistoryCard(priceHistoryItem: PriceHistoryItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "From: ${priceHistoryItem.fromUser.name}"
                )
                Text(
                    text = "Status: ${priceHistoryItem.type}"
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "to: ${priceHistoryItem.toUser.name}"
                )
                Text(
                    text = "Amount: ${priceHistoryItem.amount}"
                )
            }
            Divider(thickness = 1.dp, color = Color.Gray)
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Date: ${priceHistoryItem.dateString}")
                Text(text = "Balance: ${priceHistoryItem.currentBalance}")
            }
        }

    }
}


@Preview
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    HistoryCard(priceHistoryItem = createTestData()) {}
}

@Preview
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview2() {
    HistoryCard(priceHistoryItem = priceHistoryItemPreview.also { it.generateDate() }) {}
}

fun createTestData() : PriceHistoryItem {
    val fromUserA = FromUser(
        _id = "user123",
        fullName = "John Doe",
        hasProducts = true,
        id = "user123",
        location = Location(lat = 40, long = -74),
        name = "John",
        surname = "Doe",
        username = "johndoe"
    )

    val toUserB = ToUser(
        _id = "user456",
        fullName = "Jane Smith",
        hasProducts = false,
        id = "user456",
        location = Location(lat = 34, long = -118),
        name = "Jane",
        surname = "Smith",
        username = "janesmith"
    )

    return PriceHistoryItem(
        _id = "abc123",
        amount = 100,
        currentBalance = 500,
        date = System.currentTimeMillis(),
        fromUser = fromUserA,
        id = "def456",
        oldBalance = 400,
        toUser = toUserB,
        type = "credit"
    ).apply { generateDate() }
}

