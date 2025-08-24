package com.example.helloworld

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    sealed class Result<out T> {
        object Loading : Result<Nothing>();
        data class Success<T>(val data: T) : Result<T>()
    }

    val res: Result<String> = Result.Success("OK")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fun List<Int>.sumEvens() = filter { it % 2 == 0 }.sum()
        println(listOf(1, 2, 3, 4).sumEvens())
        println("onCreate called")
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.setFragmentResultListener("counterResult", this) { _, bundle ->
            val finalCount = bundle.getInt("finalCount")
            Toast.makeText(this, "Final count from sheet: $finalCount", Toast.LENGTH_LONG).show()
        }
        println("onResume called")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy called")
    }

    override fun onPause() {
        super.onPause()
        println("onPause called")
    }

    override fun onStop() {
        super.onStop()
        println("onStop called")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("onSaveInstanceState called")
    }


}
