package com.example.streak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //when button is clicked, execute code
        nextButton.setOnClickListener {
            insertGoal()
            nextActiviy()
        }

    }
    //get to the next page, not used currentl
    fun nextActiviy(){
        val intent = Intent(this, LandingPageActivity::class.java)
        // start your next activity
        startActivity(intent)
    }
    //take goal from text box and add it to db
    fun insertGoal(){
        if(editText.text.toString().length > 0) {
            var goal = Goal(editText.text.toString())
            var db = DatabaseHandler(context)
            db.insertData(goal)
        } else{
            Toast.makeText(context, "Please Provide a Goal", Toast.LENGTH_SHORT).show()
        }
    }

}
