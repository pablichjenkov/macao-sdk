package com.macaosoftware.component.drawer

sealed interface DrawerHeaderState

object NoDrawerHeaderState : DrawerHeaderState

class DrawerHeaderDefaultState(
    val title: String,
    val description: String,
    val imageUri: String,
    val style: DrawerStyle
) : DrawerHeaderState


