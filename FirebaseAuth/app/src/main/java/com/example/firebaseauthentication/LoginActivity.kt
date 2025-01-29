package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.firebaseauthentication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    companion object{
        const val EMAIL="email"
    }
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener(){
            Intent(this,RegisterActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnForgotPassword.setOnClickListener(){
            var firebaseAuth=FirebaseAuth.getInstance()
            Intent(this,ResetPasswordActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnLogin.setOnClickListener(){
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
            loginUser(email,pass)
        }
    }

    private fun loginUser(email:String,pass:String) {
        var firebaseAuth=FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email,pass)
            .addOnSuccessListener(){
                var intent=Intent(this,MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                Toast.makeText(this,"Login Berhasil",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener(){
                Toast.makeText(this,"Login gagal",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onStart() {
        super.onStart()
        var firebaseAuth=FirebaseAuth.getInstance()
        var user=firebaseAuth.currentUser
        if (user!=null){
            var intent=Intent(this,MainActivity::class.java);
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK;
            startActivity(intent);
        }
    }
}