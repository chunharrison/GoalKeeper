package com.example.streak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_goal_card.view.*

class CardRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Goal> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GoalViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_goal_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is GoalViewHolder ->{
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(goalList : List<Goal>){
        items = goalList
    }

    class GoalViewHolder
    constructor (
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        val cardGoal = itemView.card_goal
        val cardStreak = itemView.card_streak
        val cardDuration = itemView.card_duration

        fun bind(goal : Goal) {
            cardGoal.setText(goal.goal)
            cardStreak.setText(goal.streak.toString())
            cardDuration.setText(goal.duration.toString())
        }
    }
}