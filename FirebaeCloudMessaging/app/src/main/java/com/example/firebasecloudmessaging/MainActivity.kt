package com.example.firebasecloudmessaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(){
            if (!it.isSuccessful){
                Log.w("FCM","Fetching FCM Registration Failed",it.exception)
            }
            val token=it.result
            Log.d("FCM","Token $token")
        }
    }
}