package com.jakem.randomfacts.feature_facts.presentation.fact_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jakem.randomfacts.R
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import com.jakem.randomfacts.feature_facts.presentation.FactCard
import com.jakem.randomfacts.ui.theme.RandomFactsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FactListScreen(
    state: FactListScreenState,
    onRefresh: () -> Unit,
    onFactCardClick: (Fact) -> Unit,
) {

    Column {
        
        TopAppBar(
            title = {
                Text(stringResource(id = R.string.title_fact_list_screen))
            },
            backgroundColor = MaterialTheme.colors.primary
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = state.isLoading),
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {

            Box {

                LazyColumn {
                    items(state.facts) {

                        FactCard(
                            title = stringResource(id = R.string.fact_title, it.number),
                            text = it.text,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                                .clickable {
                                    onFactCardClick(it)
                                }
                        )

                    }
                }

            }

        }

    }
}

@Preview
@Composable
fun FactListScreenPreview() {
    
    val facts = listOf(
        Fact(
            number = 1,
            text = "1 is the number of moons orbiting Earth."
        ),
        Fact(
            number = 2,
            text = "2 is the number of starts in a binary star system" +
                    " (a stellar system consisting of two stars orbiting" +
                    " around their center of mass)."
        ),
        Fact(
            number = 3,
            text = "3 is the number of points received for a successful field" +
                    " goal in both American football and Canadian football."
        )
    )
    
    RandomFactsTheme {
        Surface(color = MaterialTheme.colors.background) {
            
            val state = remember {
                mutableStateOf(
                    FactListScreenState(facts = facts)
                )
            }

            val coroutineScope = rememberCoroutineScope()

            FactListScreen(
                state = state.value,
                onRefresh = {
                    // Simulate a refresh
                    coroutineScope.launch {
                        state.value = state.value.copy(isLoading = true)
                        delay(3000)
                        state.value = state.value.copy(isLoading = false)
                    }
                },
                onFactCardClick = {}
            )
        }
    }
    
}