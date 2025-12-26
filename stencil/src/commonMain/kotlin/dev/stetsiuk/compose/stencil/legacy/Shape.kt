package dev.stetsiuk.compose.stencil.legacy

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

internal fun Shape.toPath(
    density: Density,
    size: Size,
    layoutDirection: LayoutDirection
): Path {
    return when (val outline = createOutline(size, layoutDirection, density)) {
        is Outline.Generic -> outline.path
        is Outline.Rectangle -> Path().apply { addRect(outline.rect) }
        is Outline.Rounded -> Path().apply { addRoundRect(outline.roundRect) }
    }
}