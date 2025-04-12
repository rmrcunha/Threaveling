package com.example.threaveling.repositories

import android.util.Log
import com.example.threaveling.datasource.PostDataSource
import com.example.threaveling.models.Threavel
import kotlinx.coroutines.flow.Flow

const val TAG = "ThreavelPost"

class ThreavelRepository {

    private val postDataSource = PostDataSource()

    suspend fun savePost(post: Threavel, onSuccess:()->Unit) {
        try {
            Log.d(TAG, "Salvando postagem Step 2: $post")
            postDataSource.makeNewPost(post, onSuccess)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao salvar postagem", e)
            throw e
        }
    }

    fun getRecentPosts(limit : Long = 10): Flow<MutableList<Threavel>> {
        return postDataSource.getTopRecentPosts(limit = limit)
    }
}