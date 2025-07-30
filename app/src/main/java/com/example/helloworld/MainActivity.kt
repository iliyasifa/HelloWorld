package com.example.helloworld

import CounterFragment
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load our fragment into R.id.main(container view)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, CounterFragment())
            .commit()

        CounterBottomSheet().show(supportFragmentManager, "CounterBottomSheet")
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.setFragmentResultListener("counterResult", this) { _, bundle ->
            val finalCount = bundle.getInt("finalCount")
            Toast.makeText(this, "Final count from sheet: $finalCount", Toast.LENGTH_LONG).show()
        }
    }
}
