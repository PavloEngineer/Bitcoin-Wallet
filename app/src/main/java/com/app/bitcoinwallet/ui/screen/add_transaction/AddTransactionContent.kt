package com.app.bitcoinwallet.ui.screen.add_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.app.bitcoinwallet.R
import com.app.bitcoinwallet.domain.model.TransactionCategory
import com.app.bitcoinwallet.domain.model.TransactionData
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionContent(
    onBackButtonClick: () -> Unit,
    onSaveTransactionButtonClick: (TransactionData) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(TransactionCategory.OTHER) }
    val categories = TransactionCategory.entries.toTypedArray()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_new_transaction), color = MaterialTheme.colorScheme.primary) },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.button_cancel),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_small)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text(stringResource(R.string.label_amount), color = MaterialTheme.colorScheme.primary) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = { Text(stringResource(R.string.label_btc), color = MaterialTheme.colorScheme.primary) }
                )

                Text(stringResource(R.string.label_category), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories.size) { category ->
                        FilterChip(
                            selected = selectedCategory.value == categories[category].value,
                            onClick = { selectedCategory = categories[category] },
                            label = { Text(categories[category].value, color = MaterialTheme.colorScheme.primary) },
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                        )
                    }
                }

                TextButton(
                    onClick = {
                        onSaveTransactionButtonClick(
                            TransactionData(
                                date = LocalDateTime.now(),
                                amountCoins = amount.toFloatOrNull() ?: 0f,
                                isExpenses = true,
                                category = selectedCategory
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    Text(
                        text = stringResource(R.string.button_add_transaction),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }
    )
}