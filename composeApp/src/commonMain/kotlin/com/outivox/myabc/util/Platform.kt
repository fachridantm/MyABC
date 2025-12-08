package com.outivox.myabc.util

data class PlatformComponent(val name: String)

expect fun platform(): PlatformComponent
