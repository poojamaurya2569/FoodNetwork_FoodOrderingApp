package com.example.foodnetwork.Activites

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.foodnetwork.NavFragments.MyProfile
import com.example.foodnetwork.R
import kotlinx.android.synthetic.main.activity_registration_page.*

class RegistrationPage : AppCompatActivity() {
    lateinit var name : EditText
    lateinit var EmailAddress : EditText
    lateinit var PhoneNo: EditText
    lateinit var DeliveryAdress : EditText
    lateinit var passward: EditText
    lateinit var confirmpassward: EditText
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var Regester: Button
    lateinit var Name:String
    lateinit var email:String
    lateinit var phoneNo:String
    lateinit var deliveryAddress:String
    lateinit var Spassword:String
    lateinit var Sconpassword:String
    lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false)
        setContentView(R.layout.activity_registration_page)
        if (isLoggedIn){
            val intent = Intent(this@RegistrationPage,MainActivity::class.java)
            startActivity(intent)
        }

        name = findViewById(R.id.name)
        EmailAddress = findViewById(R.id.email)
        PhoneNo = findViewById(R.id.mobileNo)
        DeliveryAdress = findViewById(R.id.deliveryAddress)
        passward = findViewById(R.id.password)
        Regester = findViewById(R.id.register)
        confirmpassward = findViewById(R.id.passconfirmf)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Regisrer YourSelf"





        Regester.setOnClickListener {
                Name = name.text.toString()
                email= EmailAddress.text.toString()
                phoneNo = PhoneNo.text.toString()
                deliveryAddress = DeliveryAdress.text.toString()
                Spassword = passward.text.toString()
                Sconpassword = confirmpassward.text.toString()


                userPreferences(phoneNo,email,Name,deliveryAddress)
                val i = Intent(this@RegistrationPage,MyProfile::class.java)
                val intent = Intent(this@RegistrationPage,MainActivity::class.java)
                if(Spassword != Sconpassword) {
                    Toast.makeText(this@RegistrationPage,"Password Don't match!",Toast.LENGTH_SHORT).show()
                }
                else{
                 startActivity(intent)
            }}





    }
    fun userPreferences(phoneNo:String,email:String,name:String,Address:String)
    {
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()
        sharedPreferences.edit().putString("email",email).apply()
        sharedPreferences.edit().putString("phoneNo",phoneNo).apply()
        sharedPreferences.edit().putString("name",name).apply()
        sharedPreferences.edit().putString("Address",Address).apply()

    }

    override fun onPause() {
        super.onPause()
        finish()

    }
}


