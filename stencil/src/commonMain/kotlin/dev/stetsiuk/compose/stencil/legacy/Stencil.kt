package dev.stetsiuk.compose.stencil.legacy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun Stencil(
    background: @Composable BoxScope.() -> Unit,
    parentBackground: Brush,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    connection: StencilConnection = rememberStencilConnection(),
    childContent: @Composable StencilConnection.(PaddingValues) -> Unit
) {
    Box(modifier) {
        background()

        StencilParent(
            modifier = Modifier.matchParentSize(),
            connection = connection,
            childBackground = parentBackground
        )

        childContent(connection, contentPadding)
    }
}

data class StencilItemData @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String = Uuid.random().toString(),
    val configs: () -> StencilItemConfig,
)


data class StencilItemConfig(
    val enabled: Boolean,
    val shape: Shape,
    val topLeft: Offset,
    val size: Size
)

class StencilConnection {

    internal val values = mutableStateListOf<StencilItemData>()

    fun add(data: StencilItemData) {
        if (values.none { it.id == data.id }) values.add(data)
    }

    fun remove(data: StencilItemData) {
        values.remove(data)
    }
}

@Composable
fun rememberStencilConnection() = remember { StencilConnection() }