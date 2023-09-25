package com.macaosoftware.component.pager

interface PagerComponentViewModelFactory {
    fun create(pagerComponent: PagerComponent): PagerComponentViewModel
}
