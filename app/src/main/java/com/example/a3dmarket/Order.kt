package com.example.a3dmarket

data class Order(
    var id: String,
    var title: String,
    var desc: String,
    var imagen: String,
    var archivo: String,
    var fecha: String,
    var estado: Boolean,
    var usuario: String
)
