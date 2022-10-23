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
    private var lightSensor: Sensor? = null
    var temp: String = "\n no data"
    var light: String = "\n no data"
    val scrollView: ScrollView by lazy { findViewById<ScrollView>(R.id.scrollview) }


        override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                temp = "\n" + event.values[0].toString()
            }
            if (event.sensor.type == Sensor.TYPE_LIGHT) {
                light = "\n" + event.values[0].toString()
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
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (ambientTemperature == null) {
            findViewById<TextView>(R.id.textView_temperatur).text = "No temperature sensor found."
        }
        if (lightSensor == null) {
            findViewById<TextView>(R.id.textView_light).text = "No light sensor found."
        }

        val textview_temperature: TextView = findViewById(R.id.textView_temperatur)
        val textview_light: TextView = findViewById(R.id.textView_light)

        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                textview_temperature.append(temp)
                textview_light.append(light)
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                handler.postDelayed(this, 250);
            }
        })

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, ambientTemperature, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}