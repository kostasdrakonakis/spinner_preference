package com.github.kostasdrakonakis.spinnerpreference.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.kostasdrakonakis.spinnerpreference.sample.SettingsActivity.Companion.startActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener { startActivity(this@MainActivity) }
    }
}