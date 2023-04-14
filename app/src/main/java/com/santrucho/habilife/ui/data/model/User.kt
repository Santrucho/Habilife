package com.santrucho.habilife.ui.data.model

data class User(
    val userId : String,
    val username : String,
    val email : String,
    val habitComplete : Int = 0,
    val goalComplete : Int = 0,
    var token : String =" ",
)