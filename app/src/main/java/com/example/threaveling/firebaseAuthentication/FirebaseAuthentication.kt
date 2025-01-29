package com.example.threaveling.firebaseAuthentication

import android.util.Log
import com.example.threaveling.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth

object FirebaseAuthentication{
    private var auth: FirebaseAuth = Firebase.auth
    private var TAG = "EmailAndPassword"

    fun isAuthenticated():Boolean = auth.currentUser != null

    fun out() = auth.signOut()

    fun getCurrentUserId():String = auth.currentUser?.uid.toString()

     fun createUserWithEmailAndPassword(username:String, email:String, password:String, navController:()->Unit = {}, callback: AuthenticationCallback){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Log.d(TAG, "createUserWithEmailAndPassword:Success")
                val createdUser = UserModel(username, email)
                callback.onSuccess(createdUser)
                navController()
            }
        }.addOnFailureListener{ exception->
            val messageError = when(exception){
                is FirebaseAuthWeakPasswordException -> "Sua senha deve possuir no mínimo 6 caracteres!"
                is FirebaseAuthInvalidCredentialsException -> "Digite um email válido"
                is FirebaseAuthUserCollisionException -> "Esta conta já está cadastrada"
                is FirebaseNetworkException -> "Verifique a sua conexão"
                else -> "Erro ao cadastrar usuário!"
            }

            callback.onFailure(messageError)
        }
    }

    fun signInWithEmailAndPassword(email:String, password: String, navController:()->Unit = {}, callback: AuthenticationCallback){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task->
            if (task.isSuccessful){
                Log.d(TAG, "signInUserWithEmailAndPassword:Success")
                navController()
            }
        }.addOnFailureListener { exception ->
            val messageError = when (exception) {
                is FirebaseNetworkException ->  "Verifique a sua conexão"
                is FirebaseAuthInvalidCredentialsException -> "Email ou senha incorretos"
                else -> "Falha na tentiva de login"
            }

            callback.onFailure(messageError)
        }
    }
}