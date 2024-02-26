# Configurations for Kotlin PreviewGenerator (PrevGen)

By default, **PreviewGenerator** works with the NameGenerator V1 engine, which has a set of predefined keys with predefined values. These can be found [here](https://github.com/Vram-Voskanyan/PrevGen/blob/main/PreviewGenerator/src/main/kotlin/generatorengine/samples/DataSamples.kt).
Starting from v1.0.3, we have added a **CONSTANT** configuration, which generates a POJO object with predefined values. This is mostly needed for testing purposes to ensure consistent output, etc.

To switch the generation to constant, you need to add the following argument into your Gradle file: 
```kotlin
ksp {
    arg("prevgen.mode", "PREDICTABLE_MODE")
}
```

An example of the Gradle configuration can be found [here](https://github.com/Vram-Voskanyan/PrevGen/blob/main/app/build.gradle.kts).
