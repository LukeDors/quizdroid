package edu.washington.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class PrefsPage : AppCompatActivity() {
    lateinit var home: Button
    lateinit var submit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefs_page)

        home = findViewById<Button>(R.id.home)
        submit = findViewById<Button>(R.id.submit)
        val getURL = findViewById<EditText>(R.id.url)
        val getMinutes = findViewById<EditText>(R.id.minutes)

        submit.setOnClickListener {
            var url = getURL.text.toString()
            var minutes = getMinutes.text.toString()
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("MINUTES", minutes)
                putExtra("URL", url)
            }
            startActivity(intent)
        }
    }

    fun home(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}