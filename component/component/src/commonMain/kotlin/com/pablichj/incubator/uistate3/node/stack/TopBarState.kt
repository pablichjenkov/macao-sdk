package com.pablichj.incubator.uistate3.node.stack

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

interface ITopBarState {
    val icon1: State<ImageVector?>
    val icon2: State<ImageVector?>
    val title: State<String?>
    fun setTitleSectionState(titleSectionStateHolder: TitleSectionStateHolder)
    fun onIcon1Click()
    fun onIcon2Click()
    fun onTitleClick()
    fun handleBackPress()
}

class TopBarState(
    val onBackPress: () -> Unit
) : ITopBarState {

    private var titleSectionStateHolder: TitleSectionStateHolder? = null

    override val icon1 = mutableStateOf<ImageVector?>(null)
    override val icon2 = mutableStateOf<ImageVector?>(null)
    override val title = mutableStateOf<String?>(null)

    override fun setTitleSectionState(titleSectionStateHolder: TitleSectionStateHolder) {
        this.titleSectionStateHolder = titleSectionStateHolder
        icon1.value = titleSectionStateHolder.icon1
        icon2.value = titleSectionStateHolder.icon2
        title.value = titleSectionStateHolder.title
    }

    override fun onIcon1Click() {
        titleSectionStateHolder?.onIcon1Click?.invoke()
    }

    override fun onIcon2Click() {
        titleSectionStateHolder?.onIcon2Click?.invoke()
    }

    override fun onTitleClick() {
        titleSectionStateHolder?.onTitleClick?.invoke()
    }

    override fun handleBackPress() {
        onBackPress()
    }
}

data class TitleSectionStateHolder(
    val title: String? = null,
    val icon1: ImageVector? = null,
    val icon2: ImageVector? = null,
    val onIcon1Click: (() -> Unit)? = null,
    val onIcon2Click: (() -> Unit)? = null,
    val onTitleClick: (() -> Unit)? = null,
)