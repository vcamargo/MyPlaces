package com.vcamargo.myplaces.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vcamargo.myplaces.R
import com.vcamargo.myplaces.fragment.VenueDetailsFragment

class VenueDetailsActivity : AppCompatActivity() {

    companion object {
        const val KEY_VENUE_ID = "VENUE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_details)

        val bundle = intent.extras
        val venueId = with(bundle) { getString(KEY_VENUE_ID) }

        val newFragment = VenueDetailsFragment()
        val args = Bundle()
        args.putString(KEY_VENUE_ID, venueId)
        newFragment.arguments = args

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.venue_details_fragment, newFragment)
        transaction.commit()
    }
}