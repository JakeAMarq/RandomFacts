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
import com.jakem.randomfacts.feature_facts.data.remote.RetrofitInstance
import com.jakem.randomfacts.ui.theme.RandomFactsTheme
import retrofit2.HttpException
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getFactForNumbers(1, 50)
            } catch (e: IOException) {
                Log.d("MainActivity", "IOException", e)
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.d("MainActivity", "HttpException", e)
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                Log.d("MainActivity", response.body()!!.toString())
            }

            val response2 = try {
                RetrofitInstance.api.getFactForYears(1998)
            } catch (e: IOException) {
                Log.d("MainActivity", "IOException", e)
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.d("MainActivity", "HttpException", e)
                return@launchWhenCreated
            }

            if (response2.isSuccessful && response.body() != null) {
                Log.d("MainActivity", response2.body()!!.toString())
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