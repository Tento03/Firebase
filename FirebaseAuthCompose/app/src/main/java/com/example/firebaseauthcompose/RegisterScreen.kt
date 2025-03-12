package com.example.firebaseauthcompose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chatappcompose.R
import com.example.firebaseauthcompose.ui.theme.FirebaseAuthComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.security.MessageDigest

@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var focusRequesterEmail by remember { mutableStateOf(FocusRequester()) }
    var focusRequesterPassword by remember { mutableStateOf(FocusRequester()) }
    var hasErrorEmail by remember { mutableStateOf(false) }
    var hasErrorPassword by remember { mutableStateOf(false) }
    val context= LocalContext.current
    val keyboardController= LocalSoftwareKeyboardController.current

    Column(modifier = Modifier
        .fillMaxSize()
        .paint(painter = painterResource(R.drawable.img), contentScale = ContentScale.FillBounds),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .paint(
                painter = painterResource(R.drawable.img),
                contentScale = ContentScale.FillBounds
            )
            .padding(start = 20.dp, end = 20.dp)
            .height(200.dp)
            .width(280.dp)
        )
        Card(modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .paint(painter = painterResource(R.drawable.img_1), contentScale = ContentScale.FillBounds)
            .height(300.dp)
            .width(280.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email=it
                    hasErrorEmail=false
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                label = { Text("Email") },
                isError = hasErrorEmail,
                modifier = Modifier.focusRequester(focusRequesterEmail).padding(top = 20.dp, start = 10.dp, end = 10.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password=it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = hasErrorEmail,
                modifier = Modifier.focusRequester(focusRequesterEmail).padding(top = 20.dp, start = 10.dp, end = 10.dp)
            )
            Button(onClick = {
                if (email.isEmpty()){
                    focusRequesterEmail.requestFocus()
                    hasErrorEmail=true
                }
                else if (password.isEmpty()){
                    focusRequesterPassword.requestFocus()
                    hasErrorPassword=true
                }
                else if (email.isNotEmpty() && password.isNotEmpty()){
                    val passHash= passwordHash(password)
                    val firebaseAuth=FirebaseAuth.getInstance()
                    val firebaseDatabase=FirebaseDatabase.getInstance().getReference("userNew")
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener{
                            if (it.isSuccessful){
                                val id=firebaseAuth.currentUser?.uid
                               if (id!=null){
                                   val user= User(id, email, passHash)
                                   firebaseDatabase.child(id).setValue(user)
                                   Toast.makeText(context,"Register Success",Toast.LENGTH_SHORT).show()
                                   navController.navigate("Login")
                               }
                            }
                            else{
                                Toast.makeText(context,"Register Failed",Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }, modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp).fillMaxWidth(), shape = RoundedCornerShape(0.dp)) {
                Text("Register", textAlign = TextAlign.Center)
            }
            TextButton(onClick = {navController.navigate("Login")}) {
                Text("Have an account?")
            }

            if (hasErrorEmail){
                Toast.makeText(context,"Please fill email field",Toast.LENGTH_SHORT).show()
                focusRequesterEmail.requestFocus()
            }
            else if(hasErrorPassword){
                Toast.makeText(context,"Please fill password field",Toast.LENGTH_SHORT).show()
                focusRequesterPassword.requestFocus()
            }
        }
    }
}

fun passwordHash(input:String):String{
    val byte=MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
    return byte.joinToString(""){
        "%02x".format(it)
    }
}

@Preview
@Composable
private fun RegisterPrev() {
    FirebaseAuthComposeTheme {
        RegisterScreen(navController = rememberNavController())
    }

}