package com.vr.kmptestapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform