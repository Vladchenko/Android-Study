package com.example.vladislav.androidstudy.kotlin.models

/**
 * Модель ФИО
 *
 * @param name имя
 * @param lastName фамилия
 * @param paternal отчество
 *
 * @author Yanchenko Vladislav
 * @since 06.02.2021
 */
data class FIO(
    val name:String,
    val lastName:String,
    val paternal:String,
)
