package com.example.vladislav.androidstudy.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Точка входа в туториал, демонстрирующий ключевые
 * концепции Jetpack Compose с интерактивными примерами.
 *
 * ОСНОВНЫЕ ВОЗМОЖНОСТИ:
 * - 📝 Демонстрация UI-компонентов (Text, Button, TextField, и др.)
 * - 📐 Примеры раскладки (Column, Row, Box, LazyColumn)
 * - 💾 Управление состоянием (remember, rememberSaveable)
 * - 🚀 Работа с корутинами (rememberCoroutineScope)
 * - 🎬 Анимации и переходы
 * - 🏗️ Каркас экрана (Scaffold)
 *
 * НАВИГАЦИЯ:
 * - Главный экран → Полный список примеров
 * - Клик по "Scaffold" → Демонстрация Scaffold
 * - Кнопка "Назад" → Возврат на главный экран
 *
 * ТЕМА: Material Design 3 (тёмная тема)
 */
class ComposeTutorialActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var currentScreen by remember { mutableStateOf("main") }

            MaterialTheme(
                colorScheme = darkColorScheme(),
                typography = Typography(
                    titleLarge = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                ),
                shapes = Shapes(
                    medium = RoundedCornerShape(16.dp)
                )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    when (currentScreen) {
                        "main" -> {
                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        title = { Text("Compose Tutorial") },
                                        navigationIcon = {
                                            IconButton(onClick = { /* Назад */ }) {
                                                Icon(
                                                    Icons.Default.ArrowBack,
                                                    contentDescription = "Назад"
                                                )
                                            }
                                        }
                                    )
                                }
                            ) { paddingValues ->
                                TutorialContent(
                                    modifier = Modifier.padding(paddingValues),
                                    onOpenScaffold = { currentScreen = "scaffold" }
                                )
                            }
                        }
                        "scaffold" -> {
                            ScaffoldExample(
                                onBack = { currentScreen = "main" }
                            )
                        }
                    }
                }
            }
        }
    }
}