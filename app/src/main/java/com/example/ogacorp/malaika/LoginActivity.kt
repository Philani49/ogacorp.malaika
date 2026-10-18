package com.example.ogacorp.malaika

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<TextInputEditText>(R.id.etLoginEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoogleSignIn = findViewById<SignInButton>(R.id.btnGoogleSignIn)

        // --- 1. STANDARD EMAIL/PASSWORD LOGIN ---
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            when {
                email.isEmpty() || password.isEmpty() ->
                    return@setOnClickListener toast("Please fill in all fields")
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    return@setOnClickListener toast("Enter a valid email address")
            }

            btnLogin.isEnabled = false
            Repositories.auth.login(email, password) { result ->
                result.onSuccess {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }.onFailure { e ->
                    btnLogin.isEnabled = true
                    toast(e.localizedMessage ?: "Login failed")
                }
            }
        }

        // --- 2. GOOGLE SIGN-IN (SSO) SETUP ---
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // YOUR CLIENT ID IS PASTED HERE:
            .requestIdToken("137061469577-u019fuqk2dst17751coh351hfn5kcl1c.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        btnGoogleSignIn.setOnClickListener {
            // Launch the Google sign-in intent
            startActivityForResult(googleSignInClient.signInIntent, 1001)
        }

        // --- 3. NAVIGATION TO REGISTER ---
        findViewById<TextView>(R.id.tvGoToRegister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // --- 4. HANDLE GOOGLE SIGN-IN RESULT ---
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            // Successfully logged into Firebase using Google
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        } else {
                            toast("Firebase Auth failed: ${authTask.exception?.message}")
                        }
                    }
            } catch (e: ApiException) {
                // Google Sign In failed
                toast("Google sign in failed: ${e.message}")
            }
        }
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}