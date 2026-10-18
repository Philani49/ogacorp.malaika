package com.example.ogacorp.malaika

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseAuthRepository : AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(Result.success(Unit))
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Login failed")))
                }
            }
    }

    override fun register(name: String, email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userMap = hashMapOf("name" to name, "email" to email, "role" to "Client")
                    FirebaseFirestore.getInstance().collection("users")
                        .document(auth.currentUser!!.uid)
                        .set(userMap)
                        .addOnSuccessListener { onResult(Result.success(Unit)) }
                        .addOnFailureListener { onResult(Result.failure(it)) }
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Registration failed")))
                }
            }
    }
}

class FirebaseCatalogRepository : CatalogRepository {
    private val db = FirebaseFirestore.getInstance()

    override fun getServices(onResult: (Result<List<BeautyService>>) -> Unit) {
        db.collection("services").get()
            .addOnSuccessListener { result ->
                val services = result.documents.mapNotNull { doc ->
                    // This reads the exact fields you created
                    BeautyService(
                        name = doc.getString("name") ?: "Missing Name",
                        description = doc.getString("description") ?: "",
                        category = doc.getString("category") ?: "",
                        price = doc.getDouble("price") ?: 0.0,
                        durationMinutes = doc.getLong("durationMinutes")?.toInt() ?: 0,
                        order = doc.getLong("order")?.toInt() ?: 0
                    )
                }
                Log.d("FIREBASE_CHECK", "Found ${services.size} services")
                onResult(Result.success(services))
            }
            .addOnFailureListener {
                Log.e("FIREBASE_CHECK", "Error getting services", it)
                onResult(Result.failure(it))
            }
    }

    override fun getProducts(onResult: (Result<List<Product>>) -> Unit) {
        db.collection("products").get()
            .addOnSuccessListener { result ->
                val products = result.documents.mapNotNull { doc ->
                    Product(
                        name = doc.getString("name") ?: "Missing Name",
                        category = doc.getString("category") ?: "",
                        price = doc.getDouble("price") ?: 0.0,
                        sizes = doc.getString("sizes") ?: "",
                        available = doc.getBoolean("available") ?: true,
                        newArrival = doc.getBoolean("newArrival") ?: false,
                        emoji = doc.getString("emoji") ?: ""
                    )
                }
                Log.d("FIREBASE_CHECK", "Found ${products.size} products")
                onResult(Result.success(products))
            }
            .addOnFailureListener {
                Log.e("FIREBASE_CHECK", "Error getting products", it)
                onResult(Result.failure(it))
            }
    }

    override fun getNewArrivals(onResult: (Result<List<Product>>) -> Unit) {
        db.collection("products").limit(3).get()
            .addOnSuccessListener { result ->
                val products = result.documents.mapNotNull { doc ->
                    Product(
                        name = doc.getString("name") ?: "Missing Name",
                        category = doc.getString("category") ?: "",
                        price = doc.getDouble("price") ?: 0.0,
                        sizes = doc.getString("sizes") ?: "",
                        available = doc.getBoolean("available") ?: true,
                        newArrival = doc.getBoolean("newArrival") ?: false,
                        emoji = doc.getString("emoji") ?: ""
                    )
                }
                onResult(Result.success(products))
            }
            .addOnFailureListener { onResult(Result.failure(it)) }
    }
}