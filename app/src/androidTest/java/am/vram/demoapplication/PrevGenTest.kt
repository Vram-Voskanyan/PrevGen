package am.vram.demoapplication

import DataPreview
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println(testClassInnerClass1Preview)
        println(testClassInnerClass2Preview)
        println(testClassSimplePreview)
        println(testClassListPreview)
        assertEquals(4, 2 + 2)
    }
}

@DataPreview
data class TestClassInnerClass2(
    val stringName: List<InnerClass>,
    val innerclass: InnerClass2
)
@DataPreview
data class TestClassInnerClass1(
    val stringName: List<InnerClass>,
    val innerclass: InnerClass
)

data class InnerClass(val a: String, val b: Int, val c: List<String>)
data class InnerClass2(val c: List<InnerClass>)

@DataPreview
data class TestClassSimple(
    val stringName: String,
    val intAge: Int,
    val boolean: Boolean,
    val longDate: Long,
)

@DataPreview
data class TestClassList(
    val stringName: List<String>,
    val intAge: List<Int>,
    val boolean: List<Boolean>,
    val longDate: List<Long>,
)
