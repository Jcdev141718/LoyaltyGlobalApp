package com.loyaltyglobal.ui.main.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.loyaltyglobal.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_otp).setOnClickListener {
            val number = findViewById<EditText>(R.id.et_phone_number).text.trim().toString()
            val intent = Intent(applicationContext, OtpActivity::class.java)
            intent.putExtra("number", number)
            startActivity(intent)
        }
    }
}
