package com.loyaltyglobal.ui.main.view.fragments

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.budiyev.android.codescanner.*
import com.loyaltyglobal.databinding.FragmentQrCodeScannerBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.util.Constants.PERMISSIONS_REQUEST_CODE
import com.loyaltyglobal.util.MarshMellowHelper
import com.loyaltyglobal.util.clickWithDebounce

/**
 * Create by Abhin.
 */
class QrCodeScannerFragment : BaseFragment() {

    private lateinit var binding: FragmentQrCodeScannerBinding
    private lateinit var codeScanner: CodeScanner
    private var marshMellowHelper: MarshMellowHelper? = null
    private var mScannedCode: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQrCodeScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        codeScanner = context?.let { CodeScanner(it, binding.scannerView) }!!
        init()
        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                mScannedCode = it.text
                Log.e("TAG", "Scan --> ${it.text}")
            }
        }
        codeScanner.errorCallback = ErrorCallback {
            Log.e("TAG", "Error --> ${it.message}")
        }
        Handler(Looper.getMainLooper()).postDelayed({
            codeScanner.startPreview()
        }, 500)
    }

    private fun init() {
        initRequestPermission()
        binding.imgClose.clickWithDebounce { activity?.supportFragmentManager?.popBackStack() }
    }

    private fun initRequestPermission() {
        marshMellowHelper = MarshMellowHelper(requireActivity(), arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CODE)
        marshMellowHelper!!.request(object : MarshMellowHelper.PermissionCallback {
            override fun onPermissionGranted() {
                initQrScanner()
            }

            override fun onPermissionDenied(permissionDeniedError: String) {

            }

            override fun onPermissionDeniedBySystem(permissionDeniedBySystem: String) {

            }
        })
    }

    private fun initQrScanner() { // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

    }

    @Suppress("Deprecation")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (marshMellowHelper != null) {
            marshMellowHelper!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }
}


