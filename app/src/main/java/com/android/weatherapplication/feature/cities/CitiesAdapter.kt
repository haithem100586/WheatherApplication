package com.android.weatherapplication.feature.cities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapplication.databinding.ItemCityBinding
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity

/**
 * Implementation of a [RecyclerView.Adapter] that display a list of cities items.
 */
class CitiesAdapter(
    private val listener: (CitiesForSearchEntity) -> Unit
) : RecyclerView.Adapter<CityViewHolder>() {

    var citiesList: MutableList<CitiesForSearchEntity> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    ///////////////////////////////////////////////////////////////////////////
    // Overridden methods
    ///////////////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context))
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = citiesList[position]
        holder.bind(city, listener)
    }

    override fun getItemCount() = citiesList.size

    ///////////////////////////////////////////////////////////////////////////
    // Public methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Get the city item at the given position.
     *
     * @param position the item position in the adapter.
     */
    fun getItem(position: Int) = citiesList[position]

    /**
     * Remove the city item at the given position.
     *
     * @param position the item position in the adapter.
     */
    fun removeAt(position: Int) {
        citiesList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}
