package dev.stetsiuk.compose.stencil

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.toSize

@Composable
fun StencilChild(
    state: StencilState,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    // Calculates "topLeft" and "size" for "backgroundScope"
    positionParams: (
        parentParams: PositionInRootParams,
        childParams: PositionInRootParams
    ) -> PositionParams = { parentParams, childParams ->
        state.backgroundPositionParams?.let {
            it(state.parentPositionParams, childParams)
        } ?: PositionParams.default(parentParams, childParams)
    },
    backgroundScope: DrawScope.(PositionParams) -> Unit = { params ->
        state.backgroundScope?.let { it(this, params) }
    },
    content: @Composable () -> Unit
) {
    var positionParamsState by remember { mutableStateOf(PositionInRootParams()) }

    Box(
        modifier = modifier
            .clip(shape)
            .onGloballyPositioned {
                positionParamsState = PositionInRootParams(
                    positionInRoot = it.positionInRoot(),
                    size = it.size.toSize()
                )
            }
            .drawWithCache {
                val positionData = positionParams(state.parentPositionParams, positionParamsState)
                onDrawBehind {
                    backgroundScope(positionData)
                }
            }
    ) {
        content()
    }
}
