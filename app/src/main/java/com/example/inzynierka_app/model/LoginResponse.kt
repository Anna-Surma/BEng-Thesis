package com.example.inzynierka_app.model

data class LoginResponse(
    var jsonrpc: String,
    var id: Int,
    var result: Result
)

data class Result(var token: String)