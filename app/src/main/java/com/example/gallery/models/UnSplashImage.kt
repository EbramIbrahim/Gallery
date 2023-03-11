package com.example.gallery.models




data class UnSplashImage(
    val id: String,
    val likes: Int,
    val user: User,
    val urls: Urls,
)
