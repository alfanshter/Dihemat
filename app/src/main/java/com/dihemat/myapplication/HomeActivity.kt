package com.dihemat.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.databinding.ActivityHomeBinding
import com.dihemat.myapplication.location.LocationService
import com.dihemat.myapplication.ui.dashboard.DashboardFragment
import com.dihemat.myapplication.ui.pesanan.PesananFragment
import com.dihemat.myapplication.ui.profil.ProfilFragment
import com.google.android.gms.location.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    //lokasi
    var mLocationRequest: LocationRequest? = null
    var mLastLocation: Location? = null
    var mFusedLocationClient: FusedLocationProviderClient? = null

    //loading
    lateinit var progressdialog : ProgressDialog
    companion object {
        var latitudePosisi: String? = null
        var longitudePosisi: String? = null
        const val REQUEST_CHECK_SETTINGS = 101
        lateinit var activity : Activity
    }

            private val onNavigationItemSelectedListener =
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.navigation_dashboard -> {
                            supportFragmentManager.beginTransaction().replace(
                                R.id.framehome,
                                DashboardFragment()
                            ).commit()
                            return@OnNavigationItemSelectedListener true
                        }
                        R.id.navigation_pesanan -> {
                            supportFragmentManager.beginTransaction().replace(
                                R.id.framehome,
                                PesananFragment()
                            ).commit()
                            return@OnNavigationItemSelectedListener true

                        }
                        R.id.navigation_person -> {
                            supportFragmentManager.beginTransaction().replace(
                                R.id.framehome,
                                ProfilFragment()
                            ).commit()
                            return@OnNavigationItemSelectedListener true

                        }


                    }

            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        progressdialog = ProgressDialog(this)
        //lokasi
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        permission()
        val navView: BottomNavigationView = findViewById(R.id.nav_viewhome)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(DashboardFragment())


    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.framehome, fragment)
        fragmentTrans.commit()
    }


    //lokasi
    @SuppressLint("MissingPermission")
    private fun permission() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 5000 // two minute interval

        mLocationRequest!!.fastestInterval = 3000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                //Location Permission already granted
                mFusedLocationClient!!.requestLocationUpdates(
                    mLocationRequest!!, mLocationCallback,
                    Looper.myLooper()
                )
            } else {
                //Request Location Permission
                checkLocationPermission()
            }
        } else {

            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest!!, mLocationCallback,
                Looper.myLooper()
            )
        }
    }


    var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.size > 0) {
                //The last location in the list is the newest
                val location = locationList[locationList.size - 1]
                mLastLocation = location

                latitudePosisi = mLastLocation!!.latitude.toString()
                longitudePosisi = mLastLocation!!.longitude.toString()
                if (latitudePosisi!=null){
                    try {
                        loading(false)
                    }catch (e :Exception){
                        return loading(false)
                    }
                }

                //move map camera

            }
        }
    }


    val MY_PERMISSIONS_REQUEST_LOCATION = 99
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { dialogInterface, i -> //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        mFusedLocationClient!!.requestLocationUpdates(
                            mLocationRequest!!,
                            mLocationCallback, Looper.myLooper()
                        )
                    }
                } else {
                    // if not allow a permission, the application will exit
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                    System.exit(0)
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                Log.d("uyab", "User agreed to make required location settings changes.")
            }
            else -> {
                Log.d("uyab", "User chose not to make required location settings changes.")
            }
        }
    }

    override fun onPause() {
        Intent(this, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            startService(this)
        }
        super.onPause()
    }


    override fun onResume() {
        super.onResume()
        Intent(this, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
        //lokasi
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


    }

    override fun onDestroy() {
        Intent(this, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            startService(this)
        }
        super.onDestroy()
        finish()
    }


    fun loading(status: Boolean) {
        if (status) {
            progressdialog.setTitle(Constant.tunggu)
            progressdialog.setCanceledOnTouchOutside(false)
            progressdialog.show()
        } else {
            progressdialog.dismiss()
        }

    }

}