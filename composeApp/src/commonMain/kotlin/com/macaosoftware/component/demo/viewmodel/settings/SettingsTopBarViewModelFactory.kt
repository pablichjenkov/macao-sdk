package com.macaosoftware.component.demo.viewmodel.settings

import com.macaosoftware.component.demo.viewmodel.settings.SettingsTopBarViewModel
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentViewModelFactory
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault

class SettingsTopBarViewModelFactory(
    private val topBarStatePresenter: TopBarStatePresenterDefault,
    private val screenName: String,
    private val onDone: () -> Unit
) : TopBarComponentViewModelFactory<SettingsTopBarViewModel> {

    override fun create(
        topBarComponent: TopBarComponent<SettingsTopBarViewModel>
    ): SettingsTopBarViewModel {
        return SettingsTopBarViewModel(
            topBarComponent = topBarComponent,
            topBarStatePresenter = topBarStatePresenter,
            screenName = screenName,
            onDone = onDone
        )
    }
}
