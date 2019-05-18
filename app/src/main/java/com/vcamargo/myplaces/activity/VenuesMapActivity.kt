package com.vcamargo.myplaces.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
import com.vcamargo.myplaces.activity.VenueDetailsActivity.Companion.KEY_VENUE_ID
import com.vcamargo.myplaces.repository.Resource
import com.vcamargo.myplaces.utilities.CardviewVenueDetails
import com.vcamargo.myplaces.utilities.InjectorUtils
import com.vcamargo.myplaces.viewmodel.VenuesListViewModel
import kotlinx.android.synthetic.main.activity_venues_map.*
import java.lang.StringBuilder

class VenuesMapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraMoveListener,
    GoogleMap.OnMarkerClickListener {

    companion object {
        const val REQUEST_CODE_LOCATION = 99
        const val LOG_TAG = "VenuesMapActivity"
        const val DEFAULT_ZOOM = 15F
    }

    private lateinit var map: GoogleMap
    private var mFusedLocationProviderClient : FusedLocationProviderClient? = null
    private val mDefaultLocation : LatLng = LatLng(-23.6815315, -46.8754902)
    private var mLastKnownLocation: Location? = null
    private var mLocationPermissionGranted = false

    var venuesListViewModel : VenuesListViewModel? = null
    var venueDetails : CardviewVenueDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venues_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btn_search_area.setOnClickListener {
            map.clear()

            val cameraPosition = map.cameraPosition.target
            searchForVenues(cameraPosition)
            btn_search_area.visibility = View.INVISIBLE
        }

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
                locationResult?.addOnCompleteListener(this
                ) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.result
                        mLastKnownLocation?.let { it ->
                            val latlng = LatLng(
                                it.latitude,
                                it.longitude)
                            map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    latlng, DEFAULT_ZOOM
                                )
                            )
                            searchForVenues(latlng)
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
        if (map == null) {
            return
        }
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
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this,
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

    private fun searchForVenues(latLng: LatLng) {
        val factory = InjectorUtils.provideVenueDetailViewModelFactory(applicationContext)
        venuesListViewModel = ViewModelProviders.of(this, factory)
            .get(VenuesListViewModel::class.java)
        val latlng = StringBuilder()
        latlng.append(latLng.latitude)
        latlng.append(",")
        latlng.append(latLng.longitude)
        venuesListViewModel?.venuesSearch(latlng.toString())
        venuesListViewModel?.venuesLiveData?.observe(this, Observer { data ->
            when (data?.status) {
                Resource.Status.SUCCESS -> {
                    val data = data?.data
                    data?.let {
                        it.forEachIndexed { index, element ->
                            val marker = map.addMarker(
                                MarkerOptions().
                                    position(element.latLng)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                    .title(element.name))
                            marker.tag = index
                        }
                        venueDetails = CardviewVenueDetails(it, this) { venue->
                            launchDetailsActivity(
                                    this,
                                    venue.id)
                        }
                    }
                }
            }
        })
    }

    private fun launchDetailsActivity(activity: AppCompatActivity,
                                      venueId: String) {
        val intent = Intent(activity, VenueDetailsActivity::class.java)
        val bundle = Bundle().apply {
            putString(KEY_VENUE_ID, venueId)
        }
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }
}