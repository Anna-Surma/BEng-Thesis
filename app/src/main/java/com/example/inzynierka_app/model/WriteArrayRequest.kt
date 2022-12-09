package com.example.inzynierka_app.model

class WriteArrayRequest : ArrayList<WriteArrayRequestItem>()

data class WriteArrayRequestItem(
    val id: Int,
    val jsonrpc: String,
    val method: String,
    val params: ParamsWrite
)