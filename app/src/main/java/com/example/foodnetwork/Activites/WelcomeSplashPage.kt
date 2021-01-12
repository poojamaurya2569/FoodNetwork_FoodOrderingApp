package com.example.foodnetwork.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.foodnetwork.R

class WelcomeSplashPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_splash_page)
        Handler().postDelayed({
            startActivity(Intent(this@WelcomeSplashPage, LoginPage::class.java))
            finish()
        },2000)


    }
}
