package com.example.chatappcompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauthcompose.LoginScreen
import com.example.firebaseauthcompose.RegisterScreen
import com.example.firebaseauthcompose.ui.theme.FirebaseAuthComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseAuthComposeTheme {
                val navController= rememberNavController()
                val context= LocalContext.current
                val isLogin= isLogin(context)
                NavHost(navController, startDestination = if (isLogin) "Home" else "Login"){
                    composable("Home"){

                    }
                    composable("Login"){
                        LoginScreen(navController)
                    }
                    composable("Register"){
                        RegisterScreen(navController)
                    }
                }
            }
        }
    }
}

fun isLogin(context: Context):Boolean{
    val sharedPreferences=context.getSharedPreferences("Login_Pref",Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isLogin",false)
}