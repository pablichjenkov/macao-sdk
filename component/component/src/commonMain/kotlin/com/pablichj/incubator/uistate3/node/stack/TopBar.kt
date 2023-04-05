package com.pablichj.incubator.uistate3.node.stack

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.platform.LocalSafeAreaInsets

@Composable
fun TopBar(
    topBarState: ITopBarState
) {

    val safeAreaInsets = LocalSafeAreaInsets.current

    val topBarIcon1: ImageVector? by remember(topBarState) {
        topBarState.icon1
    }

    val topBarIcon2: ImageVector? by remember(topBarState) {
        topBarState.icon2
    }

    val topBarTitle: String? by remember(topBarState) {
        topBarState.title
    }

    Column {
        Spacer(Modifier.fillMaxWidth().height(safeAreaInsets.top.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(1.dp, Color.Blue)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            topBarIcon1?.let {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            topBarState.onIcon1Click()
                        },
                    imageVector = it,
                    contentDescription = "com.pablichj.incubator.uistate3.node.stack.TopBar icon"
                )
            }

            topBarIcon2?.let {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            topBarState.onIcon2Click()
                        },
                    imageVector = it,
                    contentDescription = "com.pablichj.incubator.uistate3.node.stack.TopBar icon"
                )
            }

            topBarTitle?.let {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                        .clickable {
                            topBarState.onTitleClick()
                        },
                    text = it,
                )
            }

        }
    }

}
