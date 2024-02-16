package am.vram.demoapplication

import DataPreview
import org.junit.Test

import org.junit.Assert.*
import java.util.Date

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testingGeneration() {
        println(testClassInnerClass1Preview)
        println(testClassInnerClass2Preview)
        println(testClassSimplePreview)
        println(testClassListPreview)
        println(testClassEmptyPreview)
        println(nonDataClassPreview.name)
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
    val innerclass: InnerClass,
    val innerclassDate: InnerClassDate,
    val address: String,
    val text: String,
    val price: Double
)

@DataPreview
data class TestClassEmpty(val a: Date?)

data class InnerClass(val a: String, val b: Int, val c: List<String>, val doubleList: List<Double>)
data class InnerClass2(val c: List<InnerClass>)
data class InnerClassDate(val c: List<Date?>)

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

@DataPreview
class NonDataClass(val name: String)
@DataPreview
class NonDataClassEmpty()
@DataPreview
fun nonDataFun() = false
