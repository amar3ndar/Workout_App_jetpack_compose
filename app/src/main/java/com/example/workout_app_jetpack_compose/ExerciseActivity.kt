package com.example.workout_app_jetpack_compose

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.Keep
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

class ExerciseActivity : ComponentActivity(), TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer? = null
    private var restProgress = mutableStateOf(0)

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = mutableStateOf(0)

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = mutableStateOf(-1)

    private var tts: TextToSpeech? = null
    
    private var player: MediaPlayer? = null

    private var isRestPhase = mutableStateOf(true) // Start with RestView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)

        exerciseList = Constants.yogaExerciseList()

        setContent {
            ExerciseScreen()
        }
    }


    @Composable
    fun ExerciseScreen() {
        KeepScreenOn()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Top Toolbar
            TopToolbar()
            // Timer and Exercise View: Use RestView if resting, ExerciseView otherwise
            if (isRestPhase.value) {
                RestView()
            } else {
                ExerciseView()
            }
        }
    }

    @Composable
    fun ExerciseView() {
        val progress = exerciseProgress.value
        val exercise = exerciseList!![currentExercisePosition.value]

        //Name of current Exercise
        Text(
            text = exercise.getName(),
            color = Color.Black,
            fontSize = 22.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.padding(top=5.dp))
        Divider(thickness = 2.dp, color = Color.Black,
            modifier = Modifier.padding(horizontal = 145.dp))

        //Image and Timer
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // Centers the content
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = exercise.getImage()),
                    contentDescription = null
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .border(3.dp, color = colorResource(R.color.colorAccent), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = (30 - progress) / 30f,
                        modifier = Modifier
                            .size(85.dp)
                            .clip(CircleShape),
                        color = colorResource(id = R.color.colorPrimary),
                        strokeWidth = 4.dp
                    )
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(color = colorResource(R.color.colorAccent)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (30 - progress).toString(),
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        LaunchedEffect(key1 = isRestPhase.value) {
            if (!isRestPhase.value) {
                setExerciseProgressBar()
                speakOut("Start ${exercise.getName()}")
            }
        }
    }

    //Timer for exercises
    private fun setExerciseProgressBar() {
        exerciseTimer?.cancel() // Cancel any ongoing timer

        exerciseProgress.value = 10 // Start from 10 explicitly
        exerciseTimer = object : CountDownTimer(31000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress.value = 30 - (millisUntilFinished / 1000).toInt() // Convert to seconds
            }

            override fun onFinish() {
                exerciseProgress.value = 0 // Ensure it ends at 0
                if (currentExercisePosition.value < exerciseList!!.size - 1) {
                    isRestPhase.value = true // Switch to rest phase
                    setRestProgressBar() // Start rest phase
                } else {
                    Toast.makeText(this@ExerciseActivity, "Workout complete!", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    @Composable
    fun RestView() {
        val progress = restProgress.value

        Text(
            text = "GET READY FOR",
            fontSize = 26.sp,
            color = colorResource(id = R.color.colorAccent),
            modifier = Modifier.padding(vertical = 16.dp),
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // Centers the content
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "UPCOMING EXERCISE:",
                    color = colorResource(id = R.color.upcoming_exercise),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )

                if (exerciseList != null && currentExercisePosition.value + 1 < exerciseList!!.size) {
                    Text(
                        text = exerciseList!![currentExercisePosition.value + 1].getName(),
                        color = colorResource(id = R.color.exercise_name),
                        fontSize = 22.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Image(
                        painter = painterResource(id = exerciseList!![currentExercisePosition.value + 1].getImage()),
                        contentDescription = "Exercise Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(top = 16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .border(3.dp, color = colorResource(R.color.colorAccent), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = (30 - progress) / 30f,
                        modifier = Modifier
                            .size(85.dp)
                            .clip(CircleShape),
                        color = colorResource(id = R.color.colorPrimary),
                        strokeWidth = 4.dp
                    )
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(color = colorResource(R.color.colorAccent)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (30 - progress).toString(),
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Trigger the countdown timer
        LaunchedEffect(key1 = isRestPhase.value) {
            setRestProgressBar()
            if (isRestPhase.value) { // start sound effect
                val soundURI = Uri.parse("android.resource://com.example.workout_app_jetpack_compose/" + R.raw.press_start)
                player = MediaPlayer.create(applicationContext, soundURI)
                player?.isLooping = false
                player?.start()
            }
            speakOut("Get ready for ${exerciseList!![currentExercisePosition.value + 1].getName()}")
        }
    }


    //Timer for rest and start
    private fun setRestProgressBar() {
        restTimer?.cancel() // Cancel any ongoing timer

        restProgress.value = 5 // Start from 5 explicitly
        restTimer = object : CountDownTimer(31000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress.value = 30 - (millisUntilFinished / 1000).toInt() // Convert to seconds
            }

            override fun onFinish() {
                restProgress.value = 0 // Ensure it ends at 0
                isRestPhase.value = false // Switch to exercise phase
                currentExercisePosition.value++ // Move to the next exercise
            }
        }.start()
    }


    val godOfWar = FontFamily(
        Font(R.font.god_of_war),
    )
    @Composable
    fun TopToolbar() {
        Box(
            modifier = Modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(bottomEnd = 16.dp))
                .clip(RoundedCornerShape(bottomStart = 16.dp)).background(colorResource(R.color.topToolBar)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Exercise",
                color = Color.White,
                fontSize = 25.sp,   
                fontFamily = godOfWar,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

    // TTS Initialization
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val availableLanguages = tts?.availableLanguages
            if (availableLanguages?.contains(Locale.CANADA) == true) {
                tts?.setLanguage(Locale.CANADA)
            } else {
                // Fallback language
                tts?.setLanguage(Locale.ENGLISH)
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_ADD, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        restTimer?.cancel()
        exerciseTimer?.cancel()
        tts?.stop()
        tts?.shutdown()
        player?.stop()
        player?.release()
    }
}
