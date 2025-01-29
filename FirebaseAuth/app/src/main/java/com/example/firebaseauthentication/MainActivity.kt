package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaseauthentication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logout.setOnClickListener(){
            var firebaseAuth=FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            Intent(this,LoginActivity::class.java).also {
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
            }
        }

        binding.btnResendVerification.setOnClickListener(){
            binding.txtVerificationStatus.text="Email belum diverifikasi"

            var firebaseAuth=FirebaseAuth.getInstance()
            var user=firebaseAuth.currentUser
            if (user!=null && !user.isEmailVerified) {
                binding.btnResendVerification.setOnClickListener() {
                        user.sendEmailVerification().addOnCompleteListener() {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Email verif berhasil", Toast.LENGTH_SHORT)
                                    .show();
                            } else {
                                Toast.makeText(this, "Email verif gagal", Toast.LENGTH_SHORT)
                                    .show();
                            }
                        }
                }
            }
        }
        binding.buttonupdate.setOnClickListener(){
            var intent=Intent(this,UpdateEmail::class.java)
            startActivity(intent)
        }
        binding.buttonupdatepass.setOnClickListener(){
            var intent=Intent(this,UpdatePassword::class.java)
            startActivity(intent)
        }
    }
}