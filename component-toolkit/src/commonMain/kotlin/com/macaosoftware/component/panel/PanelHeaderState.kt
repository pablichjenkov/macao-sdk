package com.macaosoftware.component.panel

sealed interface PanelHeaderState

object NoPanelHeaderState : PanelHeaderState

class PanelHeaderStateDefault(
    val title: String,
    val description: String,
    val imageUri: String,
    val style: PanelStyle
): PanelHeaderState
