package com.example.vladislav.androidstudy.compose

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ============================================================
 * JETPACK COMPOSE - ПОЛНОЕ РУКОВОДСТВО
 * ============================================================
 *
 * Jetpack Compose - современный UI-фреймворк для Android
 * на основе декларативного программирования.
 *
 * ОСНОВНЫЕ КОНЦЕПЦИИ:
 * - @Composable - функция, описывающая UI
 * - State - данные, управляющие UI
 * - Recomposition - перерисовка при изменении состояния
 * - Modifier - настройка внешнего вида и поведения
 * - CompositionLocal - неявная передача данных
 *
 * КЛЮЧЕВЫЕ ФУНКЦИИ ДЛЯ РАБОТЫ СО СОСТОЯНИЕМ:
 * - remember — сохранение между перекомпозициями
 * - rememberSaveable — сохранение при повороте
 * - rememberCoroutineScope — доступ к корутинам
 * - rememberUpdatedState — работа с обновляемыми значениями
 * - derivedStateOf — производное состояние
 * - produceState — создание состояния из внешних источников
 * - snapshotFlow — преобразование состояния в Flow
 *
 * UI КОМПОНЕНТЫ:
 * - Layout: Column, Row, Box, LazyColumn, LazyRow
 * - Text: стили, шрифты, цвета, выравнивание
 * - Buttons: Button, IconButton, FloatingActionButton
 * - Input: TextField, OutlinedTextField, BasicTextField
 * - Images: Image, painterResource, colorFilter
 * - Cards, Scaffold, Surface
 *
 * ЗАВИСИМОСТИ (в build.gradle):
 * implementation platform('androidx.compose:compose-bom:2024.10.00')
 * implementation 'androidx.compose.ui:ui'
 * implementation 'androidx.compose.material3:material3'
 * implementation 'androidx.compose.ui:ui-tooling-preview'
 * implementation 'androidx.activity:activity-compose:1.9.0'
 * implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0'
 * implementation 'androidx.compose.runtime:runtime-livedata'
 * ============================================================
 */

/**
 * 1.1. 📝 ТЕКСТ (TEXT)
 *
 * Базовый компонент для отображения текста.
 */
@Composable
fun TextExamples() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Обычный текст",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Жирный текст",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = "Курсивный текст",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontStyle = FontStyle.Italic
            )
        )

        Text(
            text = "Моноширинный текст",
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Цветной текст",
            color = Color.Cyan,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Текст с размером 20.sp",
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Текст с подчёркиванием",
            style = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = TextDecoration.Underline
            )
        )

        Text(
            text = "Зачёркнутый текст",
            style = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = TextDecoration.LineThrough
            )
        )

        Text(
            text = "Текст с тенью",
            style = MaterialTheme.typography.bodyLarge.copy(
                shadow = Shadow(
                    color = Color.Red,
                    offset = Offset(2f, 2f),
                    blurRadius = 8f
                )
            )
        )

        // ✅ Многострочный текст
        Text(
            text = "Это очень длинный текст, который занимает несколько строк и демонстрирует, как работает перенос в Compose",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        // ✅ Текст с выравниванием
        Text(
            text = "Выравнивание по центру",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // ✅ AnnotatedString (стилизованный текст)
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("Красный ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("жирный ")
                }
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                    append("курсивный ")
                }
                append("обычный текст")
            },
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

/**
 * 2.1. 🎯 КНОПКИ (BUTTONS)
 *
 * Различные виды кнопок в Material Design 3.
 */
