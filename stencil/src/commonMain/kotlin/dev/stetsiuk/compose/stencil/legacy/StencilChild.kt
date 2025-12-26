package dev.stetsiuk.compose.stencil.legacy

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.toSize
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun StencilConnection.StencilChild(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val id = rememberSaveable { Uuid.random().toString() }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(Size.Zero) }
    val configs = {
        StencilItemConfig(
            enabled = enabled,
            shape = shape,
            size = size,
            topLeft = offset
        )
    }
    Box(
        modifier = modifier
            .clip(shape)
            .onGloballyPositioned {
                offset = it.positionInParent()
                size = it.size.toSize()
            }
    ) {
        content()
    }

    DisposableEffect(Unit) {
        val data = StencilItemData(
            id = id,
            configs = configs,
        )
        add(data)
        onDispose { remove(data) }
    }
}