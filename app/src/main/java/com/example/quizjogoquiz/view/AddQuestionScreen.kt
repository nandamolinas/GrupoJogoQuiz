package com.example.quizjogoquiz.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quizjogoquiz.model.Question
import com.example.quizjogoquiz.repository.QuizRepository
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionScreen(navController: NavController, quizId: String) {

    val quizRepository = remember { QuizRepository() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var questionText by remember { mutableStateOf("") }
    var option1 by remember { mutableStateOf("") }
    var option2 by remember { mutableStateOf("") }
    var option3 by remember { mutableStateOf("") }
    var option4 by remember { mutableStateOf("") }

    var correctAnswerIndex by remember { mutableStateOf(0) }

    fun clearFields() {
        questionText = ""
        option1 = ""
        option2 = ""
        option3 = ""
        option4 = ""
        correctAnswerIndex = 0
    }

    suspend fun saveCurrentQuestion(): Boolean {
        val options = listOf(option1, option2, option3, option4)
        if (questionText.isNotBlank() && options.all { it.isNotBlank() }) {
            val newQuestion = Question(
                questionText = questionText,
                options = options,
                correctAnswerIndex = correctAnswerIndex
            )
            return quizRepository.saveQuestion(quizId, newQuestion)
        }
        return false
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Adicionar Perguntas") }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Pergunta
            OutlinedTextField(
                value = questionText,
                onValueChange = { questionText = it },
                label = { Text("Texto da Pergunta") },
                modifier = Modifier.fillMaxWidth()
            )

            // Opções
            QuestionOptionEntry(
                optionText = option1,
                onValueChange = { option1 = it },
                label = "Opção 1 (Correta)",
                isSelected = correctAnswerIndex == 0,
                onSelect = { correctAnswerIndex = 0 }
            )
            QuestionOptionEntry(
                optionText = option2,
                onValueChange = { option2 = it },
                label = "Opção 2",
                isSelected = correctAnswerIndex == 1,
                onSelect = { correctAnswerIndex = 1 }
            )
            QuestionOptionEntry(
                optionText = option3,
                onValueChange = { option3 = it },
                label = "Opção 3",
                isSelected = correctAnswerIndex == 2,
                onSelect = { correctAnswerIndex = 2 }
            )
            QuestionOptionEntry(
                optionText = option4,
                onValueChange = { option4 = it },
                label = "Opção 4",
                isSelected = correctAnswerIndex == 3,
                onSelect = { correctAnswerIndex = 3 }
            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    scope.launch {
                        val success = saveCurrentQuestion()
                        if (success) {
                            Toast.makeText(context, "Pergunta salva!", Toast.LENGTH_SHORT).show()
                            clearFields()
                        } else {
                            Toast.makeText(context, "Falha ao salvar. Preencha todos os campos.", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Salvar e Adicionar Outra")
            }

            OutlinedButton(
                onClick = {
                    scope.launch {
                        val options = listOf(option1, option2, option3, option4)

                        if (questionText.isNotBlank() || options.any { it.isNotBlank() }) {
                            val success = saveCurrentQuestion() // Tenta salvar

                            if (!success) {
                                Toast.makeText(context, "Campos inválidos. Pergunta descartada.", Toast.LENGTH_SHORT).show()
                            }

                        }


                        navController.navigate("quizList") {
                            popUpTo("quizList") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finalizar e Voltar")
            }
        }
    }
}

@Composable
private fun QuestionOptionEntry(
    optionText: String,
    onValueChange: (String) -> Unit,
    label: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        RadioButton(selected = isSelected, onClick = onSelect)
        OutlinedTextField(
            value = optionText,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.weight(1f)
        )
    }
}