package com.example.threaveling.firebaseAuthentication

import com.example.threaveling.models.UserModel

interface AuthenticationCallback {
    fun onSuccess(user: UserModel)
    fun onFailure(errorMessage: String)
}
