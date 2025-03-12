package com.example.firebaseauthcompose

data class User(val id:String,val email:String,val password:String){
    constructor():this("","","")
}
