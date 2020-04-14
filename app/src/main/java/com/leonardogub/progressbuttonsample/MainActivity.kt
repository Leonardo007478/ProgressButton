package com.leonardogub.progressbuttonsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressButton.setOnClickListener {
            progressButton.isProgressVisible = true
            Toast.makeText(this, "Button pressed!", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                progressButton.isProgressVisible = false
            }, 3000)
        }
    }
}
