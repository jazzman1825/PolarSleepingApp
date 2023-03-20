package com.example.composetest

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


private fun lineChartData() = listOf(
    5929, 6898, 8961, 5674, 7122, 2092, 3427, 5520, 4680, 7418,
    4743, 4280, 12211, 7295, 9900, 12438, 11186, 5439, 4227, 5138,
    11015, 8386, 12450, 10411, 10852, 7782, 7371, 4983, 9234, 6847
)
@Composable
fun SleepQualityChart() {

    Column() {


        Card(
            backgroundColor = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(8.dp)

        ) {
            Row(){
                Column(){
                    Text(text = "Deep")
                    Text(text = "REM")
                    Text(text = "Light")
                    Text(text = "Hours:")
                }
                Column(){
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .wrapContentSize(align = Alignment.BottomStart)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            val distance = size.width / (lineChartData().size + 1)
                            var currentX = 0F
                            val maxValue = lineChartData().maxOrNull() ?: 0
                            val points = mutableListOf<PointF>()

                            lineChartData().forEachIndexed { index, data ->
                                if (lineChartData().size >= index + 2) {
                                    val y0 = (maxValue - data) * (size.height / maxValue)
                                    val x0 = currentX + distance
                                    points.add(PointF(x0, y0))
                                    currentX += distance
                                }
                            }

                            for (i in 0 until points.size - 1) {
                                drawLine(
                                    start = Offset(points[i].x, points[i].y),
                                    end = Offset(points[i + 1].x, points[i + 1].y),
                                    color = Color(0xFF3F51B5),
                                    strokeWidth = 8f
                                )
                            }
                        }
                    }
                    Row(){
                        Text(text = "1")
                        Text(text = "2")
                        Text(text = "3")
                        Text(text = "4")
                        Text(text = "5")
                        Text(text = "6")
                        Text(text = "7")
                        Text(text = "8")
                        Text(text = "9")
                        Text(text = "10")
                        Text(text = "11")
                        Text(text = "12")
                        Text(text = "13")
                        Text(text = "14")
                        Text(text = "15")
                        Text(text = "16")
                        Text(text = "17")
                        Text(text = "18")
                        Text(text = "19")
                        Text(text = "20")
                        Text(text = "21")
                        Text(text = "22")
                        Text(text = "23")
                        Text(text = "00")

                    }
                }
            }
        }
        Card(
            backgroundColor = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(8.dp)
                ){

                Text(text = "You need to get more deep sleep.")
        }
    }
    }
