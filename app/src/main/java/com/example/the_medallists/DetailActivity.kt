package com.example.the_medallists

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val sharedPref = this.getSharedPreferences("lastClickMedalist", Context.MODE_PRIVATE)
        val countryName = sharedPref.getString("country_name", "Error(Missing Country Name)")
        val countryCode = sharedPref.getString("country_code", "Error(Missing Country Code)")
        val vName = findViewById<TextView>(R.id.lastClickedMedalist)

        vName.text = getString(R.string.last_Clicked_Message, countryName, countryCode)
    }
}