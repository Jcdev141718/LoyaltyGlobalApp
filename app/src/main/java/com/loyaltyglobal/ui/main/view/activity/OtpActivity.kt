package com.loyaltyglobal.ui.main.view.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.PhoneAuthProvider
import com.loyaltyglobal.R


class OtpActivity : AppCompatActivity(), PhoneAuthHelper.PhoneAuthInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        val number = intent.getStringExtra("number")
        PhoneAuthHelper.mPhoneAuthInterface = this
        number?.let { PhoneAuthHelper.getNUmber(this, it) }
        findViewById<Button>(R.id.login).setOnClickListener {
            val otp = findViewById<EditText>(R.id.et_otp).text.trim().toString()
            if (otp.isNotEmpty()) {
                PhoneAuthHelper.signInWithPhoneAuthCredential(otp)
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.login_resend_code).setOnClickListener {
            number?.let { it1 -> PhoneAuthHelper.resendVerificationCode(this, it1) }
        }
    }

    override fun otpVerificationSuccess() {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
    }

    override fun otpVerificationFailed() {
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
    }

    override fun onVerificationCodeSent(
        verificationId: String,
        resendToken: PhoneAuthProvider.ForceResendingToken
    ) {
    }
}
