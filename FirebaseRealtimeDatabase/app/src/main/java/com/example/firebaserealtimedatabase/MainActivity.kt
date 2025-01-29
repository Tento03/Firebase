package com.example.firebaserealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimedatabase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    companion object{
        const val ID="id"
    }
    lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    lateinit var userAdapter: UserAdapter
    private val userList= arrayListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var databaseReference=FirebaseDatabase.getInstance().getReference("user")
        binding.submit.setOnClickListener(){
            createData()
        }
        readData()
    }

    private fun readData() {
        recyclerView=binding.rvMhs
        recyclerView.layoutManager=LinearLayoutManager(this)
        userAdapter= UserAdapter(userList,object :UserAdapter.OnItemClickListener{
            override fun updateUser(user: User) {
                updateData(user)
            }

            override fun deleteUser(user: User) {
                deleteData(user)
            }
        })
        recyclerView.adapter=userAdapter
        var databaseReference=FirebaseDatabase.getInstance().getReference("user")
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (i in snapshot.children){
                    var user=i.getValue(User::class.java)
                    if (user!=null){
                        userList.add(user)
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun createData() {
        var databaseReference=FirebaseDatabase.getInstance().getReference("user")
        var id=databaseReference.push().key
        var nama=binding.nama.text.toString()
        var nim=binding.nim.text.toString()
        var user=User(id!!,nama, nim)
        databaseReference.child(id).setValue(user).addOnCompleteListener(){
            if (it.isSuccessful){
                Toast.makeText(this,"sukses", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"gagal",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateData(user: User) {
        var intent=Intent(this,UpdateActivity::class.java)
        intent.putExtra(ID,user.id)
        startActivity(intent)
    }

    private fun deleteData(user: User) {
        var databaseReference=FirebaseDatabase.getInstance().getReference("user")
        databaseReference.child(user.id).removeValue().addOnCompleteListener(){
            if (it.isSuccessful){
                Toast.makeText(this,"sukses hapus", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"gagal hapus", Toast.LENGTH_SHORT).show()
            }
        }
    }
}