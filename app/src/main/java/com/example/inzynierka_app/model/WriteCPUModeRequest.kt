package com.example.inzynierka_app.model

data class WriteCPUModeRequest (
    var id: Int,
    var jsonrpc: String,
    var method: String,
    var params: ParamsCPUWrite
)

data class ParamsCPUWrite(
    val mode: String
)