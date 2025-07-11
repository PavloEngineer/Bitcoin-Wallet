package com.app.bitcoinwallet.ui.screen.menu.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.app.bitcoinwallet.R
import com.app.bitcoinwallet.ui.model.StateUI
import com.app.bitcoinwallet.ui.model.TransactionGroupUI
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun TransactionsList(
    modifier: Modifier,
    transactionsState: StateUI<List<TransactionGroupUI>>,
    onLoadMore: () -> Unit,
    isLoadingNextPage: Boolean
) {
    val listState = rememberLazyListState()
    var lastHandledIndex by remember { mutableIntStateOf(-1) }

    LaunchedEffect(Unit) {
        snapshotFlow { listState.layoutInfo }
            .map { it.visibleItemsInfo }
            .distinctUntilChanged()
            .collect { visible ->
                val lastVisibleIndex = visible.lastOrNull()?.index ?: return@collect
                val totalItems = listState.layoutInfo.totalItemsCount

                if (lastVisibleIndex >= totalItems - 2 &&
                    lastVisibleIndex != lastHandledIndex &&
                    !isLoadingNextPage
                ) {
                    lastHandledIndex = lastVisibleIndex
                    onLoadMore()
                }
            }
    }

    when (transactionsState) {
        is StateUI.Loading -> {
            Box(modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is StateUI.Error -> {
            Box(modifier.fillMaxSize(), Alignment.Center) {
                Text(
                    text = transactionsState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is StateUI.Success -> {
            val groups = transactionsState.data

            if (groups.isEmpty()) {
                Box(modifier.fillMaxSize(), Alignment.Center) {
                    Text(
                        text = stringResource(R.string.text_empty_list),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                LazyColumn(
                    modifier = modifier,
                    state = listState
                ) {
                    groups.forEach { group ->
                        item(key = group.date) {
                            Text(
                                text = group.date,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.padding_small))
                            )
                        }

                        items(
                            items = group.items,
                            key = { it.id }
                        ) { transaction ->
                            ItemTransaction(transaction)
                        }
                    }
                }
            }
        }
    }
}

