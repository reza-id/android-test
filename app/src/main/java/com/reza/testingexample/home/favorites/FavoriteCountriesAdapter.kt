package com.reza.testingexample.home.favorites

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.reza.testingexample.R
import com.reza.testingexample.data.db.FavoriteCountry
import kotlinx.android.synthetic.main.item_country.view.*

class FavoriteCountriesAdapter(private val countries: List<FavoriteCountry>, private val listener: (FavoriteCountry) -> Unit) :
        RecyclerView.Adapter<FavoriteCountriesAdapter.FavoriteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false))
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bindItem(countries[position], listener)
    }

    class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: FavoriteCountry, listener: (FavoriteCountry) -> Unit) {
            itemView.tv_name.text = item.name
            itemView.tv_capital.text = item.capital

            val flagUrl = "https://www.countryflags.io/${item.alpha2Code}/flat/64.png"
            Glide.with(itemView.context)
                    .load(flagUrl)
                    .into(itemView.iv_flag)

            itemView.setOnClickListener { listener(item) }
        }
    }
}