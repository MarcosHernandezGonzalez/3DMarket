package com.example.a3dmarket

import android.util.Log
import com.example.a3dmarket.TursoDatabase
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.sql.Connection

class Repository {
    suspend fun getOrders(): List<Order> {
        val db = Firebase.firestore
        Log.d("Repository", "------------- GETTING ORDERS -------------")
        return try {
            val querySnapshot = db.collection("orders").get().await()
            val orders = querySnapshot.map { document ->
                Order(
                    document.id,
                    document.getString("title") ?: "",
                    document.getString("desc") ?: "",
                    document.getString("imagen") ?: "",
                    document.getString("archivo") ?: "",
                    document.getString("fecha") ?: "",
                    document.getBoolean("estado") ?: false,
                    document.getString("usuario") ?: ""
                )
            }
            Log.d("Repository", "Orders: ${orders.size}")
            orders
        } catch (e: Exception) {
            Log.e("Repository", "Error getting orders", e)
            emptyList()
        }
    }
    suspend fun getOrder(id: String): Order {
        Log.d("Repository", "------------- GET ORDER -------------")
        val db = Firebase.firestore
        return try {
            val document = db.collection("orders").document(id)
            val data = document.get().await()
            Order(
                id = document.id,
                title = data.getString("title") ?: "",
                desc = data.getString("desc") ?: "",
                imagen = data.getString("imagen") ?: "",
                archivo = data.getString("archivo") ?: "",
                fecha = data.getString("fecha") ?: "",
                estado = data.getBoolean("estado") ?: false,
                usuario = data.getString("usuario") ?: ""
            )
        } catch (e: Exception) {
            Log.e("Repository", "Error getting order", e)
            Order("", "", "", "", "", "", false, "")
        }
    }
    fun createOrder(order: Order) {
        Log.d("Repository", "------------- CREATE ORDER -------------")
        try {
            val db = Firebase.firestore
            db.collection("orders").add(order)
            Log.d("Repository", "Order created")
        } catch (e: Exception) {
            Log.e("Repository", "Error creating order", e)
        }
    }
    suspend fun existeValorEnColeccion(
        coleccion: String,
        campo: String,
        valor: String
    ): Boolean {
        val db = Firebase.firestore
        return try {
            val querySnapshot = db.collection(coleccion)
                .whereEqualTo(campo, valor)
                .limit(1)
                .get()
                .await()
            !querySnapshot.isEmpty
        } catch (e: Exception) {
            Log.e("Repository", "Error checking value existence", e)
            false
        }
    }
    fun registerUser(
        name: String,
        email: String,
        pass: String,
        printerInfo: String,
        dir: String,
        isMaker: Boolean,
        showEmail: Boolean,
        showLocation: Boolean
    ) {
        Log.d("Repository", "------------- REGISTER USER -------------")
        try {
            val db = Firebase.firestore
            val user = User(
                "",
                name,
                email,
                pass,
                printerInfo,
                dir,
                isMaker,
                showEmail,
                showLocation
            )
            db.collection("users").add(user)
            Log.d("Repository", "User created")
        } catch (e: Exception) {
            Log.e("Repository", "Error creating user", e)
        }
    }

    suspend fun login(email: String, pass: String): List<User>? {
        Log.d("Repository", "------------- LOGIN -------------")
        try {
            val db = Firebase.firestore
            val querySnapshot = db.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("pass", pass)
                .get()
                .await()
            if (querySnapshot.isEmpty) {
                return null
            } else {
                val user = querySnapshot.map { document ->
                    User(
                        document.id,
                        document.getString("name") ?: "",
                        document.getString("email") ?: "",
                        document.getString("pass") ?: "",
                        document.getString("printerInfo") ?: "",
                        document.getString("dir") ?: "",
                        document.getBoolean("isMaker") ?: false,
                        document.getBoolean("showEmail") ?: false,
                        document.getBoolean("showLocation") ?: false
                    )
                }
                Log.d("Repository", "User logged in")
                return user
            }
        } catch (e: Exception) {
            Log.e("Repository", "Error logging in user", e)
            return null
        }
    }

    suspend fun getUserInfo(id: String): User? {
        Log.d("Repository", "------------- GET USER INFO -------------")
        try {
            val db = Firebase.firestore
            val document = db.collection("users").document(id)
            val data = document.get().await()
            return User(
                id = document.id,
                name = data.getString("name") ?: "",
                email = data.getString("email") ?: "",
                pass = data.getString("pass") ?: "",
                printerInfo = data.getString("printerInfo") ?: "",
                dir = data.getString("dir") ?: "",
                isMaker = data.getBoolean("isMaker") ?: false,
                showEmail = data.getBoolean("showEmail") ?: false,
                showLocation = data.getBoolean("showLocation") ?: false
            )
        } catch (e: Exception) {
            Log.e("Repository", "Error getting user info", e)
            return null
        }
    }

}
