package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaseauthentication.databinding.ActivityUpdatePasswordBinding
import com.google.firebase.auth.FirebaseAuth

class UpdatePassword : AppCompatActivity() {
    lateinit var binding: ActivityUpdatePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdatePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAuth.setOnClickListener(){
            if (binding.etPassword.text.toString().trim().isEmpty()){
                binding.etPassword.error="Jangan kosong"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            updatePassword(pass = binding.etPassword.text.toString().trim())
        }
    }

    private fun updatePassword(pass: String) {
        var firebaseAuth=FirebaseAuth.getInstance()
        var user=firebaseAuth.currentUser
        user?.updatePassword(pass)?.addOnCompleteListener(){
            if (it.isSuccessful){
                Toast.makeText(this,"update data berhasil", Toast.LENGTH_SHORT).show();
                var intent= Intent(this,LoginActivity::class.java);
                startActivity(intent)
            } else{
                Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}