package com.example.streak
//create goal class to use as information holder and add it to a database later on
class Goal {
    var id : Int = 0
    var streak : Int = 0
    var goal : String = ""
    var duration : String = ""
    var achieved : Int = 0

    constructor(goal:String, duration:String){
        this.goal = goal
        this.duration = duration
    }

    constructor(){
    }
}