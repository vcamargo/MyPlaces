package com.vcamargo.myplaces.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vcamargo.myplaces.R
import com.vcamargo.myplaces.fragment.VenueMapFragment
import kotlin.collections.forEachIndexed as forEachIndexed1

class VenuesMapActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venues_map)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.venue_map_fragment, VenueMapFragment())
        transaction.commit()
    }
}