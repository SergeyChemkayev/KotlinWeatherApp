package com.example.kotlinweatherapp.domain.commands

interface Command<out T> {
    suspend fun execute():T
}