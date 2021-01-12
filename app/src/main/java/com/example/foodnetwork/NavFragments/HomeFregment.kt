package com.example.foodnetwork.NavFragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodnetwork.Adapter.HomeFregmentRecyclerAdapter

import com.example.foodnetwork.R
import com.example.foodnetwork.database.ResDatabase
import com.example.foodnetwork.database.ResEntities
import com.example.foodnetwork.model.Restaurants
import com.example.foodnetwork.util.ConnectionManager
import org.json.JSONException


class HomeFregment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    val ResInfo = arrayListOf<Restaurants>()
    lateinit var recyclerAdapter: HomeFregmentRecyclerAdapter
    lateinit var progressbarlayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val view =  inflater.inflate(R.layout.fragment_home_fregment, container, false)

        recyclerView = view.findViewById(R.id.HomeFregmentRecycler)
        layoutManager = LinearLayoutManager(activity)
        progressBar = view.findViewById(R.id.progressbar)
        progressbarlayout = view.findViewById(R.id.progressBarLayout)
        progressbarlayout.visibility = View.VISIBLE


        val queue = Volley.newRequestQueue(activity as Context )
       val  url = "http://13.235.250.119/v2/restaurants/fetch_result/"
        if (ConnectionManager().checkConnectivity(activity as Context)){
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

               try{
                   progressbarlayout.visibility = View.GONE
                val success = it.getJSONObject("data").getBoolean("success")
                if (success) {
                    val data = it.getJSONObject("data").getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val resJasonObject = data.getJSONObject(i)
                        val resObject = Restaurants(
                            resJasonObject.getString("id"),
                            resJasonObject.getString("name"),
                            resJasonObject.getString("rating"),
                            resJasonObject.getString("cost_for_one"),
                            resJasonObject.getString("image_url")

                        )
                        ResInfo.add(resObject)
                        recyclerAdapter = HomeFregmentRecyclerAdapter(activity as Context, ResInfo)
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = recyclerAdapter

                    }
                } else {
                    Toast.makeText(context, "Some Error Occurred!!", Toast.LENGTH_LONG).show()
                }
            }catch(e:JSONException){
                Toast.makeText(context, "Some unexpected  error Occurred!!", Toast.LENGTH_LONG).show()
            }

            },Response.ErrorListener {
                Toast.makeText(context, "Pleas Try Again !", Toast.LENGTH_LONG).show()
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "f6c65aeff18abb"
                    return headers
                }

            }
            queue.add(jsonObjectRequest)
        }
        else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection not Found")
            dialog.setPositiveButton("Open Settings"){text,listiner->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){text,listiner->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

        return view

    }

}
