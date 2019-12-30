package com.example.kotlinweatherapp.domain.commands

interface Command<out T> {
    fun execute():T
}