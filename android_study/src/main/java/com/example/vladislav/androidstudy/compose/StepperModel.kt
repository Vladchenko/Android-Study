package com.example.vladislav.androidstudy.compose

import androidx.compose.ui.graphics.Color


/**
 * Модель виджета степпер
 *
 * @property number Номер пункта
 * @property title Заголовок виджета
 * @property subtitle Подзаголовок виджета
 * @property numberBackgroundColor цвет фона номера
 * @property isLastClause флаг последнего пункта
 *      true - пункт последний
 *      false - пункт не последний
 */
data class StepperModel(
    val number: String,
    val title: String,
    val subtitle: String,
    val numberBackgroundColor: Color,
    val isLastClause: Boolean
)