@Composable
fun ButtonExamples() {
    var clickCount by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Нажатий: $clickCount",
            style = MaterialTheme.typography.titleMedium
        )

        // ✅ Обычная кнопка
        Button(
            onClick = { clickCount++ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Обычная кнопка")
        }

        // ✅ Кнопка с иконкой
        Button(
            onClick = { clickCount++ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = "Добавить")
            Spacer(modifier = Modifier.width(8.dp))
            Text("С иконкой")
        }

        // ✅ Текстовая кнопка
        TextButton(
            onClick = { clickCount++ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Текстовая кнопка")
        }

        // ✅ Кнопка с обводкой
        OutlinedButton(
            onClick = { clickCount++ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Кнопка с обводкой")
        }

        // ✅ Плавающая кнопка
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FloatingActionButton(
                onClick = { clickCount++ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }

            SmallFloatingActionButton(
                onClick = { clickCount++ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Редактировать")
            }

            LargeFloatingActionButton(
                onClick = { clickCount++ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Save, contentDescription = "Сохранить")
            }
        }

        // ✅ Кнопка с состоянием загрузки
        Button(
            onClick = {
                isLoading = true
                coroutineScope.launch {
                    delay(2000)
                    isLoading = false
                    clickCount++
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Загрузка...")
            } else {
                Icon(Icons.Default.Refresh, contentDescription = "Обновить")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Загрузить")
            }
        }

        // ✅ Кнопка с кастомным цветом
        Button(
            onClick = { clickCount++ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("Красная кнопка")
        }
    }
}

/**
 * 3.1. ✏️ ВВОД ТЕКСТА (TEXT FIELDS)
 *
 * Поля ввода текста с Material Design 3.
 */
@Composable
fun TextFieldExamples() {
    var text by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var multiLineText by remember { mutableStateOf("") }
    var numberText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ✅ OutlinedTextField (рекомендуется)
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Обычное поле") },
            placeholder = { Text("Введите текст...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // ✅ TextField с иконками
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("С иконками") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Поиск")
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = { text = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Очистить")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // ✅ Поле для пароля
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Скрыть" else "Показать"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // ✅ Многострочное поле
        OutlinedTextField(
            value = multiLineText,
            onValueChange = { multiLineText = it },
            label = { Text("Многострочный текст") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5,
            minLines = 3
        )

        // ✅ Поле для чисел
        OutlinedTextField(
            value = numberText,
            onValueChange = { numberText = it },
            label = { Text("Только числа") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = VisualTransformation.None
        )

        // ✅ Закрытое поле (disabled)
        OutlinedTextField(
            value = "Недоступно для редактирования",
            onValueChange = {},
            label = { Text("Отключено") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        // ✅ Текущие значения
        Text(
            text = "Введено: $text",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

/**
 * 4.1. 📐 РАСКЛАДКА (LAYOUT)
 *
 * Основные компоненты для организации UI:
 * - Column — вертикальное расположение
 * - Row — горизонтальное расположение
 * - Box — расположение друг над другом (стек)
 */
@Composable
fun LayoutExamples() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "📐 COLUMN — вертикальное расположение",
            style = MaterialTheme.typography.titleMedium
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue.copy(alpha = 0.1f))
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.Green)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.Blue)
            )
        }

        Text(
            text = "📐 ROW — горизонтальное расположение",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue.copy(alpha = 0.1f))
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(Color.Green)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(Color.Blue)
            )
        }

        Text(
            text = "📐 BOX — расположение друг над другом (стек)",
            style = MaterialTheme.typography.titleMedium
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.Gray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
                    .background(Color.Green)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(48.dp)
                    .background(Color.Blue)
            )
            Text(
                text = "Поверх всего",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "📐 ARRANGEMENT — различные варианты выравнивания",
            style = MaterialTheme.typography.titleMedium
        )

        // ✅ Выравнивание по центру
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue.copy(alpha = 0.1f))
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            listOf("A", "B", "C").forEach {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Red)
                        .padding(4.dp)
                ) {
                    Text(it, color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
            }
        }

        // ✅ Распределение по краям
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue.copy(alpha = 0.1f))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Левый", "Центр", "Правый").forEach {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .background(Color.Red)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(it, color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
            }
        }

        // ✅ Распределение с равными промежутками
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue.copy(alpha = 0.1f))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("1", "2", "3", "4").forEach {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Red)
                        .padding(4.dp)
                ) {
                    Text(it, color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

/**
 * 5.1. 📋 СПИСКИ (LAZY COLUMN / LAZY ROW)
 *
 * Ленивые списки для эффективной работы с большими данными.
 */
/**
 * 5.1. 📋 СПИСКИ (LAZY COLUMN / LAZY ROW)
 */
@Composable
fun LazyListExamples() {
    val items = List(50) { "Элемент ${it + 1}" }
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "📋 LAZY COLUMN — вертикальный список",
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.background)
                .border(1.dp, Color.Gray)
        ) {
            items(items) { item ->
                ListItem(
                    headlineContent = { Text(item) },
                    leadingContent = {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.Green
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = { /* Действие */ }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Меню")
                        }
                    }
                )
            }
        }

        Text(
            text = "📋 LAZY ROW — горизонтальный список",
            style = MaterialTheme.typography.titleMedium
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.background)
                .border(1.dp, Color.Gray)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.take(20)) { item ->
                Card(
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.random()
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = item,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Text(
            text = "📋 LAZY VERTICAL GRID — сетка",
            style = MaterialTheme.typography.titleMedium
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // ✅ ОБЯЗАТЕЛЬНО!
                .background(MaterialTheme.colorScheme.background)
                .border(1.dp, Color.Gray)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.take(12)) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.random()
                    )
                ) {
                    Text(
                        text = item,
                        color = Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * 6.1. 🃏 КАРТОЧКИ (CARDS)
 *
 * Карточки для группировки контента с Material Design.
 */
@Composable
fun CardExamples() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ✅ Обычная карточка
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Заголовок карточки",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Это содержимое обычной карточки с Material Design 3",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // ✅ Карточка с изображением
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.Cyan.copy(alpha = 0.3f))
                ) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = "Изображение",
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.Center)
                    )
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Карточка с изображением",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "В карточке может быть изображение, текст и кнопки",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { /* Действие */ }) {
                            Text("Кнопка")
                        }
                    }
                }
            }
        }

        // ✅ Кликабельная карточка
        var clickCount by remember { mutableStateOf(0) }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { clickCount++ },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp,
                pressedElevation = 8.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Звезда",
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Кликабельная карточка (нажатий: $clickCount)",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // ✅ Карточка с тенью
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Карточка с сильной тенью и скруглёнными углами",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * 7.1. 🖼️ ИЗОБРАЖЕНИЯ (IMAGES)
 *
 * Отображение изображений из различных источников.
 */
