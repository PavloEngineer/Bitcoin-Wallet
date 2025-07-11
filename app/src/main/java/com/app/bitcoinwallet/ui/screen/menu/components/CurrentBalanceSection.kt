package com.app.bitcoinwallet.ui.screen.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.app.bitcoinwallet.R
import com.app.bitcoinwallet.ui.model.StateUI

@Composable
fun CurrentBalanceSection(balanceState: StateUI<Float>, onAddButtonClick: ()->Unit) {
    OutlinedCard(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_coins),
                contentDescription = stringResource(R.string.text_coins),
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
            )

            when (balanceState) {
                is StateUI.Loading -> {
                    CircularProgressIndicator()
                }

                is StateUI.Success -> {
                    Text(
                        text = balanceState.data.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                is StateUI.Error -> {
                    Text(
                        text = balanceState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            IconButton(
                onClick = {
                    onAddButtonClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.text_button_add),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}