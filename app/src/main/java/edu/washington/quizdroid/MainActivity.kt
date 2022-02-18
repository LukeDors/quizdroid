package edu.washington.quizdroid

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

const val OVERVIEW_TITLE = "Topic"

class MainActivity : AppCompatActivity() {
    lateinit var physics : Button
    lateinit var math : Button
    lateinit var marvel : Button
    lateinit var prefs : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data = QuizApp.data
        var topics = data.getTopics()

        val url: String? = intent.getStringExtra("URL")
        val minutes: String? = intent.getStringExtra("MINUTES")

        val t = thread {
            val server =
                URL("https://gist.githubusercontent.com/LukeDors/9db90086d690923ecabd6d304f77ac28/raw/a879f6a27bdf73e37925240a0387cd4914f925cd/questions.json")
            val client: HttpURLConnection = server.openConnection() as HttpURLConnection
            client.requestMethod = "GET"
            val filePath = File(Environment.getExternalStorageDirectory().path, "questions.json")
            val result = BufferedReader(InputStreamReader(client.inputStream))
            val output = filePath.bufferedWriter(Charsets.UTF_8, DEFAULT_BUFFER_SIZE)
            var inputLine: String?
            while(result.readLine().also {inputLine = it} !== null) {
                output.write(inputLine)
                output.newLine()
            }
            result.close()
            output.close()
        }

        physics = findViewById<Button>(R.id.physics)
        physics.setText(topics[0].title)
        math = findViewById<Button>(R.id.math)
        math.setText(topics[1].title)
        marvel = findViewById<Button>(R.id.marvel)
        marvel.setText(topics[2].title)
        prefs = findViewById<Button>(R.id.prefs)

        if (isAirplaneModeOn(this)) {
            Toast.makeText(
                this,
                "Airplane mode is on, please turn off to download",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun overview(view: View) {
        val btn = view as Button
        val txt = btn.getText().toString()
        val intent = Intent(this, QuestionPage::class.java).apply {
            putExtra(OVERVIEW_TITLE, txt)
        }
        startActivity(intent)
    }

    fun prefs(view: View) {
        val intent = Intent(this, PrefsPage::class.java)
        startActivity(intent)
    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.System.getInt(
            context.getContentResolver(),
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) !== 0
    }
}