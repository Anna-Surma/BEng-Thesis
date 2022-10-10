package com.example.inzynierka_app.model

data class LoginRequest(
    var id: Int,
    var jsonrpc: String,
    var method: String,
    var params: LoginParams
)

data class LoginParams(
    var user: String,
    var password: String
)