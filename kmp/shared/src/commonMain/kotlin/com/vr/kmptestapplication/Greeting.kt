package com.vr.kmptestapplication

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}
annotation class DataPreview
@DataPreview
data class GeneratorTest(val age: Long, val name: String, val id: String)