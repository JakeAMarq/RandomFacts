package com.jakem.randomfacts.feature_facts.presentation.year_fact

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jakem.randomfacts.R
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import com.jakem.randomfacts.feature_facts.presentation.fact_list.FactCard
import com.jakem.randomfacts.ui.theme.RandomFactsTheme

@Composable
fun YearFactScreen(
    state: YearFactScreenState,
    onBackButtonClick: () -> Unit
) {
    Column {

        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back" // TODO
                    )
                }
            },
            title = {
                Text(stringResource(id = R.string.title_fact_list_screen))
            },
            backgroundColor = MaterialTheme.colors.primary
        )

        Box(modifier = Modifier.fillMaxSize()) {

            state.fact?.let {
                FactCard(
                    title = stringResource(id = R.string.year_title, it.number),
                    text = it.text,
                    modifier = Modifier
                        .matchParentSize()
                        .padding(8.dp)
                )
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
fun YearFactScreenPreview() {
    RandomFactsTheme {

        Surface(color = MaterialTheme.colors.background) {

            val state = YearFactScreenState(
                fact = Fact(
                    number = 1998,
                    text = "1998 is the year Jacob Marquardt was born."
                )
            )

            YearFactScreen(
                state = state,
                onBackButtonClick = {}
            )
        }

    }
}