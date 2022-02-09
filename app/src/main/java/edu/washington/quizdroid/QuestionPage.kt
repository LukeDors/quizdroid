package edu.washington.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

const val QUIZ_TITLE = "Title"

class QuestionPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_page)

        val title = intent.getStringExtra(OVERVIEW_TITLE)
        val subtitle = findViewById<TextView>(R.id.subtitle).apply {
            text = title
        }
        val data = QuizApp.data
        var topics = data.getTopics()
        val overview = findViewById<TextView>(R.id.overview)
        if (title == "Physics") {
            overview.text = topics[0].description
        } else if (title == "Math") {
            overview.text = topics[1].description
        } else if (title == "Marvel Superheroes") {
            overview.text = topics[2].description
        }
    }

    fun home(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun startQuiz(view: View) {
        val intent = Intent(this, QuizPage::class.java).apply {
            putExtra(QUIZ_TITLE, intent.getStringExtra(OVERVIEW_TITLE))
        }
        startActivity(intent)
    }
}