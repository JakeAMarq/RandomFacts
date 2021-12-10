package com.jakem.randomfacts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jakem.randomfacts.RandomFactsScreen.FactList
import com.jakem.randomfacts.RandomFactsScreen.YearFact
import com.jakem.randomfacts.feature_facts.presentation.fact_list.FactListScreen
import com.jakem.randomfacts.feature_facts.presentation.fact_list.FactListViewModel
import com.jakem.randomfacts.ui.theme.RandomFactsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomFactsApplication()
        }
    }
}

@Composable
fun RandomFactsApplication() {
    RandomFactsTheme {
        val navController = rememberNavController()

        Scaffold {
            RandomFactsNavHost(navController = navController)
        }

    }
}

@Composable
fun RandomFactsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = FactList.name,
        modifier = modifier
    ) {
        composable(FactList.name) {

            val viewModel: FactListViewModel = hiltViewModel()

            // Shows a toast whenever a new ShowToast event is emitted from viewModel.eventFlow
            val context = LocalContext.current
            LaunchedEffect(key1 = true) {
                viewModel.eventFlow.collectLatest { event ->
                    when(event) {
                        is FactListViewModel.UiEvent.ShowToast -> {
                            Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            FactListScreen(
                state = viewModel.state.value,
                onFactCardClick = {
                    navController.navigate("${YearFact.name}/${1900 + it.number}")
                }
            )
        }
        composable(
            route = "${YearFact.name}/{year}",
            arguments = listOf(
                navArgument("year") {
                    type = NavType.IntType
                }
            )
        ) { entry ->
            Text("Year screen: ${entry.arguments?.getInt("year")}")
        }
    }
}

enum class RandomFactsScreen {
    FactList, YearFact
}
