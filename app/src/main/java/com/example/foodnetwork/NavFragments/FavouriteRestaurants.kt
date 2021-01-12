package com.example.foodnetwork.NavFragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodnetwork.Adapter.HomeFregmentRecyclerAdapter

import com.example.foodnetwork.R
import com.example.foodnetwork.database.ResDatabase
import com.example.foodnetwork.database.ResEntities
import com.example.foodnetwork.model.Restaurants


class FavouriteRestaurants : Fragment() {
    private lateinit var recyclerRestaurant: RecyclerView
    private lateinit var homeFregmentRecyclerAdapter: HomeFregmentRecyclerAdapter
    private var restaurantList = arrayListOf<Restaurants>()
    private lateinit var rlLoading: RelativeLayout
    private lateinit var rlFav: RelativeLayout
    private lateinit var rlNoFav: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_favourite_restaurants, container, false)
        rlFav = view.findViewById(R.id.rlFavorites)
        rlNoFav = view.findViewById(R.id.rlNoFavorites)
        rlLoading = view.findViewById(R.id.rlLoading)
        rlLoading.visibility = View.VISIBLE
        setUpRecycler(view)
        return view
    }

    private fun setUpRecycler(view: View) {
        recyclerRestaurant = view.findViewById(R.id.recyclerRestaurants)


        val backgroundList = FavouritesAsync(activity as Context).execute().get()
        if (backgroundList.isEmpty()) {
            rlLoading.visibility = View.GONE
            rlFav.visibility = View.GONE
            rlNoFav.visibility = View.VISIBLE
        } else {
            rlFav.visibility = View.VISIBLE
            rlLoading.visibility = View.GONE
            rlNoFav.visibility = View.GONE
            for (i in backgroundList) {
                restaurantList.add(
                    Restaurants(
                        i.resId.toString(),
                        i.resName,
                        i.resRating,
                        i.resPrice.toString(),
                        i.resImg
                    )
                )
            }

            homeFregmentRecyclerAdapter = HomeFregmentRecyclerAdapter( activity as Context,restaurantList)
            val mLayoutManager = LinearLayoutManager(activity)
            recyclerRestaurant.layoutManager = mLayoutManager
            recyclerRestaurant.itemAnimator = DefaultItemAnimator()
            recyclerRestaurant.adapter = homeFregmentRecyclerAdapter
            recyclerRestaurant.setHasFixedSize(true)
        }

    }


    class FavouritesAsync(context: Context) : AsyncTask<Void, Void, List<ResEntities>>() {

        val db = Room.databaseBuilder(context, ResDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): List<ResEntities> {

            return db.resDao().getAllRes()
        }

    }


}
