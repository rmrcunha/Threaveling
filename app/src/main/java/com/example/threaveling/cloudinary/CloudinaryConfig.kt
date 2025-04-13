package com.example.threaveling.cloudinary

import android.app.Application
import android.content.Context
import com.cloudinary.Cloudinary
import com.example.threaveling.R


object CloudinaryConfig: Application() {

    private fun getString(context:Context = applicationContext):String = context.getString(R.string.cloudinary)


    //val cloudinary = Cloudinary(getString())

    val cloudinary = Cloudinary("cloudinary://791861926747343:ib5fcMjnH8Q7CKjMXV2fxRyBE3c@threaveling")
}