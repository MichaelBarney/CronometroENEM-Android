package com.example.cronmetroenem

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.*
import kotlin.concurrent.schedule

class CronometerActivity : AppCompatActivity() {
    var timeList:IntArray = intArrayOf()
    var timeInMinutes = 0
    var currentTime = 0
    var timer = Timer()


    private var myAdapter = StickerAdapter()
    private var myRecyclerView: RecyclerView? = null

    var day:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cronometer)

        day = intent.getIntExtra("DAY", 1)
        if (day == 1){
            timeInMinutes = 330
        }
        else if(day == 2){
            timeInMinutes = 300
        }


        setTime(timeInMinutes)
        myRecyclerView = findViewById<RecyclerView>(R.id.stickerRecyclerView)
        fillRecyclerView()

        // Add a Start Popup
        val startPopup = AlertDialog.Builder(this)
        startPopup.setTitle("Está preparado?")
        startPopup.setMessage("Lembre-se de colocar o celular em modo avião para que nada lhe atrapalhe!")
        startPopup.setNegativeButton("Voltar") { _, _ ->
            super.onBackPressed()
        }
        startPopup.setPositiveButton("Começar"){_, _ ->
            // Começar o Timer
            timer.schedule(0, 60){
                updateTimers()
            }

            // Som de inicio
            val mp = MediaPlayer.create(applicationContext, R.raw.bell)
            mp.start()
        }
        val dialog: AlertDialog = startPopup.create()
        dialog.show()

        // Prepare the Finish Test Button
        val finishTestButton = findViewById<Button>(R.id.finishTestButton)
        finishTestButton.setOnClickListener{
            val testTime = timeInMinutes - currentTime
            val hours =  testTime/ 60
            val minutes = testTime % 60


            // Add a Start Popup
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Entregar Orova")
            builder.setMessage("Tempo de prova: $hours h $minutes m \nVocê tem certeza que deseja entregar a prova e voltar pra tela inicial?")
            builder.setNegativeButton("Sim, entregar a prova"){_, _ ->
                timer.cancel()
                addToDatabase(false)
                super.onBackPressed()
            }
            builder.setPositiveButton("Não, voltar para a prova"){_, _ ->
                // Fazer nada
            }
            builder.create().show()
        }

        // Load Banner Ad
        val mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    fun finishCronometer() {
        Log.d("Finished Test", "Finished Test")
        timer.cancel()

        // Add to Database
        addToDatabase(false)

        runOnUiThread {
            // Add a Finish Popup
            val builder = AlertDialog.Builder(this)

            // Set the alert dialog title
            builder.setTitle("Fim da prova!")

            // Display a message on alert dialog
            builder.setMessage("Acabou o tempo, parabéns por ter terminado!")

            // Set a positive button and its click listener on alert dialog
            builder.setNegativeButton("Sair") { _, _ ->
                super.onBackPressed()
            }
            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }

        // Som de fim
        val mp = MediaPlayer.create(applicationContext, R.raw.bell)
        mp.start()
    }

    fun updateTimers() {
        Log.d("Timer", currentTime.toString())
        if (currentTime == 30) {

        } else if (currentTime == 180) {

        }

        if (currentTime <= 0) {
            timer.cancel()
            if (timeList.size > 0) {
                removeSticker()
                finishCronometer()
            }
            else {
                return
            }
            return
        }

        currentTime -= 1

        // did finish the timer
        if (timeList.size <= 1) {
            return
        }
        else {
            // Timer found a changing situation
            if (currentTime <= timeList[1]) {
                removeSticker()
            }
        }


    }

    private fun removeSticker() {
        runOnUiThread {
            val rowView = myRecyclerView?.findViewHolderForAdapterPosition(0)?.itemView

            val anim = AnimationUtils.loadAnimation(
                this,
                android.R.anim.slide_out_right
            )
            anim.duration = 300
            rowView?.startAnimation(anim)
            timeList = timeList.drop(1).toIntArray()

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(arg0: Animation) {}
                override fun onAnimationRepeat(arg0: Animation) {}
                override fun onAnimationEnd(arg0: Animation) {
                    myAdapter.notifyDataSetChanged() //Refresh list
                    anim.setAnimationListener(null)
                }
            })

            // Accessibility Event
            val nextView = myRecyclerView?.findViewHolderForAdapterPosition(1)?.itemView
            if (nextView != null) {
                nextView.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
            };

            // Som de rasgado
            val mp = MediaPlayer.create(applicationContext, R.raw.papertear)
            mp.start()

        }
    }


    fun setTime(time: Int) {
        timeInMinutes = time
        currentTime = time


        for (t in 0..(time + 1) step 15) {
            if (t == 0) {
                continue
            }
            if (t <= 45 && t != 0) {
                timeList += t
            }
            else if (t%30 == 0) {
                timeList += t
            }
        }

        timeList.reverse()
        fillRecyclerView()
    }

    fun fillRecyclerView(){
        // Fill out the RecyclerView
        myRecyclerView?.apply{
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@CronometerActivity)
        }
    }

    private inner class StickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hour: TextView = itemView.findViewById(R.id.hour)
        var status: ImageView = itemView.findViewById(R.id.status)
    }


    private inner class StickerAdapter :
        RecyclerView.Adapter<StickerViewHolder>() {

        override fun onBindViewHolder(holder: StickerViewHolder, position: Int) {

            if (position == 0) {
                //        if self.timeList[n] > self.currentTime {
                if (timeList[position] < 100) {
                    holder.status.setImageResource(R.drawable.red)
                }
                else if (timeList[position] < 180) {
                    holder.status.setImageResource(R.drawable.yellow)
                }

                holder.status.alpha = Float.MAX_VALUE
                holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(this@CronometerActivity, R.color.justWhite)
                )
            }
            else {
                holder.status.alpha = Float.MIN_VALUE
                holder.itemView.setBackgroundColor(ContextCompat.getColor(this@CronometerActivity, R.color.transparentWhite))
            }

            holder.hour.text = getTime(timeList[position])
        }

        fun getTime(m: Int):String {
            val h = (m/60)
            val mn = m%60
            val s = String.format("%02d", mn)
            return "$h:$s"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.itemlista, parent, false)
            return StickerViewHolder(view)
        }

        override fun getItemCount(): Int {
            return timeList.size
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    fun addToDatabase(cancelled: Boolean){

        Log.d("adding: canceled = " , cancelled.toString())
        val dao: ProvaDao = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "provaDB"
        ).allowMainThreadQueries().build().provaDao()

        val thisTest = Prova(0, day, currentTime, cancelled)
        dao.insertAll(thisTest)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)

        // Set the alert dialog title
        builder.setTitle("Atenção!")

        // Display a message on alert dialog
        builder.setMessage("Ao sair seu progresso será perderdido. Você tem certeza que quer finalizar a prova?")

        // Set a positive button and its click listener on alert dialog
        builder.setNegativeButton("Sair"){_, _ ->
            // Save to Database
            addToDatabase(true)
            super.onBackPressed()
        }

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("Ficar"){_, _ ->
            // Do Nothing
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

}
