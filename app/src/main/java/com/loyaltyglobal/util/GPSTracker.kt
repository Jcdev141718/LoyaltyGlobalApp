package com.loyaltyglobal.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Created by Abhin.
 */
@SuppressLint("NewApi")
class GPSTracker(private var mContext: Context?) {

    private var latitude = 0.0
    private var longitude = 0.0

    /*Declaring a Location Manager*/
    private var locationManager: LocationManager? = null

    /*flag for GPS status*/
    private var isGPSEnabled = false

    /*flag for network status*/
    private var isNetworkEnabled = false

    /* flag for GPS status*/
    private var canGetLocation = false
    private var location: Location? = null

    private var mLocationListener: LocationListener =
        LocationListener { mLocation -> location = mLocation }

    init {
        getLocation()
    }

    /**
     * provide the current location
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun getLocation(): Location? {
        try {
            locationManager = mContext?.getSystemService(LOCATION_SERVICE) as LocationManager

            // getting GPS status
            locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)?.let {
                isGPSEnabled = it
            }

            // getting network status
            locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)?.let {
                isNetworkEnabled = it
            }

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                canGetLocation = true
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager?.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, Companion.MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                        mLocationListener
                    )
                    if (locationManager != null) {
                        location =
                            locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (location != null) {
                            location?.latitude?.let {
                                latitude = it
                            }
                            location?.longitude?.let {
                                longitude = it
                            }
                            locationManager?.removeUpdates(mLocationListener)
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager?.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, Companion.MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                            mLocationListener
                        )
                        if (locationManager != null) {
                            location =
                                locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (location != null) {
                                location?.latitude?.let {
                                    latitude = it
                                }
                                location?.longitude?.let {
                                    longitude = it
                                }
                                locationManager?.removeUpdates(mLocationListener)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return location
    }

    // get latitude
    fun getLatitude(): Double? {
        return location?.let {
            return it.latitude
        }
    }

    // get longitude
    fun getLongitude(): Double? {
        return location?.let {
            return it.longitude
        }
    }

    companion object {
        const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10
        const val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong()
    }
}