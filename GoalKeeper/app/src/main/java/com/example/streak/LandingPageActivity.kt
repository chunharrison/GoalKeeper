package com.example.streak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_landing_page.*

class LandingPageActivity : AppCompatActivity() {

    val context = this
    var db = DatabaseHandler(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
        //initialize data reader
        var data = db.readData()
        goalView.text = ""
        //fill in goalview with goals from db
        for (i in 0 until data.size) {
            goalView.append("Goal: " + data.get(i).goal.toString() + " Streak: " + data.get(i).streak.toString() + "\n")
        }


        nextButton.setOnClickListener {
            nextActiviy()
        }

    }

    fun nextActiviy(){
        val intent = Intent(this, MainActivity::class.java)
        // start your next activity
        startActivity(intent)
    }
}
