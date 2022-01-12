package com.loyaltyglobal.ui.main.view.activity

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ActivityMainBinding
import com.loyaltyglobal.notifications.NotificationReceiveListener
import com.loyaltyglobal.notifications.NotificationServiceExtension
import com.loyaltyglobal.ui.base.BaseActivity
import com.loyaltyglobal.ui.main.view.fragment.ExploreFragment
import com.loyaltyglobal.ui.main.view.fragment.HomeScreenFragment
import com.loyaltyglobal.ui.main.view.fragment.ProfileFragment
import com.loyaltyglobal.ui.main.view.fragment.StoriesFragment
import com.loyaltyglobal.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), NotificationReceiveListener {

    lateinit var binding: ActivityMainBinding
    private var marshMellowHelper: MarshMellowHelper? = null
    private var isLocationPermissionGrant = false
    internal var gpsTracker: GPSTracker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadHomeFragment()
        clickListener()
        NotificationServiceExtension.mNotificationReceiveListener = this
        initMapPermission()
    }

    private fun clickListener() {
        binding.menuBottom.setOnItemSelectedListener {
            when (it) {
                R.id.menu_home -> {
                    addReplaceFragment(R.id.fl_main_container, HomeScreenFragment(), addFragment = true, addToBackStack = false)
                }
                R.id.menu_explore -> {
                    addReplaceFragment(R.id.fl_main_container, ExploreFragment(), addFragment = true, addToBackStack = false)
                }
                R.id.menu_stories -> {
                    addReplaceFragment(R.id.fl_main_container, StoriesFragment(), addFragment = true, addToBackStack = false)
                }
                R.id.menu_profile -> {
                    addReplaceFragment(R.id.fl_main_container, ProfileFragment(), addFragment = true, addToBackStack = false)
                }
            }
        }
    }

    private fun loadHomeFragment() {
        binding.menuBottom.setItemSelected(R.id.menu_home)
        addReplaceFragment(R.id.fl_main_container, HomeScreenFragment(), addFragment = true, addToBackStack = false)
    }

    override fun onNotificationReceive(payload: String) {

    }

    fun showHideBottomNavigationBar(isShow: Boolean) {
        if (isShow) {
            binding.menuBottom.show()
        } else {
            binding.menuBottom.hide()
        }
    }

    fun moveToStoriesTab() {
        binding.menuBottom.setItemSelected(R.id.menu_stories)
    }

    fun moveToHomeTab() {
        binding.menuBottom.setItemSelected(R.id.menu_home)
    }

    private fun initMapPermission() {
        marshMellowHelper = MarshMellowHelper(this, locationPermissions, Constants.MAP_PERMISSIONS_REQUEST_CODE)
        marshMellowHelper!!.request(object : MarshMellowHelper.PermissionCallback {
            override fun onPermissionGranted() {
                isLocationPermissionGrant = true
                gpsTracker = GPSTracker(this@MainActivity)
            }

            override fun onPermissionDenied(permissionDeniedError: String) {
                showToast(getString(R.string.permission_denied))
            }

            override fun onPermissionDeniedBySystem(permissionDeniedBySystem: String) {
                showToast(getString(R.string.permission_denied_by_system))
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (marshMellowHelper != null) {
            marshMellowHelper!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LocationRequest.PRIORITY_HIGH_ACCURACY -> when (resultCode) {
                RESULT_OK -> {
                    gpsTracker = GPSTracker(this)
                }
                else -> {
                }
            }
        }
    }


    override fun onResume() {
        if (isLocationPermissionGrant) checkGpsLocationProvider()
        super.onResume()
    }

    // check location service running
    private fun checkGpsLocationProvider() {
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())
        builder.setAlwaysShow(true)
        result.addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)
                gpsTracker = GPSTracker(this)
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvable = exception as ResolvableApiException
                        resolvable.startResolutionForResult(this, LocationRequest.PRIORITY_HIGH_ACCURACY)
                    } catch (e: IntentSender.SendIntentException) {
                    } catch (e: ClassCastException) {
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }
            }
        }
    }
}
