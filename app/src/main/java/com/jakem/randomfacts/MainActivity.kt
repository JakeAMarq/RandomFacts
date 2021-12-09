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
import com.jakem.randomfacts.feature_facts.data.remote.RetrofitInstance
import com.jakem.randomfacts.feature_facts.data.repository.FactRepositoryImpl
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import com.jakem.randomfacts.ui.theme.RandomFactsTheme
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factRepository: FactRepository = FactRepositoryImpl(RetrofitInstance.api)

        lifecycleScope.launchWhenCreated {

            factRepository.getNumberFacts(1, 50).collect { result ->
                when (result) {
                    is Resource.Loading -> Log.d("MainActivity", "loading number facts")
                    is Resource.Error -> Log.d("MainActivity", result.message ?: "Error")
                    is Resource.Success -> Log.d("MainActivity", result.data.toString())
                }
            }

            factRepository.getYearFacts(1998, 2021).collect { result ->
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