package com.mahmoud.todoapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.tiagoaguiar.recyclermasterjava.util.Helper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.mahmoud.todoapp.util.LocationHelper
import com.mahmoud.todoapp.util.LocationManager

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var locationHelper: LocationHelper? = null
    private var lastLocation: Location? = null
    private val M_PERMISSIONS_LOCATION = 99
    private var activity: MainActivity? = null
    private val LocationManager: LocationManager? = null
    private var mapFragment: SupportMapFragment? = null
    private var latLng: LatLng? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val REQUEST_LOCATION_RUNTIME_PERMISSION = 1
    private val REQ_CODE_LOCATION_SELECTION = 1231

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL



      /*  if (LocationUtil.isGPSEnabled(this)) {
            Toast.makeText(this@MapsActivity, "Enable", Toast.LENGTH_LONG).show()



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mMap.isMyLocationEnabled = true
                    fusedLocationProviderClient =
                        LocationServices.getFusedLocationProviderClient(this)

                    fusedLocationProviderClient!!.lastLocation
                        .addOnSuccessListener(this) { location: Location? ->

                            if (location != null) {
                                val cameraPosition = CameraPosition.Builder()
                                    .target(LatLng(location.latitude, location.longitude))
                                    .zoom(15f)
                                    .build()
                                val options = MarkerOptions().position(
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    )
                                ).icon(
                                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                                )
                                val m = mMap.addMarker(options)
                                mMap.animateCamera(
                                    CameraUpdateFactory.newCameraPosition(
                                        cameraPosition
                                    )
                                )
                                latLng = LatLng(location.latitude, location.longitude)


                                mMap.setOnMapClickListener {
                                    val lng = it.longitude
                                    val lat = it.latitude
                                    val marker = mMap.addMarker(MarkerOptions().position(it))
                                    marker.title = "new Title"
                                  /* marker.setIcon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.ic_calendar_menu))*/
                                }
                            }


                        }
                        .addOnFailureListener(this) { }


                } else {
                    LocationUtil.requestLocationPermission(this)
                }
            } else {
                mMap.isMyLocationEnabled = true
            }
        } else
            LocationUtil.openLocationDialog(this)*/
    }

    override fun onResume() {
        super.onResume()
        if (locationHelper != null) {
            locationHelper!!.startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        if (locationHelper != null) {
            locationHelper!!.stopLocationUpdates()
        }
    }

}
