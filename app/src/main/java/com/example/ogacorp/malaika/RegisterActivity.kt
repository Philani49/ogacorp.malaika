package com.example.ogacorp.malaika

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<TextInputEditText>(R.id.etRegName)
        val etEmail = findViewById<TextInputEditText>(R.id.etRegEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etRegPassword)
        val etConfirm = findViewById<TextInputEditText>(R.id.etRegConfirm)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            when {
                name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty() ->
                    return@setOnClickListener toast("Please fill in all fields")
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    return@setOnClickListener toast("Enter a valid email address")
                password.length < 6 ->
                    return@setOnClickListener toast("Password must be at least 6 characters")
                password != confirm ->
                    return@setOnClickListener toast("Passwords do not match")
            }

            btnRegister.isEnabled = false
            Repositories.auth.register(name, email, password) { result ->
                result.onSuccess {
                    startActivity(Intent(this, HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                }.onFailure { e ->
                    btnRegister.isEnabled = true
                    toast(e.localizedMessage ?: "Registration failed")
                }
            }
        }

        findViewById<TextView>(R.id.tvGoToLogin).setOnClickListener { finish() }
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}
