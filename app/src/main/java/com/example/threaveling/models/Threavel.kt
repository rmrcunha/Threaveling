package com.example.threaveling.models

import java.util.Date

data class Threavel(
    var id: String = "",
    var userId: String = "",
    var destiny: String = "",
    var date: Map<String?, String?> = mapOf("start" to null, "end" to null),
    var introduction: String = "",
    var detailedDescription: String = "",
    var stayDescription: String = "",
    var likes: Int = 0,
    val comments: Int = 0,
    val picsNumber: Int = 0,
    val createdAt: Date = Date()
)