package com.example.foodnetwork.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodnetwork.R
import com.example.foodnetwork.model.FoodItems
import com.example.foodnetwork.model.Restaurants

class FoodItemListAdapter (val context: Context, val itemList:ArrayList<FoodItems>): RecyclerView.Adapter<FoodItemListAdapter.FoodItemViewHolder>()
{
    class FoodItemViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val foodId : TextView = view.findViewById(R.id.itemNo)
        val foodName : TextView = view.findViewById(R.id.foodName)
        val foodPrice: TextView = view.findViewById(R.id.foodPrice)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item_sample_row,parent,false)
        return FoodItemListAdapter.FoodItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val food = itemList[position]
        holder.foodId.text = food.FoodId
        holder.foodName.text = food.FoodName
        holder.foodPrice.text = food.FoodCost   }

}