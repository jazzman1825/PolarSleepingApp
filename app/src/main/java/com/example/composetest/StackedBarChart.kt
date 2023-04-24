package com.example.composetest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val data = listOf(
    listOf(3f, 2f, 1f),
    listOf(2f, 1f, 1f),
    listOf(2f, 1f, 2f),
    listOf(1f, 1f, 1f),
    listOf(1f, 1f, 2f),
    listOf(1f, 2f, 1f),
    listOf(1f, 0.3f, 0.2f)
)

val sleepGoals = listOf(
    nights.getJSONObject(0).getInt("sleep_goal"),
    nights.getJSONObject(1).getInt("sleep_goal")
)

@Composable
fun StackedBarChart(
    data: List<List<Float>>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
) {


    val maxDataValue = data.flatten().maxOrNull() ?: 0f
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Card(
            backgroundColor = colors.secondary,
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
                    fontSize = 18.sp
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
                    Row(Modifier.weight(1f)
                        .height(200.dp)) {
                        data.forEach { dataPoint ->
                            StackedBar(
                                data = dataPoint,
                                maxDataValue = maxDataValue,
                                modifier = Modifier.weight(1f)
                            )
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

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MyCircle(Color(4281558410))
                    Text(" - Deep Sleep")
                    MyCircle(Color(4282100124))
                    Text(" - REM")
                    MyCircle(Color(4287201525))
                    Text(" - Light Sleep")
                }
            }}

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

                fun calculateSleepTime(start: String, end: String): Duration {
                    val formatter = DateTimeFormatter.ISO_DATE_TIME
                    val startTime =
                        LocalDateTime.parse(start, formatter).toInstant(ZoneOffset.UTC)
                    val endTime = LocalDateTime.parse(end, formatter).toInstant(ZoneOffset.UTC)
                    val duration = Duration.between(startTime, endTime)

                    return duration
                }


                fun extractHoursAndMinutes(duration: Duration): Pair<Long, Long> {
                    val hours = duration.toHours()
                    val minutes = duration.toMinutes() % 60
                    return Pair(hours, minutes)
                }

                val duration = calculateSleepTime(sleepStartTime, sleepEndTime)
                val (hours, minutes) = extractHoursAndMinutes(duration)
                if (hours > 7) {
                    Text(
                        text = "You've slept well!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = colors.secondary
                    )
                } else {
                    Text(
                        text = "You need to get more sleep!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Today you slept for:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )


                    Text(
                        text = "$hours h $minutes m",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = colors.primary
                    )
                }



            }
        }
    }
}


@Composable
fun StackedBar(
    data: List<Float>,
    maxDataValue: Float,
    modifier: Modifier = Modifier,
    barColors: List<Color> = listOf(Color(4281558410), Color(4282100124), Color(4287201525))

) {
    Canvas(modifier = modifier.height(200.dp)
        .padding(vertical = 24.dp)) {
        val barWidth = size.width / 2
        val barHeight = size.height - 104.dp.toPx()

        val category1Height = data[0] / maxDataValue * barHeight
        val category2Height = data[1] / maxDataValue * barHeight
        val category3Height = data[2] / maxDataValue * barHeight


        val topLeft = Offset(10.dp.toPx(), size.height - category1Height - category2Height - category3Height - 10.dp.toPx())
        val bottomRight = Offset(size.width - 10.dp.toPx(), size.height - 10.dp.toPx())

        drawRect(
            brush = SolidColor(barColors[0]),
            topLeft = topLeft,
            size = Size(size.width - 20.dp.toPx(), category1Height)
        )
        drawRect(
            brush = SolidColor(barColors[1]),
            topLeft = Offset(topLeft.x, topLeft.y + category1Height),
            size = Size(size.width - 20.dp.toPx(), category2Height)
        )
        drawRect(
            brush = SolidColor(barColors[2]),
            topLeft = Offset(topLeft.x, topLeft.y + category1Height + category2Height),
            size = Size(size.width - 20.dp.toPx(), category3Height)
        )

        drawRect(
            brush = SolidColor(Color.Black),
            topLeft = topLeft,
            size = Size(size.width - 20.dp.toPx(), category1Height + category2Height + category3Height),
            style = Stroke(0.dp.toPx())
        )
    }


}

