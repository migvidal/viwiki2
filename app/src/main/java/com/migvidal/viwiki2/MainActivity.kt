package com.migvidal.viwiki2

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.migvidal.viwiki2.data.network.isNetworkAvailable
import com.migvidal.viwiki2.data.network.registerNetworkListener
import com.migvidal.viwiki2.data.repository.Repository
import com.migvidal.viwiki2.ui.screens.NavGraphs
import com.migvidal.viwiki2.ui.screens.article_screen.ArticleScreen
import com.migvidal.viwiki2.ui.screens.article_screen.ArticleViewModel
import com.migvidal.viwiki2.ui.screens.destinations.ArticleScreenNavWrapperDestination
import com.migvidal.viwiki2.ui.screens.destinations.Destination
import com.migvidal.viwiki2.ui.screens.destinations.SavedArticlesScreenDestination
import com.migvidal.viwiki2.ui.screens.destinations.SearchScreenDestination
import com.migvidal.viwiki2.ui.screens.destinations.TodayScreenDestination
import com.migvidal.viwiki2.ui.screens.saved_articles_screen.SavedArticlesScreen
import com.migvidal.viwiki2.ui.screens.saved_articles_screen.SavedArticlesViewModel
import com.migvidal.viwiki2.ui.screens.search_screen.SearchScreen
import com.migvidal.viwiki2.ui.screens.search_screen.SearchViewModel
import com.migvidal.viwiki2.ui.screens.today_screen.TodayScreen
import com.migvidal.viwiki2.ui.screens.today_screen.TodayViewModel
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViWiki2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    var networkIsActive: Boolean by remember { mutableStateOf(false) }
                    SideEffect {
                        registerNetworkListener(
                            onConnected = {
                                networkIsActive = true
                            },
                            onDisconnected = {
                                networkIsActive = false
                            }
                        )
                    }
                    ViWikiApp(
                        networkIsActive = networkIsActive,
                        onCheckNetwork = {
                            networkIsActive = this@MainActivity.isNetworkAvailable()
                        })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ViWikiApp(networkIsActive: Boolean, onCheckNetwork: () -> Unit) {
    val dayViewModel: TodayViewModel = viewModel(factory = TodayViewModel.Factory)
    val searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
    val articleViewModel = viewModel<ArticleViewModel>(factory = ArticleViewModel.Factory)
    val savedArticlesViewModel =
        viewModel<SavedArticlesViewModel>(factory = SavedArticlesViewModel.Factory)

    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val currentTitleRes = run {
        val topLevelDestination = TopLevelDestination.from(navDestination = currentDestination)
        topLevelDestination?.label ?: R.string.app_name
    }


    Scaffold(
        topBar = {
            val isArticleSaved: Boolean by rememberSaveable { mutableStateOf(false) }
            val currentArticle = articleViewModel.articleData.collectAsState().value.query.pages.first()

            CustomTopBar(
                currentTitleRes = currentTitleRes,
                currentDestination = currentDestination,
                navController = navController,
                onCheckNetwork = onCheckNetwork,
                dayViewModel = dayViewModel,
                onSaveArticleClicked = { doSave ->
                    if (doSave) {
                        savedArticlesViewModel.saveArticle(currentArticle)
                    } else {
                        savedArticlesViewModel.unsaveArticle(currentArticle)
                    }
                },
            )
        },
        bottomBar = {
            Column {
                if (!networkIsActive) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Default.CloudOff, contentDescription = null)
                            Divider(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Offline mode",
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                NavigationBar {
                    TopLevelDestination.entries.forEach { tld ->
                        val labelText = stringResource(tld.label)
                        val isInCurrentBackStack =
                            currentDestination.isTopLevelDestinationInHierarchy(
                                tld.destination
                            )
                        NavigationBarItem(
                            selected = isInCurrentBackStack,
                            onClick = {
                                navController.navigate(tld.destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }

                            },
                            label = { Text(text = labelText) },
                            icon = {
                                Icon(
                                    imageVector = tld.icon,
                                    contentDescription = labelText,
                                )
                            })

                    }
                }
            }
        },
    ) { innerPadding ->
        val paddingModifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            // to prevent extra space above virtual keyboard: https://slack-chats.kotlinlang.org/t/5034424/how-do-i-use-modifier-imepadding-with-scaffold-material3-if-#9e60da1a-3c8c-4a1a-a20c-fe4d37a54454
            .consumeWindowInsets(innerPadding)
            .imePadding()
            .padding(horizontal = 16.dp)
        DestinationsNavHost(
            modifier = paddingModifier, navGraph = NavGraphs.root, navController = navController
        ) {
            composable(TodayScreenDestination) {
                val dayData = dayViewModel.dayData.collectAsState(initial = null).value
                val dayDataStatus =
                    dayViewModel.dayDataStatus.collectAsState(initial = Repository.Status.Loading).value
                TodayScreen(
                    dayData = dayData,
                    dayDataStatus = dayDataStatus,
                    onArticleClicked = { id ->
                        this.destinationsNavigator.navigate(
                            ArticleScreenNavWrapperDestination(
                                articleId = id
                            )
                        )
                    },
                )
            }
            composable(SearchScreenDestination) {
                if (!networkIsActive) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.no_internet_connection),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    val searchData = searchViewModel.searchData.collectAsState().value
                    SearchScreen(
                        searchResults = searchData.query?.search,
                        onSearchClicked = {
                            onCheckNetwork.invoke()
                            searchViewModel.refreshSearchDataFromRepository(query = it)
                        },
                        onResultClicked = {
                            this.destinationsNavigator.navigate(
                                direction = ArticleScreenNavWrapperDestination(articleId = it)
                            )
                        }
                    )
                }
            }
            composable(ArticleScreenNavWrapperDestination) {
                if (!networkIsActive) return@composable
                articleViewModel.refreshArticleDataFromRepository(pageId = this.navArgs.articleId)
                val articleData = articleViewModel.articleData.collectAsState().value
                val status = articleViewModel.articleDataStatus.collectAsState().value
                ArticleScreen(articleData = articleData, articleStatus = status)
            }
            composable(SavedArticlesScreenDestination) {
                SavedArticlesScreen(viewModel = savedArticlesViewModel)
            }

        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(topLevelDestination: Destination): Boolean {
    return this?.hierarchy?.any { navDestination ->
        navDestination.route?.contains(topLevelDestination.route, true) ?: false
    } ?: false
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ViWikiAppPreview() {
    ViWiki2Theme {
        ViWikiApp(true) {}
    }
}