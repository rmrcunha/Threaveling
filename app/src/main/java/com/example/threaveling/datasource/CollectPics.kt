package com.example.threaveling.datasource

import com.cloudinary.transformation.RoundCorners
import com.cloudinary.transformation.gravity.FocusOn
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.resize.Resize
import com.example.threaveling.cloudinary.CloudinaryConfig.cloudinary

fun collectPerfilPics(userId: String):String? {
    return cloudinary.image {
        publicId("${userId}/${userId}")
        resize(Resize.thumbnail {
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
}