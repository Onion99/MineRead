package org.onion.read

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform