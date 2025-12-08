package com.outivox.myabc.authentication.util

data class PlatformComponent(val name: String)

expect fun platform(): PlatformComponent