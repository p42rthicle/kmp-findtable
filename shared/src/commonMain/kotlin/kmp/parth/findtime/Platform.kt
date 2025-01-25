package kmp.parth.findtime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform