# Kotlin PreviewGenerator (PrevGen)

Kotlin PreviewGenerator is a lightweight compiler plugin written with Kotlin Symbol Processing (KSP). Preview Generator creates preview instances for data classes, which can be used in Jetpack Compose view previews.

## Navigation
- [Setup](#setup)
- [Usage](#usage)
- [Examples](#examples)
- [Roadmap](#roadmap)
- [License](#license)

## Setup

1. Add the KSP plugin in the main `build.gradle`:

    ```gradle
    plugins {
        // ...
        id("com.google.devtools.ksp") version "1.9.21-1.0.15"
    }
    ```

2. Add the Sonatype Maven URL:

    ```gradle
    repositories {
        // ...
        maven("https://s01.oss.sonatype.org/content/repositories/releases/")
    }
    ```

3. Add KSP and the module into Gradle dependencies:

    ```gradle
    dependencies {
        implementation("io.github.vram-voskanyan:PreviewGenerator:0.0.1-SNAPSHOT")
        ksp("io.github.vram-voskanyan:PreviewGenerator:0.0.1-SNAPSHOT")
    }
    ```

4. Sync the project, and you are good to go.

## Usage

1. Add a `@DataPreview` annotation to the class that needs to have a generated preview.
2. Rebuild the project. It will generate a Preview class inside `build\ksp\[BUILD_VARIANT]\ClassName.kt`.

## Examples

```kotlin
@DataPreview
data class DummyClass(val name: String, val age: Int, val loginDate: Long)

val previewValue = dummyClassPreview  

// dummyClassPreview is a generated class which is: DummyClass(name = "Ryan", age = 85, loginDate = 1705600601029)
// Note: `loginDate` is a Timestamp ;)
```
## Roadmap

## License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)