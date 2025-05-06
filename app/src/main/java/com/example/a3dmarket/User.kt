package com.example.a3dmarket

data class User(
    var id: String,
    var name: String,
    var email: String,
    var pass: String,
    var printerInfo: String,
    var dir: String,
    var isMaker: Boolean,
    var showEmail: Boolean,
    var showLocation: Boolean
)
