package com.android.weatherapplication.feature.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapplication.databinding.ItemForecastBinding
import com.android.weatherapplication.domain.model.ListItem

/**
 * Implementation of a [RecyclerView.Adapter] that display a list of forecast.
 */
class ForecastAdapter(private val context: Context) : RecyclerView.Adapter<ForecastViewHolder>() {

    var itemsList: MutableList<ListItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    ///////////////////////////////////////////////////////////////////////////
    // Overridden methods
    ///////////////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context))
        return ForecastViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val itemList = itemsList[position]
        holder.bind(itemList)
    }

    override fun getItemCount() = itemsList.size
}
