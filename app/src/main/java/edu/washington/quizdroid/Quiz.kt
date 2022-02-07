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

class Quiz : AppCompatActivity() {
    lateinit var next: Button
    lateinit var finish: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val finish = findViewById<Button>(R.id.finish)
        val next = findViewById<Button>(R.id.next)
        val title = intent.getStringExtra(QUIZ_TITLE)
        Log.d("title is", title.toString())
        finish.visibility = View.INVISIBLE
        var count = 1
        var correct = 0
        val summary = findViewById<TextView>(R.id.summary)
        val quizTime = findViewById<TextView>(R.id.quizTime)
        quizTime.setText(title)
        var options = emptyArray<Int>()
        var answers = emptyArray<Int>()
        var questions = emptyArray<Int>()
        var questionIndex = 0
        if (title.toString() == "Physics") {
            options = arrayOf(R.string.physicsA1,R.string.physicsA2,R.string.physicsA3,R.string.physicsA4,R.string.physicsA5,R.string.physicsA6,R.string.physicsA7,R.string.physicsA8,R.string.physicsA9,R.string.physicsA10,R.string.physicsA11,R.string.physicsA12,R.string.physicsA13,R.string.physicsA14,R.string.physicsA15)
            answers = arrayOf(R.string.physicsA2,R.string.physicsA6,R.string.physicsA7,R.string.physicsA10,R.string.physicsA14)
            questions = arrayOf(R.string.physicsQ1,R.string.physicsQ2,R.string.physicsQ3,R.string.physicsQ4,R.string.physicsQ5)
        } else if (title.toString() == "Math") {
            options = arrayOf(R.string.mathA1,R.string.mathA2,R.string.mathA3,R.string.mathA4,R.string.mathA5,R.string.mathA6,R.string.mathA7,R.string.mathA8,R.string.mathA9,R.string.mathA10,R.string.mathA11,R.string.mathA12,R.string.mathA13,R.string.mathA14,R.string.mathA15)
            answers = arrayOf(R.string.mathA3,R.string.mathA5,R.string.mathA8,R.string.mathA10,R.string.mathA14)
            questions = arrayOf(R.string.mathQ1,R.string.mathQ2,R.string.mathQ3,R.string.mathQ4,R.string.mathQ5)
        } else if (title.toString() == "Marvel Superheroes") {
            questions = arrayOf(R.string.marvelQ1, R.string.marvelQ2, R.string.marvelQ3, R.string.marvelQ4, R.string.marvelQ5)
            options = arrayOf(
                R.string.marvelA1,
                R.string.marvelA2,
                R.string.marvelA3,
                R.string.marvelA4,
                R.string.marvelA5,
                R.string.marvelA6,
                R.string.marvelA7,
                R.string.marvelA8,
                R.string.marvelA9,
                R.string.marvelA10,
                R.string.marvelA11,
                R.string.marvelA12,
                R.string.marvelA13,
                R.string.marvelA14,
                R.string.marvelA15
            )
            answers = arrayOf(
                R.string.marvelA2,
                R.string.marvelA4,
                R.string.marvelA8,
                R.string.marvelA11,
                R.string.marvelA13
            )
        }
        summary.setText(questions[questionIndex])
        var index1 = 0
        var index2 = 1
        var index3 = 2
        var currentOptions = arrayOf(options[index1],options[index2],options[index3])
        val group = findViewById<RadioGroup>(R.id.group)
        val btn1 = findViewById<RadioButton>(R.id.btn1)
        val btn2 = findViewById<RadioButton>(R.id.btn2)
        val btn3 = findViewById<RadioButton>(R.id.btn3)
        btn1.setText(currentOptions[0])
        btn2.setText(currentOptions[1])
        btn3.setText(currentOptions[2])

        next.setOnClickListener {
            if (group.getCheckedRadioButtonId() == -1) {
                next.isEnabled = true
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
                summary.setText(questions[questionIndex])
            } else {
                //question screen
                next.setText(R.string.next)
                questionIndex += 1
                index1 += 3
                index2 += 3
                index3 += 3
                var id = findViewById<RadioButton>(group.checkedRadioButtonId)
                var answer = id.text
                Log.d("input", answer.toString())
                Log.d("answer", getString(answers[questionIndex-1]))
                if (answer == getString(answers[questionIndex-1])) {
                    correct += 1
                }
                summary.setText(correct.toString())
                currentOptions = arrayOf(options[index1],options[index2],options[index3])
                btn1.setText(currentOptions[0])
                btn2.setText(currentOptions[1])
                btn3.setText(currentOptions[2])
            }
        }
    }

    fun home(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}