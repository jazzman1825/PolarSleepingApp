package com.example.composetest

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONArray
import org.json.JSONObject
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


val jsonString = """
{
  "nights": [
    {
      "polar_user": "https://www.polaraccesslink/v3/users/1",
      "date": "2020-01-01",
      "sleep_start_time": "2020-01-01T00:39:07+03:00",
      "sleep_end_time": "2020-01-01T09:19:37+03:00",
      "device_id": "1111AAAA",
      "continuity": 2.1,
      "continuity_class": 2,
      "light_sleep": 1000,
      "deep_sleep": 1000,
      "rem_sleep": 1000,
      "unrecognized_sleep_stage": 1000,
      "sleep_score": 80,
      "total_interruption_duration": 1000,
      "sleep_charge": 3,
      "sleep_goal": 28800,
      "sleep_rating": 3,
      "short_interruption_duration": 500,
      "long_interruption_duration": 300,
      "sleep_cycles": 6,
      "group_duration_score": 100,
      "group_solidity_score": 75,
      "group_regeneration_score": 54.2,
      "hypnogram": {
        "00:39": 2,
        "00:50": 3,
        "01:23": 6
      },
      "heart_rate_samples": {
        "00:41": 76,
        "00:46": 77,
        "00:51": 76
      }
    },
    {
      "polar_user": "https://www.polaraccesslink/v3/users/2",
      "date": "2020-01-01",
      "sleep_start_time": "2020-01-01T00:39:07+03:00",
      "sleep_end_time": "2020-01-01T05:19:37+03:00",
      "device_id": "1111AAAA",
      "continuity": 2.1,
      "continuity_class": 2,
      "light_sleep": 1000,
      "deep_sleep": 1000,
      "rem_sleep": 1000,
      "unrecognized_sleep_stage": 1000,
      "sleep_score": 80,
      "total_interruption_duration": 1000,
      "sleep_charge": 3,
      "sleep_goal": 30000,
      "sleep_rating": 3,
      "short_interruption_duration": 500,
      "long_interruption_duration": 300,
      "sleep_cycles": 6,
      "group_duration_score": 100,
      "group_solidity_score": 75,
      "group_regeneration_score": 54.2,
      "hypnogram": {
        "00:39": 2,
        "00:50": 3,
        "01:23": 6
      },
      "heart_rate_samples": {
        "00:41": 76,
        "00:46": 77,
        "00:51": 76
      }
    }
  ]
}
""".trimIndent()

val jsonObject = JSONObject(jsonString)
val nights = jsonObject.getJSONArray("nights")
val lastNight = nights.getJSONObject(nights.length() - 1)
val date = lastNight.getString("date")
val sleepGoal = lastNight.getInt("sleep_goal")
val sleepEndTime = lastNight.getString("sleep_end_time")
val sleepStartTime = lastNight.getString("sleep_start_time")
val lightSleep = lastNight.getInt("light_sleep")
val deepSleep  = lastNight.getInt("deep_sleep")
val remSleep = lastNight.getInt("rem_sleep")
val sleepScore = lastNight.getInt("sleep_score")
val sleepCycles = lastNight.getInt("sleep_cycles")

fun calculateSleepTime(start: String, end: String): Duration {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val startTime =
        LocalDateTime.parse(start, formatter).toInstant(ZoneOffset.UTC)
    val endTime = LocalDateTime.parse(end, formatter).toInstant(ZoneOffset.UTC)
    val duration = Duration.between(startTime, endTime)

    return duration
}

val duration = calculateSleepTime(sleepStartTime, sleepEndTime)


@Composable
fun DetailsView() {
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
    val (hours, minutes) = extractHoursAndMinutes(duration)


    val date = LocalDate.parse(date)
    val formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))



    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Today's Sleep Details",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Date: $formattedDate",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp)
                    Text(
                        text = "Total Sleep Time: \n" +
                                "$hours hours $minutes minutes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ){
                Box(
                    modifier = Modifier.padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically){
                            Text("Sleep Goal:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp)
                            Text("$sleepGoal",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = colors.primary
                            )
                        }
                    }
                }
            }

            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ){
                Box(
                    modifier = Modifier.padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(horizontalArrangement = Arrangement.spacedBy(50.dp),
                            verticalAlignment = Alignment.CenterVertically){
                            Text("Light Sleep:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp)
                            Text("$lightSleep ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = colors.primary
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(50.dp),
                            verticalAlignment = Alignment.CenterVertically){
                            Text("REM Sleep:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp)
                            Text("$remSleep ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = colors.primary
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(50.dp),
                            verticalAlignment = Alignment.CenterVertically){
                            Text("Deep Sleep:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp)
                            Text("$deepSleep ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = colors.primary
                            )
                        }

                    }
                }
            }


            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ){
                Box(
                    modifier = Modifier.padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(50.dp),
                            verticalAlignment = Alignment.CenterVertically){
                            Text("Sleep Score:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp)
                            Text("$sleepScore",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = colors.primary
                            )
                        }
                    }
                }
            }

            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ){
                Box(
                    modifier = Modifier.padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(horizontalArrangement = Arrangement.spacedBy(50.dp),
                            verticalAlignment = Alignment.CenterVertically){
                            Text("Sleep Cycles:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp)
                            Text("$sleepCycles",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = colors.primary
                            )
                        }

                    }
                }
            }



        }
    }
}
