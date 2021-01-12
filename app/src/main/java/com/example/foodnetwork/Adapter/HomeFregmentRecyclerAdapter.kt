package com.example.foodnetwork.Adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodnetwork.Activites.FoodItemList
import com.example.foodnetwork.R
import com.example.foodnetwork.database.ResDatabase
import com.example.foodnetwork.database.ResEntities
import com.example.foodnetwork.model.Restaurants
import com.squareup.picasso.Picasso

 class HomeFregmentRecyclerAdapter(val context: Context, val itemList:ArrayList<Restaurants>): RecyclerView.Adapter<HomeFregmentRecyclerAdapter.HomeFregmentViewHolder>() {


    override fun onBindViewHolder(holder: HomeFregmentViewHolder, position: Int) {
        val Res = itemList[position]
        holder.ResName.text = Res.resName
        holder.ResRating.text = Res.resRating
        holder.ResPrice.text = Res.resPrice.toString()
        val img = Picasso.get().load(Res.resImg).error(R.drawable.ic_launcher_foreground)
            .into(holder.ResImg);
        holder.ResList.setOnClickListener {
            val intent = Intent(context, FoodItemList::class.java)
            intent.putExtra("id", Res.resId)
            intent.putExtra("resName", Res.resName)
            context.startActivity(intent)
        }

        val listOfFavourites = GetAllFavAsyncTask(context).execute().get()

        if (listOfFavourites.isNotEmpty() && listOfFavourites.contains(Res.resId.toString())) {
            holder.favImage.setImageResource(R.drawable.ic_action_fav_checked)
        } else {
            holder.favImage.setImageResource(R.drawable.ic_action_fav)
        }

        holder.favImage.setOnClickListener {
            val restaurantEntity = ResEntities(
                Res.resId.toInt(),
                Res.resName,
                Res.resRating,
                Res.resPrice,
                Res.resImg
            )

            if (!DBAsyncTask(context, restaurantEntity, 1).execute().get()) {
                val async =
                    DBAsyncTask(context, restaurantEntity, 2).execute()
                val result = async.get()
                if (result) {
                    holder.favImage.setImageResource(R.drawable.ic_action_fav_checked)
                }
            } else {
                val async = DBAsyncTask(context, restaurantEntity, 3).execute()
                val result = async.get()

                if (result) {
                    holder.favImage.setImageResource(R.drawable.ic_action_fav)
                }
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFregmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_restaurant_sample_row, parent, false)

        return HomeFregmentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }


        class HomeFregmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ResImg: ImageView = view.findViewById(R.id.ResImg)
            val ResName: TextView = view.findViewById(R.id.ResName)
            val ResPrice: TextView = view.findViewById(R.id.Resprice)
            val ResRating: TextView = view.findViewById(R.id.rating)
            val ResList: RelativeLayout = view.findViewById(R.id.resList)
            val favImage = view.findViewById(R.id.imgIsFav) as ImageView

        }
        class DBAsyncTask(val context: Context, val resEntity: ResEntities, val mode: Int) :
            AsyncTask<Void, Void, Boolean>() {

            /*
            Mode 1 -> Check DB if the book is favourite or not
            Mode 2 -> Save the book into DB as favourite
            Mode 3 -> Remove the favourite book
            * */

            val db = Room.databaseBuilder(context, ResDatabase::class.java, "Res-db").build()

            override fun doInBackground(vararg params: Void?): Boolean {

                when (mode) {

                    1 -> {

                        // Check DB if the book is favourite or not
                        val Res: ResEntities? = db.resDao().getResById(resEntity.resId.toString())
                        db.close()
                        return Res != null

                    }

                    2 -> {

                        // Save the book into DB as favourite
                        db.resDao().insertRes(resEntity)
                        db.close()
                        return true

                    }

                    3 -> {

                        // Remove the favourite book
                        db.resDao().deleteRes(resEntity)
                        db.close()
                        return true

                    }
                }
                return false
            }
        }

        class GetAllFavAsyncTask(
            context: Context
        ) :
            AsyncTask<Void, Void, List<String>>() {

            val db = Room.databaseBuilder(context, ResDatabase::class.java, "res-db").build()
            override fun doInBackground(vararg params: Void?): List<String> {

                val list = db.resDao().getAllRes()
                val listOfIds = arrayListOf<String>()
                for (i in list) {
                    listOfIds.add(i.resId.toString())
                }
                return listOfIds
            }
        }}


