package com.example.threaveling.firebaseAuthentication

interface AuthenticationCallback {
    fun onSuccess(user: String)
    fun onFailure(errorMessage: String)
}
