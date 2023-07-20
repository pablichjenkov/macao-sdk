package com.pablichj.templato.component.core.drawer

sealed interface DrawerHeaderState

object NoDrawerHeaderState : DrawerHeaderState

class DrawerHeaderDefaultState(
    val title: String,
    val description: String,
    val imageUri: String,
    val style: DrawerHeaderStyle
) : DrawerHeaderState


