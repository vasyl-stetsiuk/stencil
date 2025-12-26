package dev.stetsiuk.compose.stencil.test.legacy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.stetsiuk.compose.stencil.legacy.Stencil
import dev.stetsiuk.compose.stencil.legacy.StencilChild
import dev.stetsiuk.compose.stencil.legacy.StencilParent
import dev.stetsiuk.compose.stencil.legacy.rememberStencilConnection
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun StencilPreview1() {
    val color1 = Color(0xFFE91E63)
    val color2 = Color(0xFF673AB7)

    val gradientBrush = Brush.verticalGradient(
        listOf(color1, color2)
    )
    val shape = RoundedCornerShape(24.dp)
    val connection = rememberStencilConnection()

    Stencil(
        connection = connection,
        contentPadding = PaddingValues(20.dp),
        background = {
            Spacer(Modifier.matchParentSize().background(gradientBrush))
        },
        parentBackground = SolidColor(Color.White),
        childContent = { contentPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding,
                horizontalAlignment = Alignment.CenterHorizontally,
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

@Preview
@Composable
private fun StencilPreview2() {
    val color1 = Color(0xFFE91E63)
    val color2 = Color(0xFF673AB7)

    val gradientBrush = Brush.verticalGradient(
        listOf(color1, color2)
    )
    val shape = RoundedCornerShape(24.dp)
    val connection = rememberStencilConnection()

    Box {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
        )

        StencilParent(
            modifier = Modifier.matchParentSize(),
            connection = connection,
            childBackground = SolidColor(Color.White)
        )

        LazyColumn(
            modifier = Modifier
                .matchParentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
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
}