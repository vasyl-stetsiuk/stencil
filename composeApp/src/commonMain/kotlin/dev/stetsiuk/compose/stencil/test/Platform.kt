package dev.stetsiuk.compose.stencil.test

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform