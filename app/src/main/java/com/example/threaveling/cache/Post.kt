package com.example.threaveling.cache

import com.example.threaveling.models.Threavel

object Post {
    private var post = Threavel()

    fun setPost(post: Threavel){
        this.post = post
    }

    fun getPost(): Threavel{
        return post
    }
}