package edu.washington.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button

const val OVERVIEW_TITLE = "Topic"

class MainActivity : AppCompatActivity() {
    lateinit var physics : Button
    lateinit var math : Button
    lateinit var marvel : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = QuizApp.data
        var topics = data.getTopics()

        physics = findViewById<Button>(R.id.physics)
        physics.setText(topics[0].title)
        math = findViewById<Button>(R.id.math)
        math.setText(topics[1].title)
        marvel = findViewById<Button>(R.id.marvel)
        marvel.setText(topics[2].title)
    }

    fun overview(view: View) {
        val btn = view as Button
        val txt = btn.getText().toString()
        val intent = Intent(this, QuestionPage::class.java).apply {
            putExtra(OVERVIEW_TITLE, txt)
        }
        startActivity(intent)
    }
}