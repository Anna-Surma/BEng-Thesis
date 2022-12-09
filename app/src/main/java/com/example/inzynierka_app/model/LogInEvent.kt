package com.example.inzynierka_app.model

data class LogInEvent(
    var canLogIn: Boolean,
    var token: String?
)