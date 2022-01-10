package com.helloumi.weatherapplication.feature.cities

import androidx.recyclerview.widget.RecyclerView
import com.helloumi.weatherapplication.databinding.ItemCityBinding
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity

/**
 * [RecyclerView.Adapter] for the list of cities.
 */
class CityViewHolder(
    private val binding: ItemCityBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(city: CitiesForSearchEntity, listener: (CitiesForSearchEntity) -> Unit) =
        with(binding) {
            cityName.text = city.name

            root.setOnClickListener {
                listener(city)
            }
        }
}