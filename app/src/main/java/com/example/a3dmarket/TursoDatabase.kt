package com.example.a3dmarket

import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object TursoDatabase {
    private const val URL = "libsql://batabase-eusebiosorolla.turso.io"
    private const val AUTH_TOKEN = "eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJhIjoicnciLCJnaWQiOiJjMjc0ZTY1Yy04NzJkLTRjYTAtYThkMC05NjM2ODQ0OTVkMGUiLCJpYXQiOjE3NDM2NjI4NTB9.WAhHwxmAXyIuSwFlFKaFkNpIVDjFKg9ameIpFAuFKY8SBYozXuxRtON4RaoVRQXvl6L8NAlKjPlsKXqyf66zCg" // Tu token de autenticación

    fun connect(): Connection? {
        return try {
            DriverManager.getConnection(URL, "default", AUTH_TOKEN)
        } catch (e: SQLException) {
            Log.e("TursoDatabase", "Error al conectar con Turso", e)
            null
        }
    }

}
