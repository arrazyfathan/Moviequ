package com.arrazyfathan.moviequ.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun MovieItemShimmer() {
    Column(modifier = Modifier.background(Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Box(
                modifier =
                Modifier.weight(2f)
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect(),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(3f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(4.dp))

                Card(
                    elevation = CardDefaults.cardElevation(0.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(20.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(8.dp),
            color = Color.Black.copy(alpha = 0.1f),
            thickness = 0.7.dp,
        )
    }
}
