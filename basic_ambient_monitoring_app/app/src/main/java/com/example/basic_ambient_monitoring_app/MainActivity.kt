package com.example.basic_ambient_monitoring_app

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener{

    private lateinit var sensorManager: SensorManager
    private var ambientTemperature: Sensor? = null
    var temp: String = ""
    val scrollView: ScrollView by lazy { findViewById<ScrollView>(R.id.scrollview) }


        override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                temp = "\n" + event.values[0].toString()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        ambientTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (ambientTemperature == null) {
            findViewById<TextView>(R.id.textView_temperatur).text = "No ambient temperature sensor found."
        }

        val textview: TextView = findViewById(R.id.textView_temperatur)

        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                textview.append(temp)
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                handler.postDelayed(this, 100);
            }
        })

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, ambientTemperature, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}