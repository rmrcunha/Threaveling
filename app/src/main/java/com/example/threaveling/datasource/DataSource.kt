package com.example.threaveling.datasource

import android.util.Log
import com.example.threaveling.models.Threavel
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataSource{
    private val db = Firebase.firestore
    val TAG = "ThreavelDataSource"
    val docRef = db.collection("posts")


    suspend fun makeNewPost(post:Threavel, onSuccess:()->Unit) = withContext(Dispatchers.IO) {
        Log.d(TAG, "Postagem a ser realizada Step 3: $post")

        db.collection("posts").document().set(post)
            .addOnCompleteListener { response ->
                Log.d(TAG, "Postagem realizada com sucesso $response.")
                onSuccess()
        }
            .addOnFailureListener {
            Log.d(TAG, "Erro ao realizar postagem")
        }
    }

    fun getPostById(id:String):Threavel?{
        var post:Threavel? = null
        docRef.document(id).get().addOnSuccessListener { document ->
            if (document != null) {
                post = document.toObject(Threavel::class.java)
                Log.d(TAG, "Postagem encontrada $post")
            } else {
                Log.d(TAG, "Postagem não encontrada")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Problema em encontrar a postagem")
            }
        return post
    }

    fun getAllPosts():List<Threavel>{
        val posts = mutableListOf<Threavel>()
        docRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val post = document.toObject(Threavel::class.java)
                posts.add(post)
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Problema em encontrar as postagens")
            }
        return posts
    }

    fun getTopRecentPosts(limit:Long = 10):List<Threavel>{
        val posts = mutableListOf<Threavel>()
        docRef.orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(limit)
            .get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val post = document.toObject(Threavel::class.java)
                    posts.add(post)
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Problema em encontrar as postagens")
            }
        return posts
    }
}

