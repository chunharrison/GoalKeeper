package com.example.streak

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

//work created to reset the achieved status of a goal the next day, and if failed clear the streak
class DayChecker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    val context = ctx
    var db = DatabaseHandler(context)

    override fun doWork(): Result {
        db.resetFailed()
        db.resetAchieved()

        return Result.success()
    }

}