package com.migvidal.viwiki2

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.migvidal.viwiki2.ui.ViWikiViewModel
import com.migvidal.viwiki2.ui.screens.NavGraphs
import com.migvidal.viwiki2.ui.screens.destinations.SearchScreenDestination
import com.migvidal.viwiki2.ui.screens.destinations.TodayScreenDestination
import com.migvidal.viwiki2.ui.screens.today_screen.TodayScreen
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViWiki2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ViWikiApp()
                }
            }
        }
    }
}

enum class TopLevelDestination(
    @StringRes val label: Int,
    val icon: ImageVector,
    val destination: DirectionDestinationSpec,
) {
    Today(label = R.string.today, icon = Icons.Default.Home, destination = TodayScreenDestination),
    Search(
        label = R.string.search,
        icon = Icons.Default.Search,
        destination = SearchScreenDestination
    ),
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ViWikiApp() {
    val viewModel: ViWikiViewModel = viewModel(factory = ViWikiViewModel.Factory)
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Title") })
        },
        bottomBar = {
            NavigationBar {
                TopLevelDestination.values().forEach {
                    val labelText = stringResource(it.label)
                    NavigationBarItem(
                        selected = currentDestination.isTopLevelDestinationInHierarchy(it.destination),
                        onClick = {
                            navController.popBackStack()
                            navController.navigate(it.destination.route)
                        },
                        label = { Text(text = labelText) },
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = labelText,
                            )
                        }
                    )

                }
            }
        },
    ) { innerPadding ->
        val paddingModifier = Modifier
            .padding(innerPadding)
            // to prevent extra space above virtual keyboard: https://slack-chats.kotlinlang.org/t/5034424/how-do-i-use-modifier-imepadding-with-scaffold-material3-if-#9e60da1a-3c8c-4a1a-a20c-fe4d37a54454
            .consumeWindowInsets(innerPadding)
            .imePadding()
            .padding(16.dp)
        DestinationsNavHost(
            modifier = paddingModifier, navGraph = NavGraphs.root, navController = navController
        ) {
            composable(TodayScreenDestination) {
                val dayData = viewModel.dayData.collectAsState(initial = null).value
                val dayDataStatus = viewModel.dayDataStatus.collectAsState(initial = null).value
                TodayScreen(
                    dayData = dayData,
                    dayDataStatus = dayDataStatus,
                    onRefreshClicked = { viewModel.refreshDataFromRepository() })
            }
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(topLevelDestination: DirectionDestinationSpec) =
    this?.hierarchy?.any {
        it.route?.contains(topLevelDestination.route, true) ?: false
    } ?: false

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ViWikiAppPreview() {
    ViWiki2Theme {
        ViWikiApp()
    }
}