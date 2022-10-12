package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    lateinit var stoppwatch: Chronometer
    var running = false
    var offset:Long = 0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get a reference to the stopwatch
        stoppwatch = findViewById<Chronometer>(R.id.stopwatch)

        // Restore the previous state
        offset =  savedInstanceState.getLong(OFFSET_KEY)
        running =  savedInstanceState.getBoolean(RUNNING_KEY)
        if (running) {
            stoppwatch.base = savedInstanceState.getLong(BASE_KEY)
            stoppwatch.start()
        }
        offset =  savedInstanceState.getLong(OFFSET_KEY)

        // Handle start button touch event
        var start = findViewById<Button>(R.id.start)
        start.setOnClickListener{

            if(!running) {
                setBaseTime()
                stoppwatch.start()
                running = true
            }
        }

        // Handle pause button touch event
        var pause = findViewById<Button>(R.id.pause)
        pause.setOnClickListener{

            if(running) {
                saveOffset()
                stoppwatch.stop()
                running = false
            }
        }

        // Handle reset button touch event
        var reset = findViewById<Button>(R.id.reset)
        reset.setOnClickListener{

           offset = 0
            setBaseTime()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(OFFSET_KEY, offset)
        outState.putBoolean(RUNNING_KEY, running)
        outState.putLong(BASE_KEY, stoppwatch.base)

        super.onSaveInstanceState(outState)
    }


    // Update stopwatch base
    fun setBaseTime() {
        stoppwatch.base = SystemClock.elapsedRealtime() - offset
    }

    // Record the offset
    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stoppwatch.base
    }
}