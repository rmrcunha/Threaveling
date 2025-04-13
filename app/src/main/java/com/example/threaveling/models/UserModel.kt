package com.example.threaveling.models

import java.util.Date

data class UserModel(private var username:String = "",
                     private val email:String = ""
){
    private val createdAt:Date = Date()
    private val posts = mutableListOf<Threavel>()

    fun getUsername():String = username
    fun getEmail():String = email
    fun getCreatedDate():Date = createdAt

    fun setUsername(name:String){
        this.username = name
    }
}