package com.arrazyfathan.moviequ.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.arrazyfathan.moviequ.domain.Movie
import com.arrazyfathan.moviequ.ui.theme.MoviequTheme

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.background(Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            AsyncImage(
                model = movie.poster,
                contentDescription = null,
                modifier =
                    Modifier.weight(2f)
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect(),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(3f)) {
                Text(
                    text = movie.title,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Release Date: ${movie.year}",
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Card(
                    elevation = CardDefaults.cardElevation(0.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = Color.Black,
                            contentColor = Color.White,
                        ),
                ) {
                    Text(
                        text = movie.type.replaceFirstChar { it.uppercase() },
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
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

@Preview
@Composable
fun MovieItemPreview() {
    MoviequTheme {
        MovieItem(
            movie =
                Movie(
                    title = "Kimi No Nawa",
                    year = "2017",
                    poster =
                        "https://m.media-amazon.com/images/M/MV5BODRmZDVmNzUtZDA4ZC00NjhkLWI2M2UtN2M0ZDIzNDcxYThjL2ltYWdlXkEyXkFqcGdeQXVyNTk0MzMzODA@._V1_SX300.jpg",
                    imdbID = "",
                    type = "series",
                ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
