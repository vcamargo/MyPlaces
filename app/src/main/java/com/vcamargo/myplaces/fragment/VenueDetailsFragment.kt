package com.vcamargo.myplaces.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.progress_bar.*

class VenueDetailsFragment : Fragment() {
    companion object {
        const val LOG_TAG = "VenueDetailsFragment"
    }
    var viewModel : VenueDetailsViewModel? = null
    var toolbar : Toolbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_venue_details, container, false)

        // Toolbar customization
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val appCompatActivity = activity as? AppCompatActivity
        appCompatActivity?.setSupportActionBar(toolbar)
        appCompatActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity?.supportActionBar?.setDisplayShowHomeEnabled(true)
        appCompatActivity?.supportActionBar?.setDisplayShowTitleEnabled(false)

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
                dismissLoading()
                when(data.status) {
                    Resource.Status.SUCCESS -> {
                        val details = data.data
                        details?.let {venue->
                            Glide.with(requireContext()).load(venue.photoUrl).into(venue_image)
                            toolbar?.title = venue.name

                            rating_progressbar.progress = (venue.rating * 10)
                            venue_rating.text = venue.rating.toString()

                            txt_venue_price.text = venue.price
                            txt_venue_address.text = venue.formattedAddress
                            txt_venue_categories.text = venue.categories
                            txt_venue_hours.text = venue.hours
                            txt_venue_phone.text = venue.formattedPhone
                        }
                    }
                    Resource.Status.ERROR -> {
                            ErrorDialog(
                                {
                                viewModel?.getVenueDetails(venueId)
                            },{
                                    requireActivity().finish()
                                }).
                                showErrorDialog("Failed to retrieve venue details",
                                requireContext())
                    }

                    Resource.Status.LOADING -> {
                        showLoading()
                    }
                }
            })
        }
    }

    private fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun dismissLoading() {
        progress_bar.visibility = View.GONE
    }
}