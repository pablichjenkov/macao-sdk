package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.HomeTopBarViewModel
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentViewModelFactory
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault

class HomeTopBarViewModelFactory(
    private val topBarStatePresenter: TopBarStatePresenterDefault,
    private val screenName: String,
    private val onDone: () -> Unit
) : TopBarComponentViewModelFactory<HomeTopBarViewModel> {

    override fun create(
        topBarComponent: TopBarComponent<HomeTopBarViewModel>
    ): HomeTopBarViewModel {
        return HomeTopBarViewModel(
            topBarComponent = topBarComponent,
            topBarStatePresenter = topBarStatePresenter,
            screenName = screenName,
            onDone = onDone
        )
    }
}
