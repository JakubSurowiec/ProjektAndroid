package com.example.workoutapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class MainActivity : Activity() {

    private lateinit var tvExercise: TextView
    private lateinit var ivExercise: ImageView
    private lateinit var tvHistory: TextView
    private lateinit var btnGenerateExercise: Button
    private lateinit var btnSaveExercise: Button
    private lateinit var btnClearHistory: Button
    private lateinit var tvHistoryTitle: TextView

    private val exercises = listOf("Pompki", "Przysiady", "Brzuszki", "Pajacyki", "Plank",)
    private val exerciseImages = mapOf(
        "Pompki" to R.drawable.pompki,
        "Przysiady" to R.drawable.przysiady,
        "Brzuszki" to R.drawable.brzuszki,
        "Pajacyki" to R.drawable.pajacyk,
        "Plank" to R.drawable.plank,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGenerateExercise = findViewById(R.id.btn_generate_exercise)
        btnSaveExercise = findViewById(R.id.btn_save_exercise)
        btnClearHistory = findViewById(R.id.btn_clear_history)
        tvExercise = findViewById(R.id.tv_exercise)
        ivExercise = findViewById(R.id.iv_exercise)
        tvHistoryTitle = findViewById(R.id.tv_history_title)
        tvHistory = findViewById(R.id.tv_history)

        btnGenerateExercise.setOnClickListener {
            val randomExercise = exercises.random()
            val randomReps = Random.nextInt(10, 51)
            tvExercise.text = "$randomExercise: $randomReps powtórzeń"
            ivExercise.setImageResource(exerciseImages[randomExercise] ?: R.drawable.default_image)
        }

        btnSaveExercise.setOnClickListener {
            saveExercise(tvExercise.text.toString())
        }

        btnClearHistory.setOnClickListener {
            clearHistory()
        }

        loadHistory()
    }

    private fun saveExercise(exercise: String) {
        val sharedPreferences = getSharedPreferences("exerciseHistory", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val existingHistory = sharedPreferences.getString("history", "")
        val newHistory = "$existingHistory\n$exercise"
        editor.putString("history", newHistory)
        editor.apply()
        loadHistory()
    }

    private fun loadHistory() {
        val sharedPreferences = getSharedPreferences("exerciseHistory", Context.MODE_PRIVATE)
        val history = sharedPreferences.getString("history", "")
        tvHistory.text = history
    }

    private fun clearHistory() {
        val sharedPreferences = getSharedPreferences("exerciseHistory", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("history", "")
        editor.apply()
        tvHistory.text = ""
    }
}