package com.example.quizjogoquiz.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun QuizTakingScreen(navController: NavController, quizId: String) {

    val quizRepository = remember { QuizRepository() }

    var questions by remember { mutableStateOf<List<Question>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(quizId) {
    isLoading  = true
    questions = quizRepository.getQuestionsForQuiz(quizId)
    isLoading = false
    }

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf(-1) }
    var score by remember { mutableStateOf(0) }

        Column(
         modifier = Modifier.fillMaxSize().padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement =Arrangement.Center

    ) {
        if (isLoading) {
            Text("Carregando perguntas...")
        } else if (!questions.isEmpty() && currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]

            // linha da pergunta do quiz
            Text(currentQuestion.questionText, fontSize = 22.sp, modifier = Modifier.padding(bottom = 24.dp))

            currentQuestion.options.forEachIndexed { index, option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)

                ) {
                    RadioButton(
                        selected = (index == selectedAnswerIndex),
                        onClick = { selectedAnswerIndex = index }
                    )
                    Text(option, fontSize = 18.sp, modifier = Modifier.padding(start = 8.dp))

                }

            }
            Button(
                onClick = {
                    if (selectedAnswerIndex == currentQuestion.correctAnswerIndex) {
                        score++
                    }

                    selectedAnswerIndex = -1

                    if (currentQuestionIndex < questions.size - 1) {
                        currentQuestionIndex++
                    } else {
                        navController.navigate("quizResult/$score/${questions.size}") {
                            popUpTo("quizList")
                        }
                    }
                },
                enabled = selectedAnswerIndex != -1,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(if (currentQuestionIndex < questions.size - 1) "Próxima Pergunta" else "Finalizar Quiz")
            }

        }else if (questions.isEmpty() && !isLoading) {
            Text("Este quiz ainda não tem perguntas!")
        }

    }

}

