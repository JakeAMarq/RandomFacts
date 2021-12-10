package com.jakem.randomfacts.feature_facts.presentation.fact_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jakem.randomfacts.R
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import com.jakem.randomfacts.ui.theme.RandomFactsTheme

private val CardHeight = 175.dp

@Composable
fun FactCard(
    title: String,
    text: String,
    modifier: Modifier = Modifier
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = modifier.height(CardHeight)
    ) {

        Box {

            Image(
                painter = painterResource(id = R.drawable.img_cards),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(CardHeight)
                    .align(Alignment.BottomEnd)
            )

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                Text(
                    text = text,
                    modifier = Modifier.alpha(0.7F)
                )

            }
        }

    }

}

@Preview
@Composable
fun FactCardPreviewLight() {

    RandomFactsTheme(darkTheme = false) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {

            Column {
                FactCard(
                    title = "Random Fact #2",
                    text = "2 is the number of starts in a binary star system" +
                            " (a stellar system consisting of two stars orbiting" +
                            " around their center of mass).",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                )
            }

        }
    }

}

@Preview
@Composable
fun FactCardPreviewDark() {

    RandomFactsTheme(darkTheme = true) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {

            Column {
                FactCard(
                    title = "Random Fact #2",
                    text = "2 is the number of starts in a binary star system" +
                            " (a stellar system consisting of two stars orbiting" +
                            " around their center of mass).",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                )
            }

        }
    }

}