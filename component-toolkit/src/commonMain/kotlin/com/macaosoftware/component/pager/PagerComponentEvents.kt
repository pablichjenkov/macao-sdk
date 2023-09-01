package com.macaosoftware.component.pager

sealed class PagerComponentOutEvent {
    class SelectPage(val page: Int) : PagerComponentOutEvent()
}

sealed class PagerComponentInEvent {
    class OnPageChanged(val page: Int) : PagerComponentInEvent()
}