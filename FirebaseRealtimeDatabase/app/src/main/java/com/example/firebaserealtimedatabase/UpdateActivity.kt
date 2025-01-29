package com.example.firebaserealtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaserealtimedatabase.databinding.ActivityUpdateBinding
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdate.setOnClickListener(){
            var databaseReference=FirebaseDatabase.getInstance().getReference("user")
            var id=intent.getStringExtra(MainActivity.ID)
            var nama=binding.editnama.text.toString()
            var nim=binding.editnim.text.toString()

            var userUpdates= hashMapOf<String,Any>(
                "nama" to nama,
                "nim" to nim
            )
            (if (id != null) {
                databaseReference.child(id).updateChildren(userUpdates).addOnCompleteListener(){
                    if (it.isSuccessful){
                        Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        binding.editnama.text.clear()
                        binding.editnim.text.clear()
                    }
                    else{
                        Toast.makeText(this, "Data gagal diperbarui", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}