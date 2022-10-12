package com.example.stopwatch

import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var minTime: Long = 0
    var startTime: Long = 10000
    var duration: Long = 1000

    var running = false
    var offset:Long = 0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    lateinit var countDownTimer: Chronometer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get a reference to the stopwatch
        countDownTimer = findViewById<Chronometer>(R.id.counter)

        if(countDownTimer.base <= minTime) {
            countDownTimer.stop()
        }

        setBaseTime()
        // Restore the previous state
        if (savedInstanceState != null) {
            offset =  savedInstanceState.getLong(OFFSET_KEY)
            running =  savedInstanceState.getBoolean(RUNNING_KEY)
            if (running) {
                countDownTimer.base = savedInstanceState.getLong(BASE_KEY)
                countDownTimer.start()
            } else {
                setBaseTime()
            }
        }

        // Handle start button touch event
        val start = findViewById<Button>(R.id.start)
        start.setOnClickListener{

            if(!running) {
                running = true
                countDownTimer.start()
            }
        }

        // Handle pause button touch event
        val pause = findViewById<Button>(R.id.pause)
        pause.setOnClickListener{

            if(running) {
                saveOffset()
                countDownTimer.stop();
                running = false;
            }
        }

        // Handle reset button touch event
        val reset = findViewById<Button>(R.id.reset)
        reset.setOnClickListener{

            offset = 0
            setBaseTime()
        }

        // Handle plus button touch event
        val plus = findViewById<Button>(R.id.plus)
        plus.setOnClickListener {
            if (!running) {
                countDownTimer.base = countDownTimer.base + duration
            }
        }

        // Handle minus button touch event
        val minus = findViewById<Button>(R.id.minus)
        minus.setOnClickListener {
            if (!running && (countDownTimer.base > minTime )) {
                countDownTimer.base = countDownTimer.base - duration
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(OFFSET_KEY, offset)
        outState.putBoolean(RUNNING_KEY, running)
        outState.putLong(BASE_KEY, countDownTimer.base)

        super.onSaveInstanceState(outState)
    }

    // Update stopwatch base
    fun setBaseTime() {
        countDownTimer.base = (SystemClock.elapsedRealtime() - offset) + startTime
    }

    // Record the offset
    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - countDownTimer.base
    }


}