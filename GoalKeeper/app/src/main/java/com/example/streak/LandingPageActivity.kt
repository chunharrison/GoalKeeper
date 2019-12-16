package com.example.streak

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_landing_page.*
import java.util.*


class LandingPageActivity : AppCompatActivity() {

    val context = this
    var db = DatabaseHandler(context)

    private lateinit var goalAdapter: CardRecyclerAdapter
    private var leftSwipeBackground: ColorDrawable = ColorDrawable(Color.parseColor(("#FF0000")))
    private var rightSwipeBackground: ColorDrawable = ColorDrawable(Color.parseColor(("#00ff00")))
    private lateinit var failedIcon: Drawable
    private lateinit var achievedIcon: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        initRecyclerView()
        addDataSet()

        failedIcon = ContextCompat.getDrawable(this, R.drawable.ic_failed)!!
        achievedIcon = ContextCompat.getDrawable(this, R.drawable.ic_achieved)!!

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

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView

                val iconMargin = (itemView.height - (itemView.height * 0.8).toInt())

                if (dX < 0) {
                    leftSwipeBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    failedIcon.setBounds(itemView.right - itemView.height + iconMargin, itemView.top + iconMargin, itemView.right - iconMargin,
                        itemView.bottom - iconMargin)
                    leftSwipeBackground.draw(c)
                    failedIcon.draw(c)
                } else {
                    rightSwipeBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    achievedIcon.setBounds(itemView.left + iconMargin, itemView.top + iconMargin, itemView.left + itemView.height - iconMargin,
                        itemView.bottom - iconMargin)
                    rightSwipeBackground.draw(c)
                    achievedIcon.draw(c)
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }

        addGoalButton.setOnClickListener{
            addGoalActivity()
        }

        //assign said library to variable
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)

    }

    //onResume triggers after onCreate but also triggers when the app is tabbed back in
    override fun onResume() {
        super.onResume()

        //sharedpreferenes point to key-value stores used for smaller set of data, no need to make sqlite table
        //store the day app was last used
        val sharedPref= context.getSharedPreferences("LAST_OPENED", Context.MODE_PRIVATE)
        val date = sharedPref.getInt("date_last_opened", 0)

        if (date != getCurrentDay()) {
            sharedPref.edit().putInt("date_last_opened", getCurrentDay())
            sharedPref.edit().commit()
            db.resetFailed()
            db.resetAchieved()
            goalAdapter.notifyDataSetChanged()
        }

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

    fun addGoalActivity(){
        val intent = Intent(this, MainActivity::class.java)
        // start your next activity
        startActivity(intent)
    }

    fun getCurrentDay(): Int {
        return (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 + Calendar.getInstance().get(
            Calendar.DAY_OF_YEAR))
    }

}