package com.mahmoud.todoapp

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mahmoud.todoapp.adapter.ContactDetailsAdapter
import com.mahmoud.todoapp.model.Contact
import com.mahmoud.todoapp.util.LocationHelper
import com.mahmoud.todoapp.util.LocationManager
import kotlinx.android.synthetic.main.activity_details_events.*


class DetailsEventsActivity : AppCompatActivity(), OnMapReadyCallback {
    val TAG = "DetailsEventsActivity"
    private lateinit var mMap: GoogleMap
    private lateinit var locationHelper: LocationHelper
    private var contactsAdapter: ContactDetailsAdapter? = null
    private var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_events)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        locationHelper = LocationHelper(this, object : LocationManager {

            override fun onLocationChanged(location: Location?) {
                if (location != null)
                    this@DetailsEventsActivity.location = location

                Log.e(TAG, "onLocationChanged latitude: ${location?.latitude}")
                Log.e(TAG, "onLocationChanged longitude: ${location?.longitude}")


            }

            override fun getLastKnownLocation(location: Location?) {
                if (location != null) {
                    this@DetailsEventsActivity.location = location

                    Log.e(TAG, "getLastKnownLocation latitude: ${location.latitude}")
                    Log.e(TAG, "getLastKnownLocation longitude: ${location.longitude}")


                    val cameraPosition = CameraPosition.Builder()
                        .target(LatLng(location.latitude, location.longitude))
                        .zoom(17f)
                        .build()
                    val options = MarkerOptions().position(
                        LatLng(location.latitude, location.longitude)
                    ).icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )
                    val m = mMap.addMarker(options)
                    mMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                            cameraPosition
                        )
                    )
                }
            }

        })


        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapEventDetails) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        cardEventDetails.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        val data = ArrayList<Contact>()
        data.add(Contact("Mahmoud", "0597796100", false))
        data.add(Contact("Ahmad", "059999999", false))
        data.add(Contact("Sami", "059888888", false))


        initRecycleView(data)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (locationHelper.checkLocationPermissions())
            mMap.isMyLocationEnabled = true



    }

    private fun initRecycleView(list: ArrayList<Contact>) {

        rvContacts.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            contactsAdapter = ContactDetailsAdapter(list)
            adapter = contactsAdapter
        }


    }

    override fun onResume() {
        super.onResume()
        if (locationHelper.checkMapServices()) {
            if (locationHelper.checkLocationPermissions()) {

            }
        }
        locationHelper.startLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        locationHelper.stopLocationUpdates()
    }


}
