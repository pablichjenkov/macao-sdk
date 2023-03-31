package com.pablichj.incubator.uistate3.node.pager

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