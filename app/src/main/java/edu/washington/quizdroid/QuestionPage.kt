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
        val overview = findViewById<TextView>(R.id.overview)
        if (title == "Physics") {
            overview.text = getString(R.string.physicsOverview)
        } else if (title == "Math") {
            overview.text = getString(R.string.mathOverview)
        } else if (title == "Marvel Superheroes") {
            overview.text = getString(R.string.marvelOverview)
        }
    }

    fun home(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun startQuiz(view: View) {
        val intent = Intent(this, QuestionPage::class.java).apply {
            putExtra(QUIZ_TITLE, intent.getStringExtra(OVERVIEW_TITLE))
        }
        startActivity(intent)
    }
}