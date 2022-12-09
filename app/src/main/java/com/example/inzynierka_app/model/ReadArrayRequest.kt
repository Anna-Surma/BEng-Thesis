package com.example.inzynierka_app

import com.example.inzynierka_app.model.ParamsRead

class ReadArrayRequest : ArrayList<ReadArrayRequestItem>()

data class ReadArrayRequestItem(
    val id: Int,
    val jsonrpc: String,
    val method: String,
    val params: ParamsRead
)