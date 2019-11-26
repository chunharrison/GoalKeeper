package com.example.streak
//create goal class to use as information holder and add it to a database later on
class Goal {
    var id : Int = 0
    var streak : Int = 0
    var goal : String = ""

    constructor(goal:String){
        this.goal = goal
    }

    constructor(){
    }
}