package com.example.foodnetwork.Activites

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodnetwork.Adapter.FoodItemListAdapter
import com.example.foodnetwork.R
import com.example.foodnetwork.database.ResDatabase
import com.example.foodnetwork.database.ResEntities
import com.example.foodnetwork.model.FoodItems
import com.example.foodnetwork.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject

class FoodItemList : AppCompatActivity() {
lateinit var recyclerView: RecyclerView
    lateinit var processbar:ProgressBar
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FoodItemListAdapter
    lateinit var progresslayout: RelativeLayout
    val foodinfo = arrayListOf<FoodItems>()
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    var resId:String? = "100"
    var resName:String? ="ResName"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_item_list)
        recyclerView = findViewById(R.id.FoodListRecycler)
        processbar = findViewById(R.id.progressbar)
        progresslayout = findViewById(R.id.progressBarLayout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        progresslayout.visibility = View.VISIBLE
        processbar.visibility = View.VISIBLE

        if (intent != null) {
            resId = intent.getStringExtra("id")
            resName = intent.getStringExtra("resName")
        } else {
            finish()
            Toast.makeText(
                this@FoodItemList,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (resId == "100") {
            finish()
            Toast.makeText(
                this@FoodItemList,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }
        supportActionBar?.title = resName

        val queue = Volley.newRequestQueue(this@FoodItemList)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"
        val jsonParmas = JSONObject()
        jsonParmas.put("id", resId)
        if (ConnectionManager().checkConnectivity(this@FoodItemList)) {

            val jsonRequest =
                object : JsonObjectRequest(Request.Method.POST, url, jsonParmas, Response.Listener {
                    try {
                        val success = it.getJSONObject("data").getBoolean("success")

                        if (success) {
                            val data = it.getJSONObject("data").getJSONArray("data")
                            progresslayout.visibility = View.GONE
                            processbar.visibility = View.GONE
                            for (i in 0 until data.length()) {
                                val resJasonObject = data.getJSONObject(i)
                                val resObject = FoodItems(
                                    resJasonObject.getString("id"),
                                    resJasonObject.getString("name"),
                                    resJasonObject.getString("cost_for_one")
                                )
                                foodinfo.add(resObject)
                                recyclerAdapter = FoodItemListAdapter(this@FoodItemList, foodinfo)
                                recyclerView.layoutManager = layoutManager
                                recyclerView.adapter = recyclerAdapter

                            }
                        } else {
                            Toast.makeText(
                                this@FoodItemList,
                                "Some Error Occurred!!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    } catch (e: JSONException) {
                        Toast.makeText(
                            this@FoodItemList,
                            "Some unexpected  error Occurred!!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(this@FoodItemList, "Pleas Try Again !", Toast.LENGTH_LONG).show()

                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "f6c65aeff18abb"
                        return headers
                    }

                }
            queue.add(jsonRequest)
        } else {
            val dialog = AlertDialog.Builder(this@FoodItemList)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection not Found")
            dialog.setPositiveButton("Open Settings") { text, listiner ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
            }
            dialog.setNegativeButton("Exit") { text, listiner ->
                ActivityCompat.finishAffinity(this@FoodItemList)
            }
            dialog.create()
            dialog.show()
        }


    }
    }
