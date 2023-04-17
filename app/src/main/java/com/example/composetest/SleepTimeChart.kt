package com.example.composetest


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.google.gson.Gson
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val stackedData = listOf(
    listOf(0.2f, 0.3f, 0.5f),
    listOf(0.4f, 0.1f, 0.5f),
    listOf(0.6f, 0.2f, 0.2f),
    listOf(0.4f, 0.2f, 0.4f),
    listOf(0.3f, 0.4f, 0.3f),
    listOf(0.5f, 0.2f, 0.3f),
    listOf(0.2f, 0.3f, 0.5f),
    )

// Example colors
val stackedColors = listOf(
    listOf(Color(4281558410), Color(4282100124), Color(4287201525)),
    listOf(Color(4281558410), Color(4282100124), Color(4287201525)),
    listOf(Color(4281558410), Color(4282100124), Color(4287201525)),
    listOf(Color(4281558410), Color(4282100124), Color(4287201525)),
    listOf(Color(4281558410), Color(4282100124), Color(4287201525)),
    listOf(Color(4281558410), Color(4282100124), Color(4287201525)),
    listOf(Color(4281558410), Color(4282100124), Color(4287201525)),

    // Add more colors as needed
)
val sleeptime = listOf<Night>(
    Night("12", "5",3)

)


val hours = arrayListOf<Float>(

)

val jsonString = "{\n" +
        "  \"nights\": [\n" +
        "    {\n" +
        "      \"polar_user\": \"https://www.polaraccesslink/v3/users/1\",\n" +
        "      \"date\": \"2020-01-01\",\n" +
        "      \"sleep_start_time\": \"2020-01-01T00:39:07+03:00\",\n" +
        "      \"sleep_end_time\": \"2020-01-01T09:19:37+03:00\",\n" +
        "      \"device_id\": \"1111AAAA\",\n" +
        "      \"continuity\": 2.1,\n" +
        "      \"continuity_class\": 2,\n" +
        "      \"light_sleep\": 1000,\n" +
        "      \"deep_sleep\": 1000,\n" +
        "      \"rem_sleep\": 1000,\n" +
        "      \"unrecognized_sleep_stage\": 1000,\n" +
        "      \"sleep_score\": 80,\n" +
        "      \"total_interruption_duration\": 1000,\n" +
        "      \"sleep_charge\": 3,\n" +
        "      \"sleep_goal\": 28800,\n" +
        "      \"sleep_rating\": 3,\n" +
        "      \"short_interruption_duration\": 500,\n" +
        "      \"long_interruption_duration\": 300,\n" +
        "      \"sleep_cycles\": 6,\n" +
        "      \"group_duration_score\": 100,\n" +
        "      \"group_solidity_score\": 75,\n" +
        "      \"group_regeneration_score\": 54.2,\n" +
        "      \"hypnogram\": {\n" +
        "        \"00:39\": 2,\n" +
        "        \"00:50\": 3,\n" +
        "        \"01:23\": 6\n" +
        "      },\n" +
        "      \"heart_rate_samples\": {\n" +
        "        \"00:41\": 76,\n" +
        "        \"00:46\": 77,\n" +
        "        \"00:51\": 76\n" +
        "      }\n" +
        "    }\n" +
        "  ]\n" +
        "}"
val gson = Gson()
val nightDataResponse = gson.fromJson(jsonString, NightResponse::class.java)

val firstNight = nightDataResponse.nights.firstOrNull()
val sleepStartTime = firstNight?.sleep_start_time ?: 0
val sleepEndTime = firstNight?.sleep_end_time ?: 0
val sleepGoal = firstNight?.sleep_goal ?: 0


    private val defaultMaxHeight = 200.dp



    @Composable

     fun SleepTimeChart(
        values: List<Float>,
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

                                    for ((index) in sleeptime.withIndex()) {
                                        val newItem = sleeptime[index].sleep_start_time.toFloat()
                                        hours.add(newItem)
                                    }


                                    hours.forEach { item ->
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