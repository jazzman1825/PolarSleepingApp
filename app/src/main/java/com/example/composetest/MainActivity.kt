package com.example.composetest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.composetest.ui.theme.ComposeTestTheme
import net.openid.appauth.*



class MainActivity : ComponentActivity() {
    private lateinit var service: AuthorizationService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = AuthorizationService(this)
        Log.d("log","test2")
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Button(onClick = { auth() }) {
                        Text(text = "auth")
                    }
                }
            }
        }
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d("log","test1")
        if (it.resultCode == RESULT_OK) {
            val ex = AuthorizationException.fromIntent(it.data!!)
            val result = AuthorizationResponse.fromIntent(it.data!!)
            Log.d("log","test")
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
                        // viewmodel.setToken(token!!)


                       // val intent = Intent(this, GithubActivity::class.java)
                       // startActivity(intent)
                       // finish()
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
            .setScopes()
            .build()

        val intent = service.getAuthorizationRequestIntent(request)
        launcher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        service.dispose()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestTheme {
        Greeting("Android")
    }
}