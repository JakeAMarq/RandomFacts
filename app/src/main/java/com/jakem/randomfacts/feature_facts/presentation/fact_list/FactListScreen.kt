package com.jakem.randomfacts.feature_facts.presentation.fact_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jakem.randomfacts.R
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import com.jakem.randomfacts.ui.theme.RandomFactsTheme

@Composable
fun FactListScreen(
    state: FactListScreenState,
    onFactCardClick: (Fact) -> Unit,
) {

    Column {
        
        TopAppBar(
            title = {
                Text(stringResource(id = R.string.title_fact_list_screen))
            },
            backgroundColor = MaterialTheme.colors.primary
        )

        Box {

            LazyColumn {
                items(state.facts) {

                    FactCard(
                        fact = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 16.dp)
                            .clickable {
                                onFactCardClick(it)
                            }
                    )

                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.align(Alignment.Center)
                )
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
            
            val state = FactListScreenState(facts = facts)
            
            FactListScreen(
                state = state,
                onFactCardClick = {}
            )
        }
    }
    
}