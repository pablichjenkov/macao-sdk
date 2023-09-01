package com.macaosoftware.component.pager

enum class IndicatorType {
    NoIndicator,
    Dot,
    Tab
}

class PagerStyle(
    val indicatorType: IndicatorType = IndicatorType.Dot
)

enum class IndicatorOrientation {
    Horizontal, Vertical
}