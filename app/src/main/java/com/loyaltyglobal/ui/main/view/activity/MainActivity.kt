package com.loyaltyglobal.ui.main.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loyaltyglobal.R
import com.loyaltyglobal.ui.main.view.fragment.StoriesFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val myFragment= StoriesFragment()
       supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,myFragment).addToBackStack(null).commit()

    }
}
