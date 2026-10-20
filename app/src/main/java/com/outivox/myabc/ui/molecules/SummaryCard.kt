package com.outivox.myabc.ui.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.core.theme.colorOnPrimaryContainer
import com.outivox.core.theme.colorOnTertiaryContainer
import com.outivox.core.theme.colorPrimaryContainer

@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    balance: String,
    accountNumber: String,
    isBalanceVisible: Boolean = true,
    onToggleVisibility: () -> Unit = {},
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = colorPrimaryContainer),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Total Balance", fontSize = 16.sp, color = colorOnPrimaryContainer)
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onToggleVisibility)
                        .background(colorOnPrimaryContainer.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    text = if (isBalanceVisible) "Hide" else "Show",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorOnPrimaryContainer,
                )
            }
            Text(
                text = if (isBalanceVisible) balance else "••••••••",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorOnPrimaryContainer,
            )
            Column {
                Text(text = "Checking Account", fontSize = 16.sp, color = colorOnPrimaryContainer)
                Text(text = accountNumber, fontSize = 14.sp, color = colorOnTertiaryContainer)
            }
        }
    }
}

@Preview
@Composable
private fun SummaryCardPreview() {
    SummaryCard(
        balance = "$12,345.67",
        accountNumber = "...1234",
    )
}
