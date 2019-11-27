package com.example.streak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_landing_page.*

class LandingPageActivity : AppCompatActivity() {

    val context = this
    var db = DatabaseHandler(context)

    private lateinit var goalAdapter: CardRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        initRecyclerView()
        addDataSet()
    }

    //Retrieve sqlite data and pass it on to adapter via submitList()
    private fun addDataSet(){
        var data = db.readData()
        goalAdapter.submitList(data)
    }

    //Access recyclerview
    fun initRecyclerView(){
        recycler_view.apply{
            //Always assign layout manger, this one is linear i.e. a vertical scrolling view
            layoutManager = LinearLayoutManager(this@LandingPageActivity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            goalAdapter = CardRecyclerAdapter()
            //assign adapter to this particular recyclerview
            adapter = goalAdapter
        }
    }

    fun nextActiviy(){
        val intent = Intent(this, MainActivity::class.java)
        // start your next activity
        startActivity(intent)
    }
}
