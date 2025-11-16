package com.example.quizjogoquiz.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ResultScreen(navController: NavController, score: Int, total: Int){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Quiz Finalizado!", fontSize = 32.sp)
        Text(
            "Você acertou $score de $total perguntas!",
            fontSize = 24.sp,
            modifier = Modifier.padding(32.dp)
        )

        Button(onClick = {
            // Volta para a tela inicial
            navController.navigate("quizList") {
                // Limpa a pilha de navegação
                popUpTo("quizList") { inclusive = true }
            }
        }) {
            Text("Jogar Novamente")
        }
    }
}