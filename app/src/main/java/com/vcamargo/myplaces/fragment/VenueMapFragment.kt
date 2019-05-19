package com.vcamargo.myplaces.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.vcamargo.myplaces.R
import com.vcamargo.myplaces.activity.VenueDetailsActivity
import com.vcamargo.myplaces.repository.Resource
import com.vcamargo.myplaces.utilities.CardviewVenueDetails
import com.vcamargo.myplaces.utilities.InjectorUtils
import com.vcamargo.myplaces.viewmodel.VenuesListViewModel
import kotlinx.android.synthetic.main.fragment_venues_map.*
import kotlinx.android.synthetic.main.progress_bar.*
import java.lang.StringBuilder

class VenueMapFragment :
    Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnMarkerClickListener {

    companion object {
        const val REQUEST_CODE_LOCATION = 99
        const val LOG_TAG = "VenueMapFragment"
        const val DEFAULT_ZOOM = 15F
    }

    private lateinit var map: GoogleMap
    private var mFusedLocationProviderClient : FusedLocationProviderClient? = null
    private val mDefaultLocation : LatLng = LatLng(37.5348073, 126.9925633)
    private var mLastKnownLocation: Location? = null
    private var mLocationPermissionGranted = false

    var venuesListViewModel : VenuesListViewModel? = null
    var venueDetails : CardviewVenueDetails? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_venues_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val buttonSearch : Button = view.findViewById(R.id.btn_search_area)
        buttonSearch.let {
            it.setOnClickListener {
                map.clear()

                venuesListViewModel?.lastKnownLocation?.value = map.cameraPosition.target
                btn_search_area.visibility = View.INVISIBLE
            }
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val factory = InjectorUtils.provideVenueDetailViewModelFactory(requireActivity())
        venuesListViewModel = ViewModelProviders.of(requireActivity(), factory)
            .get(VenuesListViewModel::class.java)

        venuesListViewModel?.lastKnownLocation?.observe(requireActivity(), Observer {
            searchForVenues()
        })
    }

    private fun searchForVenues() {
        venuesListViewModel?.venuesSearch()

        venuesListViewModel?.venuesLiveData?.observe(requireActivity(), Observer { data ->
            dismissLoading()
            when (data?.status) {
                Resource.Status.SUCCESS -> {
                    val data = data?.data
                    data?.let { d ->
                        d.forEachIndexed { index, element ->
                            element.latLng?.let {
                                val marker = map.addMarker(
                                    MarkerOptions().position(element.latLng)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                        .title(element.name))
                                marker.tag = index
                            }
                        }
                        venueDetails = CardviewVenueDetails(d, requireActivity()) { venue->
                            launchDetailsActivity(
                                requireActivity(),
                                venue.id)
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    ErrorDialog(
                        {
                            //                            venuesListViewModel?.venuesSearch()
                        },{
                            requireActivity().finish()
                        }).showErrorDialog("Failed to retrieve search results",
                        requireActivity())
                }

                Resource.Status.LOADING -> {
                    showLoading()
                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnCameraMoveListener(this)

        // Prompt the user for permission.
        getLocationPermission()

        if (mLocationPermissionGranted) {
            // Turn on the My Location layer and the related control on the map.
            updateLocationUI()
        }

        // Set a listener for marker click.
        map.setOnMarkerClickListener(this)
    }

    /**
     * Handle markers click
     * **/
    override fun onMarkerClick(p0: Marker?): Boolean {
        val index = p0?.tag as? Int
        p0?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        index?.let {
            venueDetails?.onVenueSelected(it)
            card_view.visibility = View.VISIBLE
        }
        return false
    }

    /**
     * Handle map scrolling/pinning
     * **/
    override fun onCameraMove() {
        btn_search_area.visibility = View.VISIBLE
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                val locationResult = mFusedLocationProviderClient?.lastLocation
                locationResult?.addOnCompleteListener(requireActivity()
                ) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.result
                        mLastKnownLocation?.let { it ->
                            val latlng = LatLng(
                                it.latitude,
                                it.longitude)
                            venuesListViewModel?.lastKnownLocation?.value = latlng
                            map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    latlng, DEFAULT_ZOOM
                                )
                            )
                        }
                    } else {
                        Log.d(LOG_TAG, "Current location is null. Using defaults.")
                        Log.e(LOG_TAG, "Exception: %s", task.exception)
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(mDefaultLocation,
                                DEFAULT_ZOOM
                            ))
                        map.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private fun updateLocationUI() {
        if (map == null) return
        try {
            if (mLocationPermissionGranted) {
                // Set all the settings of the map to match the current state of the checkboxes
                with(map.uiSettings) {
                    isMyLocationButtonEnabled = true
                    isZoomGesturesEnabled = true
                    isScrollGesturesEnabled = true
                    isTiltGesturesEnabled = true
                    isRotateGesturesEnabled = true
                    isMyLocationButtonEnabled = true
                }
                map.isMyLocationEnabled = true

                // Get the current location of the device and set the position of the map.
                    getDeviceLocation()
            } else {
                with(map.uiSettings) {
                    isMyLocationButtonEnabled = true
                    isZoomGesturesEnabled = true
                    isScrollGesturesEnabled = true
                    isTiltGesturesEnabled = true
                    isRotateGesturesEnabled = true
                }
                mLastKnownLocation = null
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        mLocationPermissionGranted = false
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true
                    updateLocationUI()
                }
            }
        }
    }

    private fun launchDetailsActivity(activity: Activity,
                                      venueId: String) {
        val intent = Intent(activity, VenueDetailsActivity::class.java)
        val bundle = Bundle().apply {
            putString(VenueDetailsActivity.KEY_VENUE_ID, venueId)
        }
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }

    private fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun dismissLoading() {
        progress_bar.visibility = View.GONE
    }
}