package edu.washington.quizdroid

import android.app.Application
import android.util.Log
import android.os.Bundle

class QuizApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("QuizApp", "loaded and running")
    }

    companion object {
        val data = Data()
    }
}