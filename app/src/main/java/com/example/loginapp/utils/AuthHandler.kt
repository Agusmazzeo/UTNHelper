package com.example.loginapp.utils

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthHandler {
    private var auth: FirebaseAuth = Firebase.auth

    fun checkUserIsAuthenticated(onSuccessCallback:(FirebaseUser)->Unit,onErrorCallback: ()->Unit){
        val currentUser = auth.currentUser
        if(currentUser == null){
            onErrorCallback()
        }
        else{
            onSuccessCallback(currentUser)
        }
    }

    suspend fun createUserWithEmailAndPassword(email: String, password: String):AuthResult?{
        return try {
            var userCreate = auth.createUserWithEmailAndPassword(email, password)
            userCreate.await()
        }catch (e: Exception){
            Log.d("Error during User account creation: ", e.message!!)
            null
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String):AuthResult?{
        return try {
            var userCreate = auth.signInWithEmailAndPassword(email, password)
            userCreate.await()
        }catch (e: Exception){
            Log.d("Error during User account sign in: ", e.message!!)
            null
        }
    }

}