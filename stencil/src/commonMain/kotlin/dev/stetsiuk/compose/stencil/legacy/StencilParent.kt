package dev.stetsiuk.compose.stencil.legacy

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun StencilParent(
    connection: StencilConnection,
    childBackground: Brush,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    Spacer(
        modifier = modifier
            .drawWithCache {
                val cutoutPath = Path().apply {
                    connection.values
                        .map { it.configs() }
                        .filter { it.enabled }
                        .forEach { item ->
                            addPath(
                                path = item.shape.toPath(
                                    density,
                                    item.size,
                                    LayoutDirection.Ltr
                                ),
                                offset = item.topLeft
                            )
                        }
                }
                onDrawBehind {
                    clipPath(cutoutPath, ClipOp.Difference) {
                        drawRect(childBackground)
                    }
                }
            }
    )
}