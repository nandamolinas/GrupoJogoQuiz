package com.example.quizjogoquiz.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.quizjogoquiz.repository.QuizRepository


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizListScreen(navController: NavController){
    val quizRepository = remember { QuizRepository() }

    val quizList = quizRepository.getAvailableQuizzes().collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        quizRepository.fetchQuizzes()
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text("App de Quiz") }) },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("createQuiz")
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Criar novo quiz")
            }
        }
    ){

        if (quizList.value.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
                Text("Carregando quizzes...")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                items(quizList.value) { quiz ->
                    QuizItem(quiz = quiz, onQuizClick = { selectedQuizId ->
                        navController.navigate("quizTaking/$selectedQuizId")
                    })
                }
            }
        }
    }



}