package com.vr.kmptestapplication

import DataPreview

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@DataPreview
data class GeneratorTest2(val age: Long, val name: String, val id: String)