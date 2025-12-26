package dev.stetsiuk.compose.stencil.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.stetsiuk.compose.stencil.Stencil
import dev.stetsiuk.compose.stencil.StencilChild
import dev.stetsiuk.compose.stencil.StencilState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun StencilPreview1() {
    val color1 = Color(0xFFE91E63)
    val color2 = Color(0xFF9C27B0)
    val color3 = Color(0xFF3F51B5)

    val shape = RoundedCornerShape(24.dp)
    val state = _root_ide_package_.dev.stetsiuk.compose.stencil.rememberStencilState()

    Stencil(
        state = state,
        modifier = Modifier.fillMaxSize(),
        backgroundScope = { params ->
            val topLeft = params.relativeToParent
            val size = params.size

            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(color1, color2, color3),
                    start = Offset(0f, topLeft.y),
                    end = Offset(0f, topLeft.y + size.height)
                )
            )
        }
    ) {
        Items(state, shape)
    }
}

@Composable
private fun Items(
    state: StencilState,
    shape: Shape
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(26) {
            StencilChild(
                state = state,
                shape = shape
            ) {
                Text(
                    modifier = Modifier
                        .padding(12.dp),
                    text = "Hello, Jetpack Compose! $it",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    }
}