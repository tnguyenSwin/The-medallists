package com.example.the_medallists


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class TheMedalistAdapter(private val listener: (TheMedalistData) -> Unit) :
    RecyclerView.Adapter<TheMedalistAdapter.ViewHolder>() {
    //[Function] This specify the ViewHolder Function and create new View
    val checkerTopTen = generateTopTenList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.medalist_row_layout, parent, false) as View
        return ViewHolder(view)
    }

    //Determine number of item to be shown
    override fun getItemCount(): Int = TheMedalistList.count

    //onBindViewHolder to update the ViewHolder contents with the item with private fields
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = TheMedalistList.MedalistList[position]
        holder.bind(item)
    }

    //Respective UI elements in medalist_row_layout
    inner class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {

        val countryName: TextView = v.findViewById(R.id.country_name_TextView)
        val countryCode: TextView = v.findViewById(R.id.country_code_TextView)
        val totalMedal: TextView = v.findViewById(R.id.total_medal_TextView)
        val backgroundLayout: ConstraintLayout = v.findViewById(R.id.country_container_layout)

        //Correcting the information in layout for each ViewHolder
        fun bind(item: TheMedalistData) {

            countryName.text = item.country_name
            countryCode.text = item.country_code
            totalMedal.text = item.total_medal.toString()
            if (item.country_code in checkerTopTen) {
                backgroundLayout.setBackgroundColor(Color.parseColor("#FFD700"))
            } else {
                backgroundLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
            }

            //Background colour change based on majority of a particular medal
            /* if (item.total_medal == 0) {
                 backgroundLayout.setBackgroundColor(Color.parseColor("#FF0000"))
             } else if (item.gold >= item.silver && item.gold >= item.bronze) {
                 backgroundLayout.setBackgroundColor(Color.parseColor("#FFD700"))
             } else if (item.silver >= item.bronze) {
                 backgroundLayout.setBackgroundColor(Color.parseColor("#C0C0C0"))
             } else if (item.bronze > 0) {
                 backgroundLayout.setBackgroundColor(Color.parseColor("#CD7F32"))
             }
             */

            //onClick to show Toast(Display Gold amount of gold medal)
            backgroundLayout.setOnClickListener {
                Toast.makeText(
                    v.context,
                    "${item.country_name} has ${item.gold} gold medal/s", Toast.LENGTH_SHORT
                ).show()
                listener(item)
            }

            //OnLongClick to show snackbar (Display amount of each medal type
            backgroundLayout.setOnLongClickListener {
                val mySnackbar = Snackbar.make(
                    v,
                    "${item.country_name} (${item.country_code}) has ${item.gold} gold medal/s, ${item.silver} silver medal/s, " +
                            "${item.bronze} bronze medal/s ",
                    Snackbar.LENGTH_INDEFINITE
                )
                mySnackbar.setAction("Dismiss") { mySnackbar.dismiss() }.show()
                listener(item)
                true
            }
        }
    }

    // [Function] Generate Top Ten List by sorting via most amount of medal
    fun generateTopTenList(): MutableList<String> {
        val topTenMedallist = mutableListOf<String>()
        val allMedallistList = mutableListOf<TheMedalistData>()

        val listSize = TheMedalistList.count - 1
        for (i in 0..listSize) {
            val countryCode = TheMedalistList.MedalistList[i].country_code
            val countryTotalMedal = TheMedalistList.MedalistList[i].total_medal
            allMedallistList.add(TheMedalistData("", countryCode, 0, 0, 0, 0, countryTotalMedal))
        }
        allMedallistList.sortByDescending { it.total_medal }
        for (i in 0..9) {
            topTenMedallist.add(allMedallistList[i].country_code)
        }
        return topTenMedallist
    }
}
