package com.example.cronmetroenem

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.google.android.gms.ads.MobileAds

class StatsActivity : AppCompatActivity() {

    var day1:List<Prova> = emptyList()
    var day2:List<Prova> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stats)
        loadData()
    }

    fun loadData(){
        runOnUiThread {
            val dao: ProvaDao = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "provaDB"
            ).allowMainThreadQueries().build().provaDao()

            day1 = dao.getByDay(1)
            day2 = dao.getByDay(2)

            // Day 1
            if (day1.size != 0) {
                // Count
                val numprovas1 = findViewById<TextView>(R.id.nprovas1)
                numprovas1.text = day1.size.toString()

                // Tempo Médio
                var sum = 0;
                var cancelledNum = 0
                for (prova in day1) {
                    sum += prova.duration
                    if (prova.cancelled) {
                        cancelledNum += 1;
                    }
                }
                val media = sum / day1.size

                val testTime = 330 - media
                val hours =  testTime/ 60
                val minutes = String.format("%02d", testTime % 60 )

                val tprovas1 = findViewById<TextView>(R.id.tprovas1)
                tprovas1.text = hours.toString() + "h$minutes"

                // Taxa de Cancelamento
                val percentage = 100 - ((cancelledNum * 100)/ day1.size)
                val completion1 = findViewById<TextView>(R.id.completion1)
                completion1.text = "$percentage%"

                // Ultima Prova
                val lastTime = 330 - day1.last().duration
                val lastHours =  lastTime/ 60
                val lastMinutes = String.format("%02d", lastTime % 60 )
                val ultima1 = findViewById<TextView>(R.id.ultima1)
                ultima1.text = lastHours.toString() + "h$lastMinutes"
            }
            // Day2
            if (day2.size != 0) {
                // Count
                val provas2 = findViewById<TextView>(R.id.nprovas2)
                provas2.text = day2.size.toString()


                // Tempo Médio
                var sum2 = 0;
                var cancelledNum = 0
                for (prova in day2) {
                    sum2 += prova.duration
                    if (prova.cancelled) {
                        cancelledNum += 1;
                    }
                }
                Log.d("Cancelled Num 2", cancelledNum.toString())
                val media2 = sum2 / day2.size
                val testTime = 300 - media2
                val hours =  testTime/ 60
                val minutes = testTime % 60
                val tprovas2 = findViewById<TextView>(R.id.tprovas2)
                tprovas2.text = hours.toString() + "h$minutes"

                // Taxa de Cancelamento
                val percentage = 100 - ((cancelledNum * 100)/ day2.size)
                Log.d("Percentage 2", day2.size.toString())
                val completion2 = findViewById<TextView>(R.id.completion2)
                completion2.text = "$percentage%"


                // Ultima Prova
                val lastTime = 300 - day2.last().duration
                val lastHours =  lastTime/ 60
                val lastMinutes = String.format("%02d", lastTime % 60 )
                val ultima2 = findViewById<TextView>(R.id.ultima2)
                ultima2.text = lastHours.toString() + "h$lastMinutes"
            }
        }

    }
}
