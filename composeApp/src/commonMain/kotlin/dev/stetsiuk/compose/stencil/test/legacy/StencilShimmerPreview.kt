package dev.stetsiuk.compose.stencil.test.legacy

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.stetsiuk.compose.stencil.legacy.Stencil
import dev.stetsiuk.compose.stencil.legacy.StencilChild
import dev.stetsiuk.compose.stencil.legacy.rememberStencilConnection
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun StencilShimmerPreview() {
    val color1 = Color(0xFFE91E63)
    val color2 = Color(0xFF673AB7)
    val colors = listOf(color1, color2, color1)
    val shape = RoundedCornerShape(24.dp)
    val connection = rememberStencilConnection()

    Stencil(
        connection = connection,
        contentPadding = PaddingValues(20.dp),
        background = {
            Spacer(
                Modifier
                    .matchParentSize()
                    .background(shimmerBrush(colors))
            )
        },
        parentBackground = SolidColor(Color.White),
        childContent = { contentPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(26) {
                    connection.StencilChild(
                        shape = shape
                    ) {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = "Hello, Jetpack Compose! $it",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun shimmerBrush(
    colors: List<Color> = listOf(
        Color.LightGray,
        Color.Gray,
        Color.LightGray
    ),
    animDuration: Int = 1600
): Brush {
    val heightPx = with(LocalDensity.current) { 700.dp.toPx() }
    val limitFraction = 1.5f

    val transition = rememberInfiniteTransition(label = "")
    val progressAnimated by transition.animateFloat(
        initialValue = -limitFraction,
        targetValue = limitFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(animDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val offset = heightPx * progressAnimated

    return Brush.linearGradient(
        colors = colors,
        start = Offset(offset, offset),
        end = Offset(offset + heightPx, offset + heightPx)
    )
}