package com.example.composetest

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.composetest.ui.theme.ComposeTestTheme
import kotlinx.coroutines.*
import net.openid.appauth.*

class MainActivity : ComponentActivity() {
    private lateinit var service: AuthorizationService


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
                            NavigationView(navController = navController, padding = padding)
                        }
                    )


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







