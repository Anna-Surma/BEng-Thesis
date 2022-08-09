package com.example.inzynierka_app.model

data class LoginRequest(
    var id: Int,
    var jsonrpc: String,
    var method: String,
    var params : Params
)

data class Params (
    var user: String,
    var password: String)