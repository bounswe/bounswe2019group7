package com.example.app.tradersapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_location_picker.*
import java.util.*
import android.app.Activity
import android.content.Intent



class LocationPickerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var loc:LatLng? = null
    private var city:String? = null
    private var country:String? = null
    private var name:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_picker)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        pickLocationButton.setOnClickListener{
            if(loc == null){
                Toast.makeText(this, "Please select a valid location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent()
            resultIntent.putExtra("city", city)
            resultIntent.putExtra("country", country)
            resultIntent.putExtra("locationX", loc?.longitude)
            resultIntent.putExtra("locationY", loc?.latitude)
            resultIntent.putExtra("name", name)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapClickListener {
            mMap.clear()
            loc = it
            val gcd = Geocoder(this, Locale.ENGLISH)
            val addresses = gcd.getFromLocation(it.latitude, it.longitude, 1)
            if (addresses.size > 0) {
                city = addresses[0].adminArea
                country = addresses[0].countryName
                name =  if(city.isNullOrBlank()) country else "$city, $country"
                mMap.addMarker(MarkerOptions().position(it).title(name)).showInfoWindow()
            }
        }


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0
            )
        } else {
            mMap.isMyLocationEnabled = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            0 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    mMap.isMyLocationEnabled = true
                }
                return
            }
            else -> {
            }
        }
    }
}
