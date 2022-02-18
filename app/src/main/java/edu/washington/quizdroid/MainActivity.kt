package edu.washington.quizdroid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.IntentFilter
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
import android.net.ConnectivityManager




const val OVERVIEW_TITLE = "Topic"

class MainActivity : AppCompatActivity() {
    lateinit var physics : Button
    lateinit var math : Button
    lateinit var marvel : Button
    var started: Boolean = false
    lateinit var prefs : Button
    lateinit var pendingIntent: PendingIntent

    class IntentListener : BroadcastReceiver() {
        init {
            Log.i("IntentListener", "Intent listener created")
        }

        override fun onReceive(context: Context?, intent: Intent?) {
            val url = intent?.extras?.getString("EXTRA_URL")
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
            Toast.makeText(context?.getApplicationContext(), url, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data = QuizApp.data
        var topics = data.getTopics()

        val receiver = IntentListener()
        val intFilter = IntentFilter()
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        intFilter.addAction("edu.us.ischool.download")
        registerReceiver(receiver, intFilter)

        val url: String? = intent.getStringExtra("URL")
        val minutes: String? = intent.getStringExtra("MINUTES")
        if (minutes !== null) {
            stopMessages(alarmManager as AlarmManager)
            sendMessages(alarmManager, url.toString(), minutes.toInt())
        }

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
            startActivity(Intent(Settings.ACTION_SETTINGS))
        } else if (hasSignal() !== null) {
            Toast.makeText(
                this,
                "No singal, please connect and retry",
                Toast.LENGTH_SHORT
            ).show()
        } else if (alarmManager !== null) {
            sendMessages(alarmManager, url.toString(), 5)
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

    private fun sendMessages(alarm: AlarmManager, url: String, min: Int) {
        val intent = Intent("edu.us.ischool.msg")
        var time = System.currentTimeMillis() + (min * 60000)
        intent.putExtra("EXTRA_URL", url)

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, time, (min * 60000).toLong(), pendingIntent)

        started = true
    }

    private fun hasSignal(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun stopMessages(alarm: AlarmManager) {
        alarm.cancel(pendingIntent)
        started = false
    }
}