package com.example.a3dmarket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dmarket.Order
import com.example.a3dmarket.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CatalogoVM: ViewModel() {
    private val repository = Repository()
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders
    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> get() = _order
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user
    private val _userId = MutableLiveData<String?>()
    val userId: MutableLiveData<String?> get() = _userId
    private val _userInfo = MutableLiveData<User?>()
    val userInfo: MutableLiveData<User?> get() = _userInfo

    fun fetchOrders() {
        Log.d("CatalogoVM", "------------- FETCHING ORDERS -------------")
        viewModelScope.launch(Dispatchers.IO) {
            val orderList = repository.getOrders()
            _orders.postValue(orderList)
        }
    }
    fun getOrder(id: String) {
        Log.d("CatalogoVM", "------------- GETTING ORDER -------------")
        viewModelScope.launch(Dispatchers.IO) {
            val order = repository.getOrder(id)
            _order.postValue(order)
        }
    }
    fun createOrder(order: Order) {
        Log.d("CatalogoVM", "------------- CREATING ORDER -------------" + order)
        viewModelScope.launch(Dispatchers.IO) {
            repository.createOrder(order)
        }
    }
    suspend fun checkUsername(username: String): Boolean {
        return repository.existeValorEnColeccion("users", "name", username)
    }
    suspend fun checkEmail(email: String): Boolean {
        return repository.existeValorEnColeccion("users", "email", email)
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
        Log.d("CatalogoVM", "------------- REGISTER USER -------------")
        viewModelScope.launch(Dispatchers.IO) {
            repository.registerUser(
                name,
                email,
                pass,
                printerInfo,
                dir,
                isMaker,
                showEmail,
                showLocation
            )
        }
    }

    fun login(email: String, pass: String) {
        Log.d("CatalogoVM", "------------- LOGIN -------------")
        viewModelScope.launch {
            val userList = withContext(Dispatchers.IO) {
                repository.login(email, pass)
            }
            val user = userList?.firstOrNull()
            _user.value = user
            _userId.value = user?.id
            Log.d("CatalogoVM", "User logged in, id: ${user?.id}")
        }
    }
    fun getUserInfo(id: String) {
        Log.d("CatalogoVM", "------------- GETTING USER ORDER -------------")
        viewModelScope.launch(Dispatchers.IO) {
            val order = repository.getUserInfo(id)
            _userInfo.postValue(order)
        }
    }

}