package com.example.workout_app_jetpack_compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(onStartClick = {
                //intent is use to navigate directly to there.
                val intent = Intent(this, ExerciseActivity::class.java)
                startActivity(intent)

            })
        }
    }
}
