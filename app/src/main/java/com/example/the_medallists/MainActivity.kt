package com.example.the_medallists

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById<RecyclerView>(R.id.Country_Medalist_List)
        importCSVFile()
        list.adapter = TheMedalistAdapter { saveData(it) }
        list.layoutManager = LinearLayoutManager(this)
    }

    fun importCSVFile() {
        val file = resources.openRawResource(R.raw.medallists).bufferedReader()
        file.readLine()
        file.forEachLine {
            val temp = it.split(",")
            TheMedalistList.MedalistList.add(
                TheMedalistData(
                    temp[0],
                    temp[1],
                    temp[2].toInt(),
                    temp[3].toInt(),
                    temp[4].toInt(),
                    temp[5].toInt(),
                    temp[3].toInt() + temp[4].toInt() + temp[5].toInt()
                )
            )
        }
        TheMedalistList.count = TheMedalistList.MedalistList.size
        Log.i("Medalist_Count", "${TheMedalistList.count}")
    }

    fun saveData(item: TheMedalistData) {
        val sharedPref =
            this.getSharedPreferences("lastClickMedalist", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("country_name", item.country_name)
            putString("country_code", item.country_code)
            apply()
        }
    }

    private fun medalistIntentAdapter() {
        val intentAdapter = Intent(this, DetailActivity::class.java)
        startActivity(intentAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.lastclicked, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.lastClickedSaveItem -> {
                medalistIntentAdapter()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}