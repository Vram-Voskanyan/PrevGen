# Kotlin PreviewGenerator (PrevGen)

Kotlin PreviewGenerator is a lightweight compiler plugin written with Kotlin Symbol Processing (KSP). Preview Generator creates preview instances for data classes, which can be used in Jetpack Compose view previews.

## Navigation
- [Setup](#setup)
- [Usage](#usage)
- [Examples](#examples)
- [Roadmap](#ongoing-and-future-works)
- [Feedback and contact us](#)
- [License](#license)
- [Contribute](#contributing)

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
        mavenCentral()
    }
    ```

3. Add KSP and the module into Gradle dependencies:

    ```gradle
    dependencies {
        implementation("io.github.vram-voskanyan.kmp:PreviewGenerator:0.0.2")
        ksp("io.github.vram-voskanyan.kmp:PreviewGenerator:0.0.2")
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

### Example for Jetpack Compose
TODO: soon ;)

## Ongoing and Future Works

Since this project was developed only during non-office hours, not everything is covered. For example, the following types are not currently supported: Non-Data classes, Enum, Map, List (in progress).

We also have some exciting features in mind for implementation, depending on community interest:

1. **Customized Data Size:** Introduce an option to edit the generated data size for checking how the view behaves with large or small datasets. This may involve adding a parameter option to annotations.

2. **Flexible Data Generation:** Currently, the generator relies on an argument name-checker to pick values close to the parameter type. For the next step, we aim to provide an option to pass values from outside, allowing customization such as non-English values.

## Contact Author 

- (email)[vram.arm@gmail.com]
- (Linkedin)[https://www.linkedin.com/in/vram-voskanyan-146b6198/]

## License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Contributing

If you would like to contribute code you can do so through GitHub by forking the repository and sending a pull request.
When submitting code, please make every effort to follow existing conventions and style in order to keep the code as readable as possible.

