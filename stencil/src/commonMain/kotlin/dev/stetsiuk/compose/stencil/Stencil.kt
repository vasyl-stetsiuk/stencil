package dev.stetsiuk.compose.stencil

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.toSize

@Composable
fun Stencil(
    state: StencilState,
    modifier: Modifier = Modifier,
    backgroundPositionParams: (
        parentParams: PositionInRootParams,
        childParams: PositionInRootParams
    ) -> PositionParams = { parentParams, childParams ->
        PositionParams.default(parentParams, childParams)
    },
    backgroundScope: DrawScope.(PositionParams) -> Unit = {},
    content: @Composable () -> Unit
) {
    state.backgroundPositionParams = backgroundPositionParams
    state.backgroundScope = backgroundScope

    Box(
        modifier = modifier
            .onGloballyPositioned {
                state.parentPositionParams = PositionInRootParams(
                    positionInRoot = it.positionInRoot(),
                    size = it.size.toSize()
                )
            }
    ) {
        content()
    }
}