package com.app.bitcoinwallet.ui.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.app.bitcoinwallet.R
import com.app.bitcoinwallet.ui.model.StateUI
import com.app.bitcoinwallet.ui.model.TransactionGroupUI
import com.app.bitcoinwallet.ui.screen.menu.components.AddBalanceDialog
import com.app.bitcoinwallet.ui.screen.menu.components.CurrentBalanceSection
import com.app.bitcoinwallet.ui.screen.menu.components.TransactionsList

@Composable
fun MenuContent(
    balanceState: StateUI<Float>,
    transactionsState: StateUI<List<TransactionGroupUI>>,
    currentRateBitcoinState: StateUI<Int>,
    onAddTransactionButtonClick: ()->Unit,
    onAddCoinsToWallet: (Float)->Unit,
    onLoadNextPage: ()->Unit,
    isLoadingNextPage: Boolean
) {
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(dimensionResource(R.dimen.padding_normal))
    ) {

        if (showDialog) {
            AddBalanceDialog(
                onDismiss = { showDialog = false },
                onSave = { balance ->
                    onAddCoinsToWallet(balance)
                    showDialog = false
                }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CurrentBalanceSection(
                    balanceState = balanceState,
                    onAddButtonClick = {
                        showDialog = true
                    }
                )

                CurrentRangeBitcoin(currentRateBitcoinState)
            }

            TextButton(
                onClick = {
                    onAddTransactionButtonClick()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = stringResource(R.string.button_add_transaction),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.background
                )
            }

            TransactionsList(
                modifier = Modifier.fillMaxSize(),
                transactionsState = transactionsState,
                onLoadMore = {
                    onLoadNextPage()
                },
                isLoadingNextPage
            )
        }
    }
}

@Composable
fun CurrentRangeBitcoin(
    currentRateBitcoinState: StateUI<Int>
) {
    when (currentRateBitcoinState) {
        is StateUI.Loading -> {
            CircularProgressIndicator()
        }

        is StateUI.Success -> {
            Text(
                text = stringResource(R.string.label_range_bitcoin, currentRateBitcoinState.data.toString()) ,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        is StateUI.Error -> {
            Text(
                text = currentRateBitcoinState.message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
