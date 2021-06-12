package com.mark.arcsinustestapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mark.arcsinustestapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}