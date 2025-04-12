package com.example.threaveling.datasource

import android.util.Log
import com.example.threaveling.firebaseAuthentication.FirebaseAuthentication.getCurrentUserId
import com.example.threaveling.models.Threavel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class PostDataSource{
    private val db = Firebase.firestore
    private val _allPosts = MutableStateFlow<MutableList<Threavel>>(mutableListOf())
    private val allPosts: StateFlow<MutableList<Threavel>> = _allPosts

    private val tag = "ThreavelDataSource"
    private val docUsersRef = db.collection("users")
    private val docPostsRef = db.collection("posts")


    suspend fun makeNewPost(post:Threavel, onSuccess:()->Unit) = withContext(Dispatchers.IO) {
        Log.d(tag, "Postagem a ser realizada Step 3: $post")

        docUsersRef.document(getCurrentUserId()).update("posts", FieldValue.arrayUnion(post.id))
            .addOnCompleteListener { response ->
                Log.d(tag, "Postagem realizada com sucesso $response.")
                onSuccess()
        }.addOnFailureListener {
            Log.d(tag, "Erro ao realizar postagem")
        }
        docPostsRef.document(post.id).set(post).addOnSuccessListener {_->
            onSuccess()
        }.addOnFailureListener { exception ->
            Log.d(tag, "Erro ao realizar postagem $exception")
        }

    }


     fun getTopRecentPosts(limit:Long = 10): Flow<MutableList<Threavel>> {
        val posts :MutableList<Threavel> = mutableListOf()

        docPostsRef.limit(limit).get().addOnCompleteListener{querySnapshtot ->
            if(querySnapshtot.isSuccessful){
                for (document in querySnapshtot.result){
                    val post = document.toObject(Threavel::class.java)
                    posts.add(post)
                    _allPosts.value = posts
                }
            }
        }
        return allPosts
    }
}

