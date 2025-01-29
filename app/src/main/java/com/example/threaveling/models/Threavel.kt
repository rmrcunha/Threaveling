package com.example.threaveling.models

data class Threavel(
    var id:String,
    var destiny:String,
    var date: Pair<String?, String?>,
    var  introduction:String,
    var detailedDescription:String,
    var stayDescription:String,
    var likes:Int = 0,
    val comments:Int = 0,
    val picsNumber:Int = 0,
)