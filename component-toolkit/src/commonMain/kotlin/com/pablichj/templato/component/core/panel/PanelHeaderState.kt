package com.pablichj.templato.component.core.panel

sealed interface PanelHeaderState

object NoPanelHeaderState : PanelHeaderState

class PanelHeaderStateDefault(
    val title: String,
    val description: String,
    val imageUri: String,
    val style: PanelHeaderStyle
): PanelHeaderState
