package com.arrazyfathan.moviequ.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.arrazyfathan.moviequ.ui.theme.MoviequTheme

@Composable
fun ShimmerListItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if(isLoading) {
        MovieItemShimmer()
    } else {
        contentAfterLoading()
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        ),
        shape = RoundedCornerShape(8.dp)
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Preview
@Composable
fun ShimmerPreview() {
    MoviequTheme {
        ShimmerListItem(
            isLoading = true,
            contentAfterLoading = {

            },
            modifier = Modifier
        )
    }
}
