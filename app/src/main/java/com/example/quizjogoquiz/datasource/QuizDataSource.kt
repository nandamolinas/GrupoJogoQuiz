package com.example.quizjogoquiz.datasource

import android.util.Log
import com.example.quizjogoquiz.model.Question
import com.example.quizjogoquiz.model.Quiz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class QuizDataSource {

    private val db = FirebaseFirestore.getInstance()

    private val _quizList = MutableStateFlow<List<Quiz>>(emptyList())

    fun getQuizListFlow(): Flow<List<Quiz>> = _quizList


    fun fetchAvailableQuizzes() {
        db.collection("quizzes").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val quizzes = task.result.map { doc ->
                    doc.toObject(Quiz::class.java).copy(id = doc.id)
                }
                _quizList.value = quizzes
            } else {

                Log.e("QuizDataSource", "Erro ao buscar quizzes.", task.exception)
            }
        }
    }

    suspend fun fetchQuestionsForQuiz(quizId: String): List<Question> {
        return try {
            val task = db.collection("quizzes").document(quizId).collection("questions")
                .get()
                .await()

            task.map { it.toObject(Question::class.java) }
        } catch (e: Exception) {
            Log.e("QuizDataSource", "Erro ao buscar perguntas.", e)
            emptyList()
        }
    }

    suspend fun saveQuizHeader(title: String, description: String): String? {
        return try {
            val quizData = hashMapOf(
                "title" to title,
                "description" to description
            )

            val documentRef = db.collection("quizzes").add(quizData).await()
            documentRef.id
        } catch (e: Exception) {
            Log.e("QuizDataSource", "Erro ao salvar cabeçalho do quiz.", e)
            null
        }
    }

    suspend fun saveQuestion(quizId: String, question: Question): Boolean {
        val questionData = hashMapOf(
            "questionText" to question.questionText,
            "options" to question.options,
            "correctAnswerIndex" to question.correctAnswerIndex
        )

        return try {
            db.collection("quizzes").document(quizId)
                .collection("questions").add(questionData).await()
            true
        } catch (e: Exception) {
            Log.e("QuizDataSource", "Erro ao salvar pergunta.", e)
            false
        }
    }
}