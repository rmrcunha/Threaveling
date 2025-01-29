package com.example.threaveling.cloudinary

import android.content.Context
import android.net.Uri
import com.cloudinary.upload.upload
import com.cloudinary.uploader
import com.example.threaveling.firebaseAuthentication.FirebaseAuthentication.getCurrentUserId
import java.io.File



object CloudinaryUpload {

    fun uploadImage(context: Context, uri: Uri, fileName:String, onSuccess:(String)->Unit, onError:(String)->Unit){
        val cloudinary = CloudinaryConfig.cloudinary

        val filePath = context.contentResolver.openInputStream(uri)?.use{ inputStream ->
            val tempFile = File.createTempFile("temp", ".jpg")
            tempFile.outputStream().use{ output->
                inputStream.copyTo(output)
            }
            tempFile.absolutePath
        }
        if(filePath != null){
            Thread{
                try{
                    cloudinary.uploader().upload(filePath){
                        params{
                            folder = getCurrentUserId()
                            publicId = fileName
                        }
                    }
                    onSuccess(uri.toString())
                }catch (e:Exception){
                    e.printStackTrace()
                    onError(e.message ?: "Unknown error")
                }
            }.start()
        } else{
            onError("Failed to get file path")

        }
    }
}