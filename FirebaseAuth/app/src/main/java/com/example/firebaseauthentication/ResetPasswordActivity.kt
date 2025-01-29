package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.firebaseauthentication.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class ResetPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener(){
            var email=binding.etEmail.text.toString().trim()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmail.error="Ga sesuai format"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            resetPassword(email)
        }
    }

    private fun resetPassword(email: String) {
        var firebaseAuth=FirebaseAuth.getInstance()
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(){
            if (it.isSuccessful){
                    Toast.makeText(this, "cek email bro", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK;
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show();
                }
            }
        }
}
