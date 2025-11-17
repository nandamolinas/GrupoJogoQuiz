package com.example.quizjogoquiz.model

data class Question(
    val questionText: String = "",
    val options: List<String> = emptyList(),
    val correctAnswerIndex: Int = 0
)