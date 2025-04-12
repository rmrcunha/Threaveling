package com.example.threaveling.cache

import com.cloudinary.transformation.RoundCorners
import com.cloudinary.transformation.gravity.FocusOn
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.resize.Resize
import com.example.threaveling.cloudinary.CloudinaryConfig.cloudinary
import com.example.threaveling.firebaseAuthentication.FirebaseAuthentication.getCurrentUserId

object GeneralLoad {
    private val imageCache = mutableMapOf<String, String?>()

    private fun reloadProfilePic(){
        val perfilImage = cloudinary.image{
            publicId("${getCurrentUserId()}/${getCurrentUserId()}")
            resize(Resize.thumbnail(){
                width(1.0F)
                aspectRatio(1.0F)
                gravity(
                    Gravity.focusOn(
                        FocusOn.face()
                    )
                )
            })
            roundCorners(RoundCorners.max())
        }.generate()

        imageCache[getCurrentUserId()]= perfilImage
    }

    fun getProfilePic():String? {
        if (imageCache.containsKey(getCurrentUserId())) {
            return imageCache[getCurrentUserId()]
        } else {
            reloadProfilePic()
            return imageCache[getCurrentUserId()]
        }
    }
}
