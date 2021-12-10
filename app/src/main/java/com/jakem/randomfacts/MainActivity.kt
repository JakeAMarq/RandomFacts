package com.jakem.randomfacts

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.jakem.randomfacts.feature_facts.presentation.year_fact.YearFactScreen
import com.jakem.randomfacts.feature_facts.presentation.year_fact.YearFactViewModel
import com.jakem.randomfacts.ui.theme.RandomFactsTheme
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun yearFactViewModelFactory(): YearFactViewModel.Factory
    }

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

@SuppressLint("RememberReturnType")
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
        composable(FactList.name) { entry ->

            val viewModel: FactListViewModel = hiltViewModel()

            // Shows a toast whenever a new ShowToast event is emitted from viewModel.eventFlow
            val context = LocalContext.current
            LaunchedEffect(key1 = true) {
                viewModel.eventFlow.collectLatest { event ->
                    when(event) {
                        is FactListViewModel.UiEvent.ShowToast -> {
                            Toast.makeText(context, event.messageId, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            FactListScreen(
                state = viewModel.state.value,
                onRefresh = viewModel::onRefresh,
                onFactCardClick = {

                    // Only navigate if resumed to prevent double-click bug
                    if (entry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        navController.navigate("${YearFact.name}/${1900 + it.number}")
                    }

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

            val year = entry.arguments?.getInt("year")
                ?: throw IllegalStateException("No year argument passed to YearFactScreen")

            val viewModel = yearFactViewModel(year)

            YearFactScreen(
                state = viewModel.state.value,
                onBackButtonClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}

@Composable
fun yearFactViewModel(year: Int): YearFactViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).yearFactViewModelFactory()

    return viewModel(factory = YearFactViewModel.provideFactory(factory, year))
}

enum class RandomFactsScreen {
    FactList, YearFact
}
