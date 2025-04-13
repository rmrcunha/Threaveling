package com.example.threaveling.datasource

import com.example.threaveling.firebaseAuthentication.FirebaseAuthentication.getCurrentUserId
import com.example.threaveling.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class UserDataSource {
    private val db = Firebase.firestore
    private val docRef = db.collection("users")

     fun userDetails(user:UserModel, onSuccess:()->Unit, onFail:()->Unit){
        docRef.document(getCurrentUserId()).set(user)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFail()
            }
    }

    suspend fun getUserById(userId:String): UserModel? {
        val snapshot = docRef.document(userId).get().await()
        return snapshot.toObject(UserModel::class.java)
    }
}

//ugEOGfwQX0QKiJ9TTHt3eAfAbEC2