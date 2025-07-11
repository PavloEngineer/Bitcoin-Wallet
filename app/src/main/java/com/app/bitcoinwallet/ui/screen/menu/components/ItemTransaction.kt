package com.app.bitcoinwallet.ui.screen.menu.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.app.bitcoinwallet.R
import com.app.bitcoinwallet.domain.model.TransactionData
import com.app.bitcoinwallet.domain.utils.DatePatternConstants.DAY_MONTH_YEAR_HOUR_MINUTE
import com.app.bitcoinwallet.ui.theme.GREEN
import com.app.bitcoinwallet.ui.theme.RED
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ItemTransaction(
    transactionData: TransactionData
) {
    val colorBox = if (transactionData.isExpenses) RED else GREEN
    val timeTransaction =
        transactionData.date.format(DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_HOUR_MINUTE))

    OutlinedCard(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_small))
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.height_item_transaction))
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = timeTransaction,
                style = MaterialTheme.typography.bodyLarge,
                color = colorBox,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(dimensionResource(R.dimen.width_line))
                        .background(colorBox)
                )

                Text(
                    text = transactionData.category?.value ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = transactionData.amountCoins.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}