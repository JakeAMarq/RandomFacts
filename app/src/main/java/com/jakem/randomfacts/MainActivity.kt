package com.jakem.randomfacts

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.jakem.randomfacts.core.util.Resource
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import com.jakem.randomfacts.feature_facts.domain.use_cases.GetNumberFactsUseCase
import com.jakem.randomfacts.feature_facts.domain.use_cases.GetYearFactUseCase
import com.jakem.randomfacts.ui.theme.RandomFactsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getNumberFactsUseCase: GetNumberFactsUseCase
    @Inject
    lateinit var getYearFactUseCase: GetYearFactUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {

            getNumberFactsUseCase(1, 50).collect { result ->
                Log.d("MainActivity", "NumberFacts:")
                when (result) {
                    is Resource.Loading -> Log.d("MainActivity", "\tLoading")
                    is Resource.Error -> Log.d("MainActivity", "\tError")
                    is Resource.Success -> Log.d("MainActivity", "\tSuccess")
                }
                Log.d("MainActivity", "\tMessage: " + result.message)
                Log.d("MainActivity", "\tData: " + result.data)
            }

            getYearFactUseCase(1998).collect { result ->
                when (result) {
                    is Resource.Loading -> Log.d("MainActivity", "loading year facts")
                    is Resource.Error -> Log.d("MainActivity", result.message ?: "Error")
                    is Resource.Success -> Log.d("MainActivity", result.data.toString())
                }
            }

        }


        setContent {
            RandomFactsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RandomFactsTheme {
        Greeting("Android")
    }
}