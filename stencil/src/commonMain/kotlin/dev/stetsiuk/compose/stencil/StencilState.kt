package dev.stetsiuk.compose.stencil

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope

@Stable
data class PositionInRootParams(
    val positionInRoot: Offset = Offset.Zero,
    val size: Size = Size.Zero
)

@Stable
data class PositionParams(
    val relativeToParent: Offset = Offset.Zero,
    val size: Size = Size.Zero
) {
    companion object {
        fun default(
            parentParams: PositionInRootParams,
            childParams: PositionInRootParams
        ) = PositionParams(
            // position relative to parent
            relativeToParent = parentParams.positionInRoot - childParams.positionInRoot,
            size = parentParams.size
        )
    }
}

@Stable
class StencilState(
    initialParentPositionParams: PositionInRootParams = PositionInRootParams()
) {
    var parentPositionParams by mutableStateOf<PositionInRootParams>(initialParentPositionParams)
        internal set

    var backgroundPositionParams by mutableStateOf<((
        parentParams: PositionInRootParams,
        childParams: PositionInRootParams
    ) -> PositionParams)?>(null)
        internal set

    var backgroundScope by mutableStateOf<(DrawScope.(PositionParams) -> Unit)?>(null)
        internal set
}

@Composable
fun rememberStencilState(
    initialParentPositionParams: PositionInRootParams = PositionInRootParams()
): StencilState = remember {
    StencilState(initialParentPositionParams)
}

