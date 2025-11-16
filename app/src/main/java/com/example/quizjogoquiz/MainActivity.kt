package com.example.quizjogoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizjogoquiz.view.QuizTakingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "quizList" // Nanda:deixa a tela 1 como principal
            ) {
                // Rota para a Tela 1: Lista de Quizzes
                composable(route = "quizList") {
                    QuizListScreen(navController = navController)
                }
                //esta parte te leva para a tela 2
                composable(
                    route = "quizTaking/{quizId}",
                    arguments = listOf(navArgument("quizId") {
                        type = NavType.Companion.StringType
                    })
                ) { backStackEntry ->
                    val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
                    QuizTakingScreen(navController = navController, quizId = quizId)
                }
                //Tela 3
                composable(
                    route = "quizResults/{score}/{total}",
                    arguments = listOf(
                        navArgument("score") { type = NavType.Companion.IntType },
                        navArgument("total") { type = NavType.Companion.IntType }
                    )

                ) { backStackEntry ->
                    val score = backStackEntry.arguments?.getInt("score") ?: 0
                    val total = backStackEntry.arguments?.getInt("total") ?: 0
                    ResultScreen(navController = navController, score = score, total = total)
                }
                composable(route = "createQuiz") {
                    CreateQuizScreen(navController = navController)
                }

                composable(
                    route = "addQuestion/{quizId}",
                    arguments = listOf(navArgument("quizId") {
                        type = NavType.Companion.StringType
                    })
                ) { backStackEntry ->
                    val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
                    AddQuestionScreen(navController = navController, quizId = quizId)
                }

            }


        }
    }
}