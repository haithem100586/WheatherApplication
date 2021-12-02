package com.android.weatherapplication.feature.weather

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapplication.R
import com.android.weatherapplication.databinding.ItemForecastBinding
import com.android.weatherapplication.domain.model.ListItem
import com.android.weatherapplication.utils.extensions.resIdByName
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * [RecyclerView.Adapter] for the list of forecasts.
 */
class ForecastViewHolder(
    private val binding: ItemForecastBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listItem: ListItem) =
        with(binding) {
            cardView.setCardBackgroundColor(listItem.getColor())
            timeOfDayText.text = listItem.getHourOfDay()
            dayOfWeekText.text = listItem.getDay()

            Glide.with(context)
                .load(context.resIdByName("icon_"+listItem.getWeatherItem()?.icon, "drawable"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .into(forecastIcon)

            tempText.text = listItem.main?.getTempString()
            tempMin.text = listItem.main?.getTempMinString()
            tempMax.text = listItem.main?.getTempMaxString()
        }
}
