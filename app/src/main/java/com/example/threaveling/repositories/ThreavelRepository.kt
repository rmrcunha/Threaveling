package com.example.threaveling.repositories

import android.util.Log
import com.example.threaveling.datasource.DataSource
import com.example.threaveling.models.Threavel

const val TAG = "ThreavelPost"

class ThreavelRepository {

    private val dataSource = DataSource()

    suspend fun savePost(post: Threavel, onSuccess:()->Unit) {
        try {
            Log.d(TAG, "Salvando postagem Step 2: $post")
            dataSource.makeNewPost(post, onSuccess)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao salvar postagem", e)
            throw e
        }

    }
}