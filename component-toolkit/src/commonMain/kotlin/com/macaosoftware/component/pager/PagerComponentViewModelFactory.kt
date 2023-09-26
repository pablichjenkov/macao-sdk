package com.macaosoftware.component.pager

interface PagerComponentViewModelFactory<VM : PagerComponentViewModel> {
    fun create(pagerComponent: PagerComponent<VM>): VM
}
