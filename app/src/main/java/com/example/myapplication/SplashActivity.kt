package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        // Use Handler to delay the navigation to the next activity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, signinActivity::class.java))
            finish() // Close splash screen
        }, 3000) // Delay of 3 seconds
    }
}
