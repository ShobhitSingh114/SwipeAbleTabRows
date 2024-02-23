package com.example.swipeabletabrows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.swipeabletabrows.ui.theme.SwipeAbleTabRowsTheme

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tabItems = listOf(
            TabItem(
                "Home1",
                Icons.Outlined.Home,
                Icons.Filled.Home
            ),
            TabItem(
                "Browse1",
                Icons.Outlined.ShoppingCart,
                Icons.Filled.ShoppingCart
            ),
            TabItem(
                "Account1",
                Icons.Outlined.AccountCircle,
                Icons.Filled.AccountCircle
            ),
            TabItem(
                "Home2",
                Icons.Outlined.Home,
                Icons.Filled.Home
            ),
            TabItem(
                "Browse2",
                Icons.Outlined.ShoppingCart,
                Icons.Filled.ShoppingCart
            ),
            TabItem(
                "Account2",
                Icons.Outlined.AccountCircle,
                Icons.Filled.AccountCircle
            ),
        )

        setContent {
            SwipeAbleTabRowsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedTabIndex by remember {
                        mutableIntStateOf(0)
                    }
                    // initialze pagerState Here
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
                            tabItems.forEachIndexed { index, tabItem ->
                                Tab(
                                    // selected = checking if present tab is selected or not
                                    // i.e. if index of tabItems is same as selectedTabItem
                                    // then it is selected
                                    selected = index == selectedTabIndex,
                                    onClick = {
                                        // when we click on the tab
                                        // then give selectedTabItem, current index
                                        selectedTabIndex = index
                                    },
                                    // TabItem ka text
                                    text = { Text(text = tabItem.title) },
                                    icon = {
                                        Icon(
                                            // checking and setting
                                            // when selected show filled icon
                                            // else show filled icon
                                            imageVector =
                                            if (index == selectedTabIndex){
                                                tabItem.selectedItem
                                        }else tabItem.unselectedItem,
                                            contentDescription = tabItem.title
                                        )
                                    }
                                )
                            }
                        }
                        // opt in for containing class mainActivity
                        val pagerState = rememberPagerState {
                            tabItems.size
                        }

                        // It triggred when 'selected tab' is change by 'tapping' the 'tab' not by 'swiping'
                        LaunchedEffect(selectedTabIndex){
                            // when we do that
                            // update the currently selected page of our horizontal pager as well

                            // In a nutshell = tab selected kr ne per ye horizontal pager ko change kr rha hai

                            pagerState.animateScrollToPage(selectedTabIndex)
                        }

                        // State of the currently selected page
                        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress){

                            // In a nutshell = horizontalPager swipe kr ne per ye TabRow ko change kr rha hai
                            if (!pagerState.isScrollInProgress){
                                selectedTabIndex = pagerState.currentPage
                            }
                        }

                        // for swiping functionality
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                // this weight means ScrollableTabRow get the required space
                                // and rest space is allocated to Horizontal Pager
                                .weight(1f)
                        ) { index ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = tabItems[index].title)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class TabItem(
    val title: String,
    val unselectedItem: ImageVector,
    val selectedItem: ImageVector
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SwipeAbleTabRowsTheme {

    }
}