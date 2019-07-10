package com.example.playground.data.entity

import java.io.Serializable

data class User (
    var id : String? = null,
    var name : String? = null,
    var photo : String? = null
): Serializable