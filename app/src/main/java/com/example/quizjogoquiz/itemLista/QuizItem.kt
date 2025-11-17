package com.example.quizjogoquiz.itemLista

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizjogoquiz.model.Quiz

@Composable
fun QuizItem(
    quiz: Quiz,
    onQuizClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onQuizClick(quiz.id) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(quiz.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(quiz.description, fontSize = 14.sp)
        }
    }
}