package edu.washington.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.isVisible
import android.util.Log

class QuizPage : AppCompatActivity() {
    lateinit var next: Button
    lateinit var finish: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val data = QuizApp.data
        val topics = data.getTopics()
        val finish = findViewById<Button>(R.id.finish)
        val next = findViewById<Button>(R.id.next)
        val title = intent.getStringExtra(QUIZ_TITLE)
        finish.visibility = View.INVISIBLE
        var count = 1
        var correct = 0
        val summary = findViewById<TextView>(R.id.summary)
        val quizTime = findViewById<TextView>(R.id.quizTime)
        quizTime.setText(title)
        var topic = topics[0]
        var questionIndex = 0
        if (title.toString() == "Physics") {
            topic = topics[0]
        } else if (title.toString() == "Math") {
            topic = topics[1]
        } else if (title.toString() == "Marvel Superheroes") {
            topic = topics[2]
        }
        summary.setText(topic.questions[questionIndex].question)
        var currentOptions = topic.questions[questionIndex].options
        val group = findViewById<RadioGroup>(R.id.group)
        val btn1 = findViewById<RadioButton>(R.id.btn1)
        val btn2 = findViewById<RadioButton>(R.id.btn2)
        val btn3 = findViewById<RadioButton>(R.id.btn3)
        val btn4 = findViewById<RadioButton>(R.id.btn4)
        btn1.setText(currentOptions[0])
        btn2.setText(currentOptions[1])
        btn3.setText(currentOptions[2])
        btn4.setText(currentOptions[3])

        next.setOnClickListener {
            if (group.getCheckedRadioButtonId() == -1) {
                next.isEnabled = false
            } else {
                next.isEnabled = true
            }
            count += 1
            if (count == 9) {
                next.visibility = View.INVISIBLE
                next.isEnabled = false
                finish.visibility = View.VISIBLE
                finish.isEnabled = true
            }
            if ((count % 2) == 1) {
                //answer screen
                next.setText(R.string.submit)
                summary.setText(topic.questions[questionIndex].question)
            } else {
                //question screen
                next.setText(R.string.next)
                questionIndex += 1
                val id = findViewById<RadioButton>(group.checkedRadioButtonId)
                val answer = id.text
                Log.d("input", answer.toString())
                Log.d("answer", getString(topic.questions[questionIndex-1].answer))
                if (answer == getString(topic.questions[questionIndex-1].answer)) {
                    correct += 1
                }
                summary.setText(correct.toString())
                currentOptions = topic.questions[questionIndex].options
                btn1.setText(currentOptions[0])
                btn2.setText(currentOptions[1])
                btn3.setText(currentOptions[2])
                btn3.setText(currentOptions[3])
            }
        }
    }

    fun home(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}