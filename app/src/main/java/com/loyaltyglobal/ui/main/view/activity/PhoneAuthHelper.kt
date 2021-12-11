package com.loyaltyglobal.ui.main.view.activity

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

/**
 * Created by Abhin.
 */
object PhoneAuthHelper {

    // create instance of firebase auth
    private lateinit var auth: FirebaseAuth
    var mPhoneAuthInterface: PhoneAuthInterface? = null

    // we will use this to match the sent otp from firebase
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    fun initFirebase() {
        auth = FirebaseAuth.getInstance()
        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("GFG", "onVerificationCompleted Success")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("GFG", "onVerificationFailed  $e")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("GFG", "onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token
                mPhoneAuthInterface?.onVerificationCodeSent(storedVerificationId, resendToken)
            }
        }
    }

    fun getNUmber(activity: Activity, phoneNumber: String) {
        sendVerificationCode(activity, "+91$phoneNumber")
    }

    // this method sends the verification code and starts the callback of verification
    // which is implemented above in onCreate
    private fun sendVerificationCode(activity: Activity, number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("GFG", "Auth started")
    }

    fun signInWithPhoneAuthCredential(otp: String) {
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
            storedVerificationId, otp
        )

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mPhoneAuthInterface?.otpVerificationSuccess()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        mPhoneAuthInterface?.otpVerificationFailed()
                    }
                }
            }
    }

    fun resendVerificationCode(
        activity: Activity,
        phoneNumber: String,
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(resendToken) // ForceResendingToken from callbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    interface PhoneAuthInterface {
        fun otpVerificationSuccess()
        fun otpVerificationFailed()
        fun onVerificationCodeSent(
            verificationId: String,
            resendToken: PhoneAuthProvider.ForceResendingToken
        )
    }
}
