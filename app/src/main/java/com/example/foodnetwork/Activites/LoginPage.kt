package com.example.foodnetwork.Activites

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.foodnetwork.R
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var email : EditText
    lateinit var Password: EditText
    lateinit var ForgotPassword: TextView
    lateinit var Signup: TextView
    lateinit var Login: Button
    var Email = "abc@gmail.com"
    var password = "abc"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false)
        setContentView(R.layout.activity_login_page)
        if (isLoggedIn) {
            val intent = Intent(this@LoginPage, MainActivity::class.java)
            startActivity(intent)
        }
            email = findViewById(R.id.email)
            Password = findViewById(R.id.password)
            ForgotPassword = findViewById(R.id.forgotPassword)
            Signup = findViewById(R.id.signup)
            Login = findViewById(R.id.login)

            Login.setOnClickListener {

                Email = email.text.toString()
                password = Password.text.toString()
                userPreferences(Email,password)
                val intent = Intent(this@LoginPage,MainActivity::class.java)
                startActivity(intent)
            }
        signup.setOnClickListener {
            val intent = Intent(this@LoginPage,RegistrationPage::class.java)
            startActivity(intent)
        }

        forgotPassword.setOnClickListener {
            val intent = Intent(this@LoginPage,ForgotPage::class.java)
            startActivity(intent)
        }


    }
    fun userPreferences(email:String,password:String)
    {
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()
        sharedPreferences.edit().putString("email",email).apply()
        sharedPreferences.edit().putString("password",password).apply()
    }

    override fun onPause() {
        super.onPause()
        finish()}


}
