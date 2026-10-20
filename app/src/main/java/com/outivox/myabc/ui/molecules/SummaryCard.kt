package com.outivox.myabc.ui.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.core.theme.colorOnPrimaryContainer
import com.outivox.core.theme.colorOnTertiaryContainer
import com.outivox.core.theme.colorPrimaryContainer

// TODO (Step 1.1): Apply State Hoisting to SummaryCard.
// Hoist internal state out of the Composable by accepting `isBalanceVisible: Boolean = true`
// and `onToggleVisibility: () -> Unit = {}`. This transforms SummaryCard into a pure Stateless Composable:
// 1. If `isBalanceVisible` is true, render the actual balance; otherwise display masked "••••••".
// 2. Add a clickable label or icon (e.g. "Hide" / "Show") in the header row that invokes `onToggleVisibility`.
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
            Text(text = "Total Balance", fontSize = 16.sp, color = colorOnPrimaryContainer)
            Text(
                text = if (isBalanceVisible) balance else "••••••",
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
