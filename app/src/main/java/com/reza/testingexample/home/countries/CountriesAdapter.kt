package com.reza.testingexample.home.countries

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.reza.testingexample.R
import com.reza.testingexample.data.model.Country
import kotlinx.android.synthetic.main.item_country.view.*

class CountriesAdapter(private val countries: List<Country>, private val listener: (Country) -> Unit) :
    RecyclerView.Adapter<CountriesAdapter.CountryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        return CountryHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false))
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.bindItem(countries[position], listener)
    }

    class CountryHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(country: Country, listener: (Country) -> Unit) {
            itemView.tv_name.text = country.name
            itemView.tv_capital.text = country.capital
            Glide.with(itemView.context)
                .load("https://www.countryflags.io/${country.alpha2Code}/flat/64.png")
                .into(itemView.iv_flag)

            itemView.setOnClickListener { listener(country) }
        }
    }
}