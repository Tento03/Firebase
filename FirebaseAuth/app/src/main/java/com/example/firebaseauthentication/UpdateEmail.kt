package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.firebaseauthentication.databinding.ActivityUpdateEmailBinding
import com.google.firebase.auth.FirebaseAuth

class UpdateEmail : AppCompatActivity() {
    lateinit var binding: ActivityUpdateEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdateEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var firebaseAuth=FirebaseAuth.getInstance()
        var user=firebaseAuth.currentUser

        binding.btnUpdate.setOnClickListener(){
            var email=binding.etEmail.text.toString().trim()

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
            updateEmail(email)
        }
    }

    private fun updateEmail(email: String) {
        var firebaseAuth=FirebaseAuth.getInstance()
        var user=firebaseAuth.currentUser
        if (user != null) {
            user.updateEmail(email).addOnCompleteListener(){
                if (it.isSuccessful){
                    Toast.makeText(this,"update data berhasil", Toast.LENGTH_SHORT).show();
                    var intent= Intent(this,LoginActivity::class.java);
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"${it.exception?.message}",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}