package com.macaosoftware.component.demo.startup

import com.macaosoftware.component.viewmodel.ComponentViewModelFactory
import com.macaosoftware.component.viewmodel.StateComponent

class StartupViewModelFactory() : ComponentViewModelFactory<StartupViewModel> {

    override fun create(component: StateComponent<StartupViewModel>): StartupViewModel {
        return StartupViewModel()
    }
}
