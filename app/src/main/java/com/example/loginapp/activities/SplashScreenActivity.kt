package com.example.loginapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.loginapp.R

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000 // 3 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler().postDelayed(

            {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            , SPLASH_TIME_OUT)
    }
}