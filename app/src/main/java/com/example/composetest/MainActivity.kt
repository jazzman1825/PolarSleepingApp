package com.example.composetest

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.composetest.ui.theme.ComposeTestTheme
import kotlinx.coroutines.*
import net.openid.appauth.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL



class MainActivity : ComponentActivity() {
    private lateinit var service: AuthorizationService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = AuthorizationService(this)
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        Button(onClick = {auth()}) {
                            Text("auth")
                        }
                        Button(onClick = {
                            val sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                            val scope = MainScope()
                            val token = sharedPref.getString("token", "default_value")
                            scope.launch {
                                val sleepData = getSleep(token!!)
                                Log.d("sleep", sleepData)
                            }

                        }) {
                            Text("sleep")
                        }
                    }

                }
            }
        }
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {
            val ex = AuthorizationException.fromIntent(it.data!!)
            val result = AuthorizationResponse.fromIntent(it.data!!)
            if (ex != null){
                Log.e("Auth", "launcher: $ex")
            } else {
                val secret = ClientSecretBasic(CLIENT_SECRET)
                val tokenRequest = result?.createTokenExchangeRequest()

                service.performTokenRequest(tokenRequest!!, secret) {res, exception ->
                    if (exception != null){
                        Log.e("Auth", "launcher: ${exception.error}" )
                    } else {
                        val token = res?.accessToken
                        if (token != null) {
                            Log.d("token", token)
                        }
                        val sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                        val editor = sharedPref.edit()
                        editor.putString("token", token)
                        editor.apply()
                    }
                }
            }
        }
    }

    private fun auth() {
        val redirectUri = Uri.parse("com.din2polar.app:/oauth2_callback")
        val authorizeUri = Uri.parse("https://flow.polar.com/oauth2/authorization")
        val tokenUri = Uri.parse("https://polarremote.com/v2/oauth2/token")

        val config = AuthorizationServiceConfiguration(authorizeUri, tokenUri)
        val request = AuthorizationRequest
            .Builder(config, CLIENT_ID, ResponseTypeValues.CODE, redirectUri)
            .setScopes("accesslink.read_all")
            .build()

        val intent = service.getAuthorizationRequestIntent(request)
        launcher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        service.dispose()
    }
}

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
