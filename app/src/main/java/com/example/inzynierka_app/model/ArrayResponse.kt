package com.example.inzynierka_app

class ArrayResponse : ArrayList<ArrayResponseItem>()

data class ArrayResponseItem(
    val id: Int,
    val jsonrpc: String,
    val result: Boolean
)