/*
 * Creator: Vram Voskanyan (vram.arm@gmail.com) on 21/01/2024, 21:21 Last modified: 21/01/2024, 21:01 Ⓒ 2024
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
fun HistoryCard(paymentHistoryItem: PaymentHistoryItem, onClick: () -> Unit) {
    Card(modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp).clickable(onClick = onClick)) {
        Column(Modifier.padding(8.dp)) {
            Row(Modifier.fillMaxWidth().padding(2.dp), Arrangement.SpaceBetween) {
                Text(text = "From: ${paymentHistoryItem.fromUser.name}")
                Text(text = "Status: ${paymentHistoryItem.status}")
            }
            Row(Modifier.fillMaxWidth().padding(4.dp), Arrangement.SpaceBetween) {
                Text(text = "to: ${paymentHistoryItem.toUser.name}")
                Text(text = "Amount: ${paymentHistoryItem.amount}")
            }
            Divider(thickness = 1.dp, color = Color.Gray)
            Row(Modifier.fillMaxWidth().padding(2.dp), Arrangement.SpaceBetween) {
                Text(text = "Date: ${paymentHistoryItem.dateString}")
                Text(text = "Balance: ${paymentHistoryItem.currentBalance}")
            }
        }
    }
}

@Preview
@Composable
fun HistoryCardPreview() {
    HistoryCard(paymentHistoryItem = createTestData()) {}
}

@Preview
@Composable
fun HistoryCardPreviewWithPrevGen() {
    HistoryCard(paymentHistoryItem = paymentHistoryItemPreview.also { it.generateDate() }) {}
}

fun createTestData() : PaymentHistoryItem {
    val fromUserA = User(
        _id = "user123",
        fullName = "John Doe",
        hasProducts = true,
        id = "user123",
        location = Location(lat = 40, long = -74),
        name = "John",
        surname = "Doe",
        username = "johndoe"
    )

    val toUserB = User(
        _id = "user456",
        fullName = "Jane Smith",
        hasProducts = false,
        id = "user456",
        location = Location(lat = 34, long = -118),
        name = "Jane",
        surname = "Smith",
        username = "janesmith"
    )

    return PaymentHistoryItem(
        _id = "abc123",
        amount = 100,
        currentBalance = 500,
        date = System.currentTimeMillis(),
        fromUser = fromUserA,
        id = "def456",
        oldBalance = 400,
        toUser = toUserB,
        status = "credit"
    ).apply { generateDate() }
}

