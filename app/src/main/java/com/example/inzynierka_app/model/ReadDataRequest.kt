package com.example.inzynierka_app.model

import com.google.gson.annotations.SerializedName

data class ReadDataRequest(var jsonrpc: String,
                           var method: String,
                           var id: Int,
                           var params: ParamsVar)

data class ParamsVar(@SerializedName("var")
                      var params_var:String)