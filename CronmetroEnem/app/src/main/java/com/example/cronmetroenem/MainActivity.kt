package com.example.cronmetroenem

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Começar prova 1
        val start1 = findViewById<Button>(R.id.start_test)
        start1.setOnClickListener {
            val intent = Intent(baseContext, CronometerActivity::class.java)
            intent.putExtra("DAY", 1)
            startActivity(intent)
        }

        // Começar prova 2
        val start2 = findViewById<Button>(R.id.start2)
        start2.setOnClickListener {
            val intent = Intent(baseContext, CronometerActivity::class.java)
            intent.putExtra("DAY", 2)
            startActivity(intent)
        }

        // Info dia 1
        val info1 = findViewById<ImageButton>(R.id.day1Info)
        info1.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)

            // Set the alert dialog title
            builder.setTitle("Primeiro Dia")

            // Display a message on alert dialog
            builder.setMessage("São 5 horas e 30 minutos para Linguagens, Ciências Humanas e Redação")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Ok"){_, _ ->
                // Do Nothing
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }

        // Info dia 2
        val info2 = findViewById<ImageButton>(R.id.day2Info)
        info2.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)

            // Set the alert dialog title
            builder.setTitle("Segundo Dia")

            // Display a message on alert dialog
            builder.setMessage("São 5 horas para Matemática e Ciências da Natureza")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Ok"){_, _ ->
                // Do Nothing
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }

        // Ver provas antigas
        val provas_enem= findViewById<Button>(R.id.enemButton)
        provas_enem.setOnClickListener {
            startActivity(Intent(applicationContext,TestsActivity::class.java))
        }

        // Ver estatisticas
        val statsButton= findViewById<Button>(R.id.statsButton)
        statsButton.setOnClickListener {
            startActivity(Intent(applicationContext,StatsActivity::class.java))
        }

        // Ir para o instagram
        val instagramButton= findViewById<ImageButton>(R.id.instagramButton)
        instagramButton.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/michaelbarneyjr/"))
            startActivity(i)
        }

        // Initialize Ads
        MobileAds.initialize(this) {}
    }
}
