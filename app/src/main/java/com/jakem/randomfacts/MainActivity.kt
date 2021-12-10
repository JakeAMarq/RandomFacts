package com.jakem.randomfacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jakem.randomfacts.RandomFactsScreen.FactList
import com.jakem.randomfacts.RandomFactsScreen.YearFact
import com.jakem.randomfacts.ui.theme.RandomFactsTheme
import dagger.hilt.android.AndroidEntryPoint

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
            Text(
                text = "FactList screen",
                modifier = Modifier.clickable {
                    navController.navigate("${YearFact.name}/1998")
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
