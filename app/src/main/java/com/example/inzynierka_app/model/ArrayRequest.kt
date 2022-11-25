package com.example.inzynierka_app

class ArrayRequest : ArrayList<ArrayRequestItem>()

data class ArrayRequestItem(
    val id: Int,
    val jsonrpc: String,
    val method: String,
    val params: ArrayParams
)

data class ArrayParams(
    val `var`: String
)