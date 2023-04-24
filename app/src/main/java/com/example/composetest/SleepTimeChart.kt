package com.example.composetest


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONObject

val sleeptime = listOf(
    listOf(1f, 1f, 1f),
    listOf(2f, 1f, 1f),
    listOf(1f, 1f, 3f),
    listOf(1f, 1f, 1f),
    listOf(1f, 1f, 2f),
    listOf(1f, 2f, 1f),
    listOf(1f, 1f, 2f)
)


val hoursList = arrayListOf<Float>(

)




private val defaultMaxHeight = 200.dp



@Composable

fun SleepTimeChart(
    values: List<List<Float>>,
    modifier: Modifier = Modifier,
    maxHeight: Dp = defaultMaxHeight
) {

    val borderColor = colors.primary
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            backgroundColor = colors.secondary,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Week Statistics",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Row() {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        Text(text = "10h")
                        Text(text = "8h")
                        Text(text = "6h")
                        Text(text = "4h")

                    }

                    Column() {
                        Row(

                            modifier = modifier.then(
                                Modifier
                                    .fillMaxWidth()
                                    .height(maxHeight)
                                    .drawBehind {
                                        drawLine(
                                            color = borderColor,
                                            start = Offset(0f, size.height),
                                            end = Offset(size.width, size.height),
                                            strokeWidth = strokeWidth
                                        )

                                        drawLine(
                                            color = borderColor,
                                            start = Offset(0f, size.height),
                                            end = Offset(0f, 0f),
                                            strokeWidth = strokeWidth
                                        )
                                    }
                            ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {


                            @Composable
                            fun RowScope.Bar(
                                value: Float,
                                color: Color,
                                maxHeight: Dp

                            ) {

                                val itemHeight =
                                    remember(value) { value * maxHeight.value / 10 }

                                Spacer(
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                        .height(itemHeight.dp)
                                        .weight(1f)
                                        .background(color)
                                )

                            }
                            Row(
                                verticalAlignment = Alignment.Bottom
                            ) {

                                /* for ((index) in sleeptime.withIndex()) {
                                      val newItem = Night[index].toFloat()
                                      hoursList.add(newItem)
                                  }*/


                                hoursList.forEach { item ->
                                    Bar(
                                        value = item,
                                        color = colors.primary,
                                        maxHeight = maxHeight
                                    )
                                }

                            }

                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(22.dp)
                        ) {
                            Text(text = "Mon")
                            Text(text = "Tue")
                            Text(text = "Wed")
                            Text(text = "Thu")
                            Text(text = "Fri")
                            Text(text = "Sat")
                            Text(text = "Sun")

                        }

                    }
                }
            }

        }
        Card(
            backgroundColor = colors.secondary,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You've slept well!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

            }
        }



    }


}