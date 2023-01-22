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
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.StringJoiner


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById<RecyclerView>(R.id.Country_Medalist_List)
        importCSVFile()
        writingtoExistingFile()
        list.adapter = TheMedalistAdapter { saveData(it) }
        list.layoutManager = LinearLayoutManager(this)
    }

    fun writingtoExistingFile() {
        val data = listOf(
            TheMedalistData("ABCDEFG", "ABC", 1, 2, 3, 4, 5)
        )
        try{
            val file =openFileOutput("medallists.csv", MODE_APPEND)
            data.forEach{
                file.bufferedWriter().use{

                    out->out.write("\n${it.country_name},${it.country_code},${it.total_medal},${it.gold},${it.silver},${it.bronze}")
                }
            }
            file.close()

        }catch (e:IOException){
            Log.i("Error:File","Path invalid")

        }
    }


//"${it.country_name},${it.country_code},${it.total_medal},${it.gold},${it.silver},${it.bronze}"


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
        file.close()
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