package com.example.quizjogoquiz.repository

import com.example.quizjogoquiz.datasource.QuizDataSource
import com.example.quizjogoquiz.model.Question
import com.example.quizjogoquiz.model.Quiz
import kotlinx.coroutines.flow.Flow

class QuizRepository {

    private val dataSource = QuizDataSource()

    fun getAvailableQuizzes(): Flow<List<Quiz>> {
        return dataSource.getQuizListFlow()
    }

    fun fetchQuizzes() {
        dataSource.fetchAvailableQuizzes()
    }


    suspend fun getQuestionsForQuiz(quizId: String): List<Question> {
        return dataSource.fetchQuestionsForQuiz(quizId)
    }

    suspend fun saveQuizHeader(title: String, description: String): String? {
        return dataSource.saveQuizHeader(title, description)
    }

    suspend fun saveQuestion(quizId: String, question: Question): Boolean {
        return dataSource.saveQuestion(quizId, question)
    }
}