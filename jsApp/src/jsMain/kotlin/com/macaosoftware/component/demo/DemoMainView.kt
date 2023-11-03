package com.macaosoftware.component.demo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.macaosoftware.component.BrowserComponentRender
import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.factory.AdaptiveSizeDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.AppViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.BottomNavigationDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.DrawerDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.PagerDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.PanelDemoViewModelFactory
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.drawer.DrawerHeaderDefaultState
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault
import com.macaosoftware.component.drawer.DrawerStyle
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentDefaults
import com.macaosoftware.component.navbar.BottomNavigationStatePresenterDefault
import com.macaosoftware.component.navbar.BottomNavigationStyle
import com.macaosoftware.component.pager.PagerComponent
import com.macaosoftware.component.pager.PagerComponentDefaults
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.component.panel.PanelHeaderStateDefault
import com.macaosoftware.component.panel.PanelStatePresenterDefault
import com.macaosoftware.component.panel.PanelStyle
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import com.macaosoftware.platform.JsBridge
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DemoMainView(
    jsBridge: JsBridge,
    onBackPress: () -> Unit
) {

    val adaptiveSizeComponent = remember {
        AdaptiveSizeComponent(
            AdaptiveSizeDemoViewModelFactory()
        )
    }

    val bottomNavigationComponent = remember {
        BottomNavigationComponent(
            // pushStrategy = FixSizedPushStrategy(1), // Uncomment to test other push strategies
            viewModelFactory = BottomNavigationDemoViewModelFactory(
                bottomNavigationStatePresenter = BottomNavigationStatePresenterDefault(
                    dispatcher = Dispatchers.Main,
                    bottomNavigationStyle = BottomNavigationStyle(
                        showLabel = true
                    )
                )
            ),
            content = BottomNavigationComponentDefaults.BottomNavigationComponentView
        )
    }

    val drawerComponent = remember {
        DrawerComponent(
            viewModelFactory = DrawerDemoViewModelFactory(
                drawerStatePresenter = DrawerStatePresenterDefault(
                    dispatcher = Dispatchers.Main,
                    drawerStyle = DrawerStyle(),
                    drawerHeaderState = DrawerHeaderDefaultState(
                        title = "Component Toolkit",
                        description = "This is the default Drawer Component",
                        imageUri = "no_image",
                        style = DrawerStyle()
                    )
                )
            ),
            content = DrawerComponentDefaults.DrawerComponentView
        )
    }

    val panelComponent = remember {
        PanelComponent(
            viewModelFactory = PanelDemoViewModelFactory(
                panelStatePresenter = PanelStatePresenterDefault(
                    dispatcher = Dispatchers.Main,
                    panelStyle = PanelStyle(),
                    panelHeaderState = PanelHeaderStateDefault(
                        title = "Component Toolkit",
                        description = "This is the default Panel Component",
                        imageUri = "no_image",
                        style = PanelStyle()
                    )
                )
            ),
            content = PanelComponentDefaults.PanelComponentView
        )
    }

    val appComponent = remember {
        StackComponent(
            viewModelFactory = AppViewModelFactory(
                stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
            ),
            content = StackComponentDefaults.DefaultStackComponentView
        )
    }

    val pagerComponent = remember {
        PagerComponent(
            viewModelFactory = PagerDemoViewModelFactory(),
            content = PagerComponentDefaults.PagerComponentView
        )
    }

    var rootComponent by remember { mutableStateOf<Component?>(null) }

    MaterialTheme {
        Box {
            rootComponent.ifNotNull {
                BrowserComponentRender(
                    it,
                    jsBridge,
                    onBackPress = {
                        if (rootComponent == null) {
                            onBackPress.invoke()
                        } else {
                            rootComponent = null
                        }
                    }
                )
            }.elseIfNull {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LaunchButton(
                        "Drawer Example"
                    ) {
                        rootComponent = drawerComponent
                    }
                    LaunchButton(
                        "Pager Example"
                    ) {
                        rootComponent = pagerComponent
                    }
                    LaunchButton(
                        "Panel Example"
                    ) {
                        rootComponent = panelComponent
                    }
                    LaunchButton(
                        "BottomBar Example"
                    ) {
                        rootComponent = bottomNavigationComponent
                    }
                    LaunchButton(
                        "Adaptive Navigation Example"
                    ) {
                        rootComponent = adaptiveSizeComponent
                    }
                    LaunchButton(
                        "Stack Navigation with Splash screen Example"
                    ) {
                        rootComponent = appComponent
                    }
                }
            }
        }
    }
}

@Composable
private fun LaunchButton(
    text: String,
    onClick: () -> Unit
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    )
    Button(onClick = onClick) {
        Text(text)
    }
}
