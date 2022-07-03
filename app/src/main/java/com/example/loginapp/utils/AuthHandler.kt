package com.example.loginapp.utils

import android.util.Log
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

    suspend fun createUserWithEmailAndPassword(email: String, password: String):Boolean{
        return try {
            var userCreate = auth.createUserWithEmailAndPassword(email, password)
            userCreate.await()
            true
        }catch (e: Exception){
            Log.d("Error during User account creation: ", e.message!!)
            false
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String):Boolean{
        return try {
            var userCreate = auth.signInWithEmailAndPassword(email, password)
            userCreate.await()
            true
        }catch (e: Exception){
            Log.d("Error during User account sign in: ", e.message!!)
            false
        }
    }

}