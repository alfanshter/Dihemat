package com.dihemat.myapplication.location

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.dihemat.myapplication.HomeActivity
import com.dihemat.myapplication.Session.SessionManager
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService: Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    val TAG = "dinda"
    lateinit var sessionManager: SessionManager
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }





    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        sessionManager = SessionManager(this)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient
            .getLocationUpdates(10000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val lat = location.latitude.toString()
                val long = location.longitude.toString()
                val bearing = location.bearing
                HomeActivity.latitudePosisi = lat
                HomeActivity.longitudePosisi = long

                //kirim location
                Log.d(TAG, "start: $lat")


            }
            .launchIn(serviceScope)



    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    private fun location(){

    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}