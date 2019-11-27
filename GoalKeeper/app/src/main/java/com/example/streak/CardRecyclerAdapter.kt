package com.example.streak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_goal_card.view.*

//RecyclerViewAdapter takes data to be displayed in recyclerview and adapts it to the recyclerview

//Extends generic recycler viewholder class - allows for display of multiple view combinations
class CardRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //variable that will hold data from sqlite database
    private var items: List<Goal> = ArrayList()

    //Creating each different viewholder in recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GoalViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_goal_card, parent, false)
        )
    }

    //Check which viewholder type is being created and bind info appropriately from the list of data
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is GoalViewHolder ->{
                holder.bind(items.get(position))
            }
        }
    }

    //Return number of goals
    override fun getItemCount(): Int {
        return items.size
    }

    //Declare goal data for recyclerview adapter to use
    fun submitList(goalList : List<Goal>){
        items = goalList
    }

    //ViewHolder class defining what each viewholder will look like in the recyclerview
    class GoalViewHolder
    constructor (
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        //Views drawn from the goal viewholder xml file
        val cardGoal = itemView.card_goal
        val cardStreak = itemView.card_streak
        val cardDuration = itemView.card_duration

        //Takes each goal object and binding its info to the views in the layout
        fun bind(goal : Goal) {
            cardGoal.setText(goal.goal)
            cardStreak.setText(goal.streak.toString())
            cardDuration.setText(goal.duration.toString())
        }
    }
}