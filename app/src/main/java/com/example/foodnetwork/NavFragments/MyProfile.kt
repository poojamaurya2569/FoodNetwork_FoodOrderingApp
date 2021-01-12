package com.example.foodnetwork.NavFragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout

import com.example.foodnetwork.R

class MyProfile : Fragment() {
    lateinit var name : TextView
    lateinit var EmailAddress : TextView
    lateinit var PhoneNo: TextView
    lateinit var DeliveryAdress : TextView
    lateinit var Name:String
    lateinit var email:String
    lateinit var phoneNo:String
    lateinit var deliveryAddress:String
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        sharedPreferences = activity?.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)!!

        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)
        name = view.findViewById(R.id.name)
        EmailAddress = view.findViewById(R.id.email)
        PhoneNo = view.findViewById(R.id.mobileNo)
        DeliveryAdress = view.findViewById(R.id.deliveryAddress)
        Name = sharedPreferences.getString("name", "Name") as String
        email = sharedPreferences.getString("email","abc@gmail.com") as String
        phoneNo = sharedPreferences.getString("phoneNo", "1111222233") as String
        deliveryAddress = sharedPreferences.getString("Address","On earth") as String
        name.text = Name
        EmailAddress.text = email
        PhoneNo.text = phoneNo
        DeliveryAdress.text = deliveryAddress
        return view


    }

}