@Composable
fun ImageExamples() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "🖼️ ИЗОБРАЖЕНИЯ",
            style = MaterialTheme.typography.titleMedium
        )

        // ✅ Из вектора (Material Icons)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Избранное",
                modifier = Modifier.size(48.dp)
            )
            Image(
                imageVector = Icons.Default.Star,
                contentDescription = "Звезда",
                modifier = Modifier.size(48.dp),
                colorFilter = ColorFilter.tint(Color.Yellow)
            )
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Профиль",
                modifier = Modifier.size(48.dp),
                colorFilter = ColorFilter.tint(Color.Cyan)
            )
        }

        // ✅ Из ресурсов
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_camera),
                contentDescription = "Камера",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = "Галерея",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_share),
                contentDescription = "Поделиться",
                modifier = Modifier
                    .size(64.dp)
                    .rotate(45f)
            )
        }

        // ✅ С кастомными свойствами
        Image(
            imageVector = Icons.Default.Home,
            contentDescription = "Дом",
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    shape = CircleShape
                )
                .padding(16.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        // ✅ Загрузка изображения (симуляция)
        var imageLoaded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = if (imageLoaded) Color.Green else Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { imageLoaded = !imageLoaded }
        ) {
            if (imageLoaded) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Загружено",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    tint = Color.White
                )
            } else {
                Text(
                    text = "Нажмите",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

/**
 * 8.1. 💬 ДИАЛОГИ (DIALOGS)
 *
 * Диалоговые окна для взаимодействия с пользователем.
 */
@Composable
fun DialogExamples() {
    var showAlertDialog by remember { mutableStateOf(false) }
    var showCustomDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "💬 ДИАЛОГИ",
            style = MaterialTheme.typography.titleMedium
        )

        // ✅ Кнопка для показа AlertDialog
        Button(
            onClick = { showAlertDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Показать AlertDialog")
        }

        // ✅ Кнопка для показа кастомного диалога
        Button(
            onClick = { showCustomDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Показать кастомный диалог")
        }

        // ✅ AlertDialog
        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                title = { Text("Заголовок диалога") },
                text = { Text("Это пример AlertDialog с подтверждением действия") },
                icon = {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.Cyan
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showAlertDialog = false
                            // Действие подтверждения
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAlertDialog = false }) {
                        Text("Отмена")
                    }
                }
            )
        }

        // ✅ Кастомный диалог
        if (showCustomDialog) {
            Dialog(
                onDismissRequest = { showCustomDialog = false }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 16.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Green
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Успешно!",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Это кастомный диалог с произвольным содержимым",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { showCustomDialog = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Закрыть")
                        }
                    }
                }
            }
        }
    }
}


