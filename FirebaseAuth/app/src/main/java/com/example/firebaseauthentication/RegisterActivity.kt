package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.firebaseauthentication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(){
            Intent(this,LoginActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnForgotPassword.setOnClickListener(){
            Intent(this,LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnRegister.setOnClickListener(){
            var email=binding.etEmail.text.toString().trim()
            var pass=binding.etPassword.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmail.error="Tidak sesuai format"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()){
                binding.etEmail.error="Email tidak boleh kosong"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (pass.isEmpty()){
                binding.etPassword.error="Password tidak boleh kosong"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            if (pass.length <=8){
                binding.etPassword.error="Password harus 8 karakter"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            registerUser(email,pass)
        }
    }

    private fun registerUser(email: String, pass: String) {
        var firebaseAuth= FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(){
            if (it.isSuccessful){
                Intent(this,LoginActivity::class.java).also {intent->
                    startActivity(intent)
                }
                Toast.makeText(this,"Registrasi Berhasil",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Registrasi Gagal",Toast.LENGTH_SHORT).show()
            }
        }
    }
}