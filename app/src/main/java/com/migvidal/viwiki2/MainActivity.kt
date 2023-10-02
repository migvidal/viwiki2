package com.migvidal.viwiki2

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.migvidal.viwiki2.ui.screens.destinations.SearchScreenDestination
import com.migvidal.viwiki2.ui.screens.destinations.TodayScreenDestination
import com.migvidal.viwiki2.ui.theme.ViWiki2Theme
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
    Search(label = R.string.search, icon = Icons.Default.Search, destination = SearchScreenDestination),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViWikiApp() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Title") })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = true, onClick = {}, icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"
                    )
                },
                    label = { Text(text = "Home") })
            }
        },
    ) {
        Text(
            modifier = Modifier.padding(it),
            text = "Section screen"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ViWikiAppPreview() {
    ViWiki2Theme {
        ViWikiApp()
    }
}