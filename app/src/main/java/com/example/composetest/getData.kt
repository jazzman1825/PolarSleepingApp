package com.example.composetest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

suspend fun getSleep(token: String): String = withContext(Dispatchers.IO) {
    val obj = URL("https://www.polaraccesslink.com/v3/users/sleep")
    val connection = obj.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.setRequestProperty("Authorization", "Bearer $token")
    val responseCode = connection.responseCode
    val input = BufferedReader(InputStreamReader(connection.inputStream))
    val response = StringBuffer()
    var inputLine: String?
    while (input.readLine().also { inputLine = it } != null) {
        response.append(inputLine)
    }
    input.close()
    return@withContext response.toString()
}