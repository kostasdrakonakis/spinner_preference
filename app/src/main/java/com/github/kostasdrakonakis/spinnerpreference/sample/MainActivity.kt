package com.github.kostasdrakonakis.spinnerpreference.sample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.kostasdrakonakis.spinnerpreference.sample.SettingsActivity.Companion.startActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener { startActivity(this@MainActivity) }
    }
}