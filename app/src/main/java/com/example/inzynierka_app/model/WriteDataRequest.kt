package com.example.inzynierka_app.model

data class WriteDataRequest(
    var id: Int,
    var jsonrpc: String,
    var method: String,
    var params: ParamsWrite
)