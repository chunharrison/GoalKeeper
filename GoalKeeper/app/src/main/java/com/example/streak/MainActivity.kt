package com.example.streak

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button click to show TimePicker Dialog
        initDurationButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener =  TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                // Set time to TextView: initDuration
                initDuration.text = SimpleDateFormat("HH:mm").format(cal.time)
            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        //when button is clicked, execute code
        nextButton.setOnClickListener {
            insertGoal()
            nextActivity()
        }

    }

    // get to the next page, not used currentl
    private fun nextActivity(){
        val intent = Intent(this, LandingPageActivity::class.java)
        // start your next activity
        startActivity(intent)
    }
    //take goal from text box and add it to db
    private fun insertGoal(){
        if(editText.text.toString().length > 0) {
            var goal = Goal(editText.text.toString(), initDuration.text.toString())
            var db = DatabaseHandler(context)
            db.insertData(goal)
        } else{
            Toast.makeText(context, "Please Provide a Goal", Toast.LENGTH_SHORT).show()
        }
    }

}
