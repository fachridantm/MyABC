package com.outivox.myabc.ui.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.myabc.core.theme.colorPrimaryContainer

@Composable
fun AccountBalanceCard(balance: String, accountNumber: String) {
    Card(colors = CardDefaults.cardColors(containerColor = colorPrimaryContainer)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(text = "Current Balance", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = balance, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Checking Account $accountNumber", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Preview
@Composable
private fun AccountBalanceCardPreview() {
    AccountBalanceCard(
        balance = "$1,234.56",
        accountNumber = "**** 1234",
    )
}
