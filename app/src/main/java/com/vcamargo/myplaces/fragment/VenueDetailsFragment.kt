package com.vcamargo.myplaces.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.vcamargo.myplaces.R
import com.vcamargo.myplaces.activity.VenueDetailsActivity.Companion.KEY_VENUE_ID
import com.vcamargo.myplaces.repository.Resource
import com.vcamargo.myplaces.utilities.InjectorUtils
import com.vcamargo.myplaces.viewmodel.VenueDetailsViewModel
import kotlinx.android.synthetic.main.fragment_venue_details.*

class VenueDetailsFragment : Fragment() {
    companion object {
        const val LOG_TAG = "VenueDetailsFragment"
    }
    var viewModel : VenueDetailsViewModel? = null
    var toolbar : Toolbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_venue_details, container, false)
        toolbar = view.findViewById(R.id.toolbar) as? Toolbar
        toolbar?.setNavigationIcon(R.drawable.icon_back_arrow)
        toolbar?.setNavigationOnClickListener { activity?.onBackPressed() }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        val venueId = bundle?.getString(KEY_VENUE_ID)
        venueId?.let {
            val factory = InjectorUtils.provideVenueDetailViewModelFactory(requireContext())
            viewModel = ViewModelProviders.of(this, factory)
                .get(VenueDetailsViewModel::class.java)
            viewModel?.getVenueDetails(venueId)
            viewModel?.venueDetailsData?.observe(this, Observer {data ->
                when(data.status) {
                    Resource.Status.SUCCESS -> {
                        val details = data.data
                        details?.let {venue->
                            Log.d(LOG_TAG, venue.photoUrl)
                            Glide.with(requireContext()).load(venue.photoUrl).into(venue_image)
                            toolbar?.title = venue.name
                        }
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}