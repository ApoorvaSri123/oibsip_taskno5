package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnReset: Button

    private var handler: Handler = Handler()
    private var startTime: Long = 0
    private var timeInMilliseconds: Long = 0
    private var elapsedTime: Long = 0

    private val updateTime: Runnable = object : Runnable {
        override fun run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime
            val updatedTime: Int = elapsedTime.toInt() + timeInMilliseconds.toInt()
            val seconds: Int = updatedTime / 1000
            val minutes: Int = seconds / 60
            val hours: Int = minutes / 60

            val timerString = String.format(
                "%02d:%02d:%02d",
                hours,
                minutes % 60,
                seconds % 60
            )

            tvTimer.text = timerString
            handler.postDelayed(this, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTimer = findViewById(R.id.tvTimer)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnReset = findViewById(R.id.btnReset)

        btnStart.setOnClickListener {
            startTime = SystemClock.uptimeMillis()
            handler.postDelayed(updateTime, 0)
            btnStart.isEnabled = false
            btnStop.isEnabled = true
            btnReset.isEnabled = false
        }

        btnStop.setOnClickListener {
            elapsedTime += timeInMilliseconds
            handler.removeCallbacks(updateTime)
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnReset.isEnabled = true
        }

        btnReset.setOnClickListener {
            elapsedTime = 0
            tvTimer.text = "00:00:00"
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnReset.isEnabled = false
        }
    }
}
