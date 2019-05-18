package com.vcamargo.myplaces.utilities

import android.app.Activity
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.vcamargo.myplaces.R
import com.vcamargo.myplaces.model.VenueBasicDetails
import java.lang.StringBuilder

class CardviewVenueDetails(
    val venuesList: List<VenueBasicDetails>,
    val activity: Activity,
    val onClick: (VenueBasicDetails) -> Unit) {

    fun onVenueSelected(index : Int) {
        val venue = venuesList[index]

        val venueName = activity.findViewById<TextView>(R.id.venue_name)
        venueName.text = venue.name

        val venueCategory = activity.findViewById<TextView>(R.id.venue_type)
        venueCategory.text = venue.categoryName

        val distance = StringBuilder()
        distance.append(venue.distance)
        distance.append(" m ")
        distance.append(venue.address)

        val venueAddress = activity.findViewById<TextView>(R.id.venue_location)
        venueAddress.text = distance.toString()

        val venueIcon = activity.findViewById<ImageView>(R.id.img_venue_type)

        Glide.with(activity).load(venue.categoryImage).into(venueIcon)

        val cardview = activity.findViewById<CardView>(R.id.card_view)

        cardview.setOnClickListener {
            onClick(venue)
        }
    }
}
