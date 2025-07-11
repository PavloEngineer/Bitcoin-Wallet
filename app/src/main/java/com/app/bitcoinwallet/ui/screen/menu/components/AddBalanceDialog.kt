package com.app.bitcoinwallet.ui.screen.menu.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.app.bitcoinwallet.R

@Composable
fun AddBalanceDialog(
    onDismiss: () -> Unit,
    onSave: (Float) -> Unit
) {
    var income by remember { mutableFloatStateOf(0f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.title_new_bitcoins)) },
        text = {
            Column {
                OutlinedTextField(
                    value = income.toString(),
                    onValueChange = {
                        income = it.toFloatOrNull() ?: 0f
                    },
                    label = { Text(stringResource(R.string.label_income)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (income > 0) {
                        onSave(income)
                    }
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.button_add))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.button_cancel))
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    )
}