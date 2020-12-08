package com.example.myapplication

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.myapplication.api.EndPoints
import com.example.myapplication.api.Problem
import com.example.myapplication.api.ServiceBuilder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.net.CacheResponse

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var problems: List<Problem>

    // add to implement last known location
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        // call the service and add markers
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getProblem()
        var position: LatLng

        call.enqueue(object : retrofit2.Callback<List<Problem>> {
            override fun onResponse(call: Call<List<Problem>>, response: Response<List<Problem>>) {
                if (response.isSuccessful){
                    problems=response.body()!!
                    for (problem in problems) {
                        position = LatLng(problem.lat.toString().toDouble(),
                                problem.lng.toString().toDouble())
                        mMap.addMarker(MarkerOptions().position(position).title(problem.description))
                    }
                }
            }

            override fun onFailure(call: Call<List<Problem>>, t:Throwable){
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        // initiate fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setupMap()
        // Add a marker in Sydney and move the camera
       //  val sydney = LatLng(-34.0, 151.0)
      //   mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    fun setupMap(){
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return
        } else {
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if(location!=null) {
                    lastLocation=location
                    Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_SHORT).show()
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }
    companion object {
        // add to implement last known location
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        //added to implement location periodic updates
        private const val REQUEST_CHECK_SETTINGS = 2
    }
}

