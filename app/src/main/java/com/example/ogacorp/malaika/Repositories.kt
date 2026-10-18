package com.example.ogacorp.malaika

interface AuthRepository {
    fun login(email: String, password: String, onResult: (Result<Unit>) -> Unit)
    fun register(name: String, email: String, password: String, onResult: (Result<Unit>) -> Unit)
}

interface CatalogRepository {
    fun getServices(onResult: (Result<List<BeautyService>>) -> Unit)
    fun getProducts(onResult: (Result<List<Product>>) -> Unit)
    fun getNewArrivals(onResult: (Result<List<Product>>) -> Unit)
}

object Repositories {
    var auth: AuthRepository = FirebaseAuthRepository()
    var catalog: CatalogRepository = FirebaseCatalogRepository()
}
