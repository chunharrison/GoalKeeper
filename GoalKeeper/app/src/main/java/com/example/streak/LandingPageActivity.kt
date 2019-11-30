package com.example.streak

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        //library that enables commands on swipe and move defined
        //LEFT swipe specifically
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            //unused
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            //upon left swipe delete data from database and current list in use aswell as the viewholder
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                if (position == ItemTouchHelper.LEFT){
                    db.deleteData(goalAdapter.getRowId(viewHolder.adapterPosition))
                    goalAdapter.deleteViewHolder(viewHolder)
                } else if (position == ItemTouchHelper.RIGHT) {
                    db.achieved(goalAdapter.getRowId(viewHolder.adapterPosition))
                    goalAdapter.deleteViewHolder(viewHolder)
                }
            }

        }

        //assign said library to variable
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)

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

    fun delete(position: Int) { //removes the row
        db.deleteData(position)

        db.close()
    }

    fun nextActiviy(){
        val intent = Intent(this, MainActivity::class.java)
        // start your next activity
        startActivity(intent)
    }
}