/**
 * 9.1. 🎬 АНИМАЦИИ (ANIMATIONS)
 *
 * Анимации в Compose для плавного UI.
 */
@Composable
fun AnimationExamples() {
    var visible by remember { mutableStateOf(true) }
    var sizeState by remember { mutableStateOf(48.dp) }
    var rotationState by remember { mutableStateOf(0f) }

    val animatedSize by animateDpAsState(
        targetValue = sizeState,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )

    val animatedRotation by animateFloatAsState(
        targetValue = rotationState,
        animationSpec = tween(
            durationMillis = 1000,
            easing = EaseInOut
        )
    )

    val animatedColor by animateColorAsState(
        targetValue = if (visible) Color.Cyan else Color.Red,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "🎬 АНИМАЦИИ",
            style = MaterialTheme.typography.titleMedium
        )

        // ✅ Анимированное появление
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Анимированный контент",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { visible = !visible }) {
                Text(if (visible) "Скрыть" else "Показать")
            }
        }

        // ✅ Анимация размера
        Box(
            modifier = Modifier
                .size(animatedSize)
                .background(Color.Blue, RoundedCornerShape(8.dp))
        )

        Button(
            onClick = {
                sizeState = if (sizeState == 48.dp) 80.dp else 48.dp
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Изменить размер")
        }

        // ✅ Анимация вращения
        Box(
            modifier = Modifier
                .size(80.dp)
                .rotate(animatedRotation)
                .background(Color.Green, RoundedCornerShape(8.dp))
        )

        Button(
            onClick = {
                rotationState += 360f
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Повернуть")
        }

        // ✅ Анимация цвета
        Text(
            text = "Анимированный цвет",
            color = animatedColor,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

/**
 * 10.1. 🏗️ SCAFFOLD — КАРКАС ЭКРАНА
 *
 * Scaffold предоставляет структуру экрана с:
 * - TopAppBar (верхний бар)
 * - BottomAppBar (нижний бар)
 * - FloatingActionButton (плавающая кнопка)
 * - Snackbar (уведомления)
 * - Drawer (боковое меню)
 */
@ExperimentalMaterial3Api
@Composable
fun ScaffoldExample(
    onBack: () -> Unit = {}
) {
    var showSnackbar by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    BackHandler {
        onBack() // Возврат на главный
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scaffold Example") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Menu, contentDescription = "Меню")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Поиск */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Поиск")
                    }
                    IconButton(onClick = { /* Уведомления */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Уведомления")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Кнопка нажата!")
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            NavigationBar {
                listOf(
                    "Главная" to Icons.Default.Home,
                    "Поиск" to Icons.Default.Search,
                    "Профиль" to Icons.Default.Person
                ).forEach { (label, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = false,
                        onClick = { /* Переключение вкладок */ }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Содержимое экрана",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Scaffold предоставляет готовый каркас",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ============================================================
// 1️⃣1️⃣ REMEMBER — СОХРАНЕНИЕ СОСТОЯНИЯ
// ============================================================

/**
 * 11.1. ❌ ПРИМЕР БЕЗ REMEMBER
 */
@Composable
fun CounterWithoutRemember() {
    var count = 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Red.copy(alpha = 0.1f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "❌ БЕЗ REMEMBER",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Red
        )
        Text(
            text = "Счётчик: $count",
            style = MaterialTheme.typography.headlineLarge
        )
        Button(
            onClick = { count++ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Увеличить (не работает)")
        }
        Text(
            text = "⚠️ При каждой перекомпозиции count = 0",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

/**
 * 11.2. ✅ ПРИМЕР С REMEMBER
 */
@Composable
fun CounterWithRemember() {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Green.copy(alpha = 0.1f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "✅ С REMEMBER",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Green
        )
        Text(
            text = "Счётчик: $count",
            style = MaterialTheme.typography.headlineLarge
        )
        Button(
            onClick = { count++ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Увеличить")
        }
        Text(
            text = "✅ remember сохраняет значение",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

/**
 * 11.3. 💾 REMEMBER SAVEABLE
 */
@Composable
fun RememberSaveableExample() {
    var text by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Blue.copy(alpha = 0.1f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "💾 REMEMBER SAVEABLE",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Blue
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Введите текст") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Текст сохранится при повороте") }
        )
        Text(
            text = "💾 Сохраняется при повороте",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

/**
 * 11.4. 🎯 REMEMBER С КЛЮЧАМИ
 */
@Composable
fun RememberWithKeysExample() {
    var filterText by remember { mutableStateOf("") }
    var multiplier by remember { mutableStateOf(1) }

    val filteredList = remember(filterText) {
        listOf("Apple", "Banana", "Cherry", "Date", "Elderberry")
            .filter { it.contains(filterText, ignoreCase = true) }
    }

    val expensiveCalculation = remember(multiplier) {
        (1..1000000).sum() * multiplier
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Magenta.copy(alpha = 0.1f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "🎯 REMEMBER С КЛЮЧАМИ",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Magenta
        )
        OutlinedTextField(
            value = filterText,
            onValueChange = { filterText = it },
            label = { Text("Фильтр") },
            modifier = Modifier.fillMaxWidth()
        )
        Text("Результат: ${filteredList.joinToString()}")

        Row {
            Text("Множитель: $multiplier")
            Button(onClick = { multiplier++ }) {
                Text("+")
            }
        }
        Text("Вычисление: $expensiveCalculation")
        Text(
            text = "💡 Пересчёт только при изменении ключей",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

// ============================================================
// 1️⃣2️⃣ REMEMBER COROUTINE SCOPE
// ============================================================

@Composable
fun RememberCoroutineScopeExample() {
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("Нажмите кнопку") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Cyan.copy(alpha = 0.1f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "🚀 REMEMBER COROUTINE SCOPE",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Cyan
        )
        Text(text = text)
        if (isLoading) CircularProgressIndicator()
        Button(
            onClick = {
                coroutineScope.launch {
                    isLoading = true
                    text = "⏳ Загрузка..."
                    delay(2000)
                    text = "✅ Загрузка завершена!"
                    isLoading = false
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Загрузка..." else "Запустить корутину")
        }
        Text(
            text = "✅ Корутина привязана к жизненному циклу",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

// ============================================================
// 1️⃣3️⃣ DERIVED STATE OF
// ============================================================

@Composable
fun DerivedStateOfExample() {
    var items by remember { mutableStateOf(listOf("Apple", "Banana", "Cherry", "Date")) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredItems by remember {
        derivedStateOf {
            items.filter { it.contains(searchQuery, ignoreCase = true) }
        }
    }

    val longItemsCount by remember {
        derivedStateOf {
            filteredItems.count { it.length > 5 }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Yellow.copy(alpha = 0.1f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "📊 DERIVED STATE OF",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Yellow
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Поиск") },
            modifier = Modifier.fillMaxWidth()
        )
        Text("Всего: ${items.size} | Найдено: ${filteredItems.size} | Длинных: $longItemsCount")
        LazyColumn(
            modifier = Modifier
                .height(150.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            items(filteredItems) { item ->
                Text(
                    text = item,
                    modifier = Modifier.padding(8.dp),
                    color = if (item.length > 5) Color.Red else Color.Unspecified
                )
            }
        }
        Text(
            text = "💡 Пересчёт только при изменении зависимостей",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

// ============================================================
// 1️⃣4️⃣ PRODUCE STATE
// ============================================================

@Composable
fun ProduceStateExample() {
    val currentTime by produceState(initialValue = "Загрузка...", producer = {
        while (true) {
            value = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            delay(1000)
        }
    })

    val counter by produceState(initialValue = 0, producer = {
        flow {
            var i = 0
            while (true) {
                emit(i++)
                delay(2000)
            }
        }.collect { value = it }
    })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Gray.copy(alpha = 0.1f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "⏱️ PRODUCE STATE",
            style = MaterialTheme.typography.titleMedium
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Текущее время: $currentTime")
                Text("Счётчик: $counter", color = Color.Cyan)
            }
        }
        Text(
            text = "⏱️ Автоматическое обновление из корутин",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

// ============================================================
// 1️⃣5️⃣ SNAPSHOT FLOW
// ============================================================

@Composable
fun SnapshotFlowExample() {
    var searchQuery by remember { mutableStateOf("") }
    var results by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        snapshotFlow { searchQuery }
            .debounce(500)
            .distinctUntilChanged()
            .collect { query ->
                delay(100)
                results = "Результаты для: '$query'"
            }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Cyan.copy(alpha = 0.1f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "🌊 SNAPSHOT FLOW",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Cyan
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Поиск с debounce 500ms") },
            modifier = Modifier.fillMaxWidth()
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Text(text = results, modifier = Modifier.padding(16.dp))
        }
        Text(
            text = "🌊 snapshotFlow + debounce + distinctUntilChanged",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

// ============================================================
// 1️⃣6️⃣ СРАВНЕНИЕ ПОДХОДОВ
// ============================================================

@Composable
fun RememberComparisonScreen() {
    var normalVar = 0
    var remembered by remember { mutableIntStateOf(0) }
    var saved by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "📊 Сравнение подходов",
            style = MaterialTheme.typography.titleMedium
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Red.copy(alpha = 0.1f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("❌ Обычная переменная", fontWeight = FontWeight.Bold, color = Color.Red)
                Text("Значение: $normalVar (сбрасывается)")
                Button(onClick = { normalVar++ }) {
                    Text("Увеличить (не работает)")
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Green.copy(alpha = 0.1f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("✅ remember", fontWeight = FontWeight.Bold, color = Color.Green)
                Text("Значение: $remembered (сохраняется)")
                Button(onClick = { remembered++ }) {
                    Text("Увеличить")
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Blue.copy(alpha = 0.1f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("💾 rememberSaveable", fontWeight = FontWeight.Bold, color = Color.Blue)
                Text("Значение: $saved (сохраняется при повороте)")
                Button(onClick = { saved++ }) {
                    Text("Увеличить")
                }
            }
        }
    }
}

// ============================================================
// 1️⃣7️⃣ ПОЛНЫЙ ЭКРАН ТУТОРИАЛА
// ============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialContent(
    modifier: Modifier = Modifier,
    onOpenScaffold: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "🔹 JETPACK COMPOSE — ПОЛНЫЙ ТУТОРИАЛ",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Все ключевые концепции с примерами",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Divider()

        // ============================================================
        // UI КОМПОНЕНТЫ
        // ============================================================

        Text(
            text = "📝 ТЕКСТ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        TextExamples()

        Divider()

        Text(
            text = "🎯 КНОПКИ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        ButtonExamples()

        Divider()

        Text(
            text = "✏️ ВВОД ТЕКСТА",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        TextFieldExamples()

        Divider()

        Text(
            text = "📐 РАСКЛАДКА (LAYOUT)",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        LayoutExamples()

        Divider()

        Text(
            text = "📋 СПИСКИ (LAZY LIST)",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        LazyListExamples()

        Divider()

        Text(
            text = "🃏 КАРТОЧКИ (CARDS)",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        CardExamples()

        Divider()

        Text(
            text = "🖼️ ИЗОБРАЖЕНИЯ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        ImageExamples()

        Divider()

        Text(
            text = "💬 ДИАЛОГИ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        DialogExamples()

        Divider()

        Text(
            text = "🎬 АНИМАЦИИ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        AnimationExamples()

        Divider()

        Text(
            text = "🏗️ SCAFFOLD",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOpenScaffold() } // Show Scaffold example in a separate screen, since this screen already has scaffold and having 2 crashes the app
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Divider()

        // ============================================================
        // REMEMBER И СОСТОЯНИЕ
        // ============================================================

        Text(
            text = "💾 REMEMBER — СОХРАНЕНИЕ СОСТОЯНИЯ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )

        CounterWithoutRemember()
        CounterWithRemember()
        RememberSaveableExample()
        RememberWithKeysExample()

        Divider()

        Text(
            text = "🚀 REMEMBER COROUTINE SCOPE",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        RememberCoroutineScopeExample()

        Divider()

        Text(
            text = "📊 DERIVED STATE OF",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        DerivedStateOfExample()

        Divider()

        Text(
            text = "⏱️ PRODUCE STATE",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        ProduceStateExample()

        Divider()

        Text(
            text = "🌊 SNAPSHOT FLOW",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        SnapshotFlowExample()

        Divider()

        Text(
            text = "📊 СРАВНЕНИЕ ПОДХОДОВ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        RememberComparisonScreen()

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ============================================================
// 1️⃣8️⃣ ПРЕДПРОСМОТРЫ
// ============================================================

@Preview(
    name = "Light Theme",
    showBackground = true
)
@Composable
fun PreviewTutorialContent() {
    MaterialTheme {
        val onOpenScaffold: () -> Unit = {}
        TutorialContent(onOpenScaffold = onOpenScaffold)
    }
}

@Preview(
    name = "Dark Theme",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun PreviewDarkTutorialContent() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        val onOpenScaffold: () -> Unit = {}
        TutorialContent(onOpenScaffold = onOpenScaffold)
    }
}

/**
 * ============================================================
 * ШПАРГАЛКА ПО JETPACK COMPOSE
 * ============================================================
 *
 * 🔹 UI КОМПОНЕНТЫ:
 *
 * 📝 ТЕКСТ:
 * Text(text, style, color, textAlign, maxLines, overflow)
 *
 * 🎯 КНОПКИ:
 * Button, TextButton, OutlinedButton, IconButton, FloatingActionButton
 *
 * ✏️ ВВОД:
 * TextField, OutlinedTextField, BasicTextField
 *
 * 📐 РАСКЛАДКА:
 * Column (вертикально), Row (горизонтально), Box (стек)
 *
 * 📋 СПИСКИ:
 * LazyColumn, LazyRow, LazyVerticalGrid
 *
 * 🃏 КАРТОЧКИ:
 * Card, ElevatedCard, OutlinedCard
 *
 * 🖼️ ИЗОБРАЖЕНИЯ:
 * Image(imageVector/painter, contentDescription)
 *
 * 💬 ДИАЛОГИ:
 * AlertDialog, Dialog
 *
 * 🎬 АНИМАЦИИ:
 * AnimatedVisibility, animateDpAsState, animateColorAsState, animateFloatAsState
 *
 * 🏗️ SCAFFOLD:
 * Scaffold(topBar, bottomBar, floatingActionButton, snackbarHost)
 *
 * ============================================================
 *
 * 🔹 СОСТОЯНИЕ И REMEMBER:
 *
 * ✅ remember { } — сохраняет между перекомпозициями
 * ✅ rememberSaveable { } — сохраняет при повороте
 * ✅ rememberCoroutineScope() — доступ к корутинам
 * ✅ rememberUpdatedState(value) — актуальное значение в эффектах
 * ✅ derivedStateOf { } — производное состояние
 * ✅ produceState(initial) { } — из внешних источников
 * ✅ snapshotFlow { state } — состояние в Flow
 * ✅ remember(key) { } — с ключами для оптимизации
 *
 * ============================================================
 *
 * 🔹 КОГДА ИСПОЛЬЗОВАТЬ:
 *
 * | Ситуация | Инструмент |
 * |----------|------------|
 * | Простое состояние UI | remember + mutableStateOf |
 * | Состояние при повороте | rememberSaveable |
 * | Запуск корутин в UI | rememberCoroutineScope |
 * | Долгоживущие эффекты | rememberUpdatedState |
 * | Производные данные | derivedStateOf |
 * | Внешние источники | produceState |
 * | Реагирование с debounce | snapshotFlow |
 * | Оптимизация вычислений | remember с ключами |
 *
 * ============================================================
 *
 * 🔹 MODIFIER (ЧАСТО ИСПОЛЬЗУЕМЫЕ):
 *
 * РАЗМЕР: .size(), .fillMaxSize(), .width(), .height()
 * ОТСТУПЫ: .padding(), .offset()
 * ВНЕШНИЙ ВИД: .background(), .border(), .shadow(), .clip()
 * ПОВЕДЕНИЕ: .clickable(), .focusable()
 * РАСКЛАДКА: .weight(), .align()
 *
 * ============================================================
 *
 * 🔹 ПРАВИЛА:
 * 1. ✅ Всегда передавайте Modifier как параметр
 * 2. ✅ Используйте remember для состояния UI
 * 3. ✅ Используйте rememberSaveable для данных при повороте
 * 4. ✅ Используйте LazyColumn для больших списков
 * 5. ✅ Всегда обрабатывайте состояния загрузки/ошибки
 * 6. ✅ Выносите сложные компоненты в отдельные функции
 * 7. ✅ Используйте MaterialTheme для стилизации
 * 8. ✅ Не смешивайте логику и UI в @Composable
 * ============================================================
 */