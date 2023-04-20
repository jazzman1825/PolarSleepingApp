package com.example.composetest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun StackedBarChart(
    data: List<List<Float>>,
    colors: List<List<Color>>,
    modifier: Modifier = Modifier
        .height(250.dp)
        .fillMaxWidth()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Card(
            backgroundColor = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(8.dp)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ){
                println(nights)

                Text(
                    "Week Statistics",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Row(){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        Text(text = "10h")
                        Text(text = "8h")
                        Text(text = "6h")
                        Text(text = "4h")

                    }
                    Canvas(modifier = modifier.fillMaxSize()) {
                        val barWidth = size.width / data.size

                        data.forEachIndexed { index, categories ->
                            var currentY = 0f
                            categories.forEachIndexed { categoryIndex, value ->
                                val barHeight = size.height * value
                                drawRect(
                                    color = colors[index][categoryIndex],
                                    topLeft = Offset(index * barWidth, size.height - currentY - barHeight), // Align to bottom x-axis
                                    size = Size(barWidth - 20, barHeight)
                                )
                                currentY += barHeight
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(text = "Mon")
                    Text(text = "Tue")
                    Text(text = "Wed")
                    Text(text = "Thu")
                    Text(text = "Fri")
                    Text(text = "Sat")
                    Text(text = "Sun")

                }
                @Composable
                fun MyCircle(color:Color){
                    Canvas(modifier = Modifier.size(20.dp), onDraw = {
                        drawCircle(color = color)

                    })
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MyCircle(Color(4287201525))
                    Text(" - Deep Sleep")
                    MyCircle(Color(4282100124))
                    Text(" - REM")
                    MyCircle(Color(4281558410))
                    Text(" - Light Sleep")
                }
            }
        }
        Card(
            backgroundColor = MaterialTheme.colors.secondary,
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
                fun calculateSleepTime(start: String, end: String): Duration {
                    val formatter = DateTimeFormatter.ISO_DATE_TIME
                    val startTime =
                        LocalDateTime.parse(start, formatter).toInstant(ZoneOffset.UTC)
                    val endTime = LocalDateTime.parse(end, formatter).toInstant(ZoneOffset.UTC)
                    val duration = Duration.between(startTime, endTime)
                    return duration
                }

                val sleepTime =
                    calculateSleepTime(sleepStartTime.toString(), sleepEndTime.toString())

                fun extractHoursAndMinutes(durationString: String): Pair<Long, Long> {
                    val duration = Duration.parse(durationString)
                    val hours = duration.toHours()
                    val minutes = duration.toMinutes() % 60
                    return Pair(hours, minutes)
                }

                val durationString = "PT8H40M30S"
                val (hours, minutes) = extractHoursAndMinutes(durationString)

                Text(
                    text = "Today you slept for $hours hours and $minutes minutes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )


            }
        }
    }



}
