package generatorengine

import appendText
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.Modifier
import java.io.OutputStream
import kotlin.random.Random

class NameGeneratorEngineV1 : NameGeneratorEngine {
    // can be added extra config for future. if needed huge data to test.
    private val longMaxRange: Long = 10000
    private val randomStringMin = 4
    private val randomStringMax = 10
    private val randomIntMin = 100
    private val randomIntMax = 1000
    private val listElementsMaxCount = 5

    override fun generateValues(file: OutputStream, parameters: List<KSValueParameter>) {
        parameters.forEach {
            getValueAndGeneratePreview(it, file)
        }
        file.appendText(")")
    }

    // itarator
    private fun getValueAndGeneratePreview(ksValueParameter: KSValueParameter, file: OutputStream) {
        val name = ksValueParameter.name!!.asString()
        val typeResolve = ksValueParameter.type.resolve().declaration
        val typeName by lazy { StringBuilder(typeResolve.qualifiedName?.asString() ?: "<ERROR>") }
        if (!handleClassType(typeResolve, name, file) && !handleListType(typeName, ksValueParameter, name, file)) {
            file.appendText(generateValueByType(ksValueParameter, typeName.toString(), name))
        }
    }

    // List tipe handler
    private fun handleListType(
        typeName: StringBuilder,
        ksValueParameter: KSValueParameter,
        name: String,
        file: OutputStream
    ): Boolean {
        if (typeName.toString() != "kotlin.collections.List") return false
        // is generic type a Custom class
        val typeArgs = ksValueParameter.type.element!!.typeArguments
        typeArgs.firstOrNull { typeArg ->
            val subTypeResolve = typeArg.type?.resolve()?.declaration

            if (subTypeResolve is KSClassDeclaration && isDataClass(subTypeResolve)) {
                file.appendText("\t$name = listOf(\n")

                repeat(Random.nextInt(1, listElementsMaxCount)) {
                    file.appendText("${subTypeResolve.simpleName.asString()}(\n")
                    generateValues(file, subTypeResolve.primaryConstructor!!.parameters)
                    file.appendText(",\n")
                }
                file.appendText("),\n")
            } else {
                file.appendText(
                    "\t$name = listOf(${generateListItems(ksValueParameter)}),\n"
                )
            }
            false
        }
        return true
    }

    private fun handleClassType(typeResolve: KSDeclaration, name: String, file: OutputStream): Boolean {
        if (!(typeResolve is KSClassDeclaration && isDataClass(typeResolve))) return false
        file.appendText("$name = ${typeResolve.simpleName.asString()}(\n")
        generateValues(file, typeResolve.primaryConstructor!!.parameters)
        file.appendText(",\n")
        return true
    }



    override fun generateValueByType(
        ksValueParameter: KSValueParameter,
        type: String,
        key: String
    ): String =
        when (type) {
            "kotlin.String" -> "\t$key = \"${generateString(key)}\",\n"
            "kotlin.Int" -> "\t$key = ${generateInt(key)},\n"
            "kotlin.Boolean" -> "\t$key = ${generateBoolean(key)},\n"
            "kotlin.Long" -> "\t$key = ${generateLong(key)},\n"
            else -> "\t$key = null,\n"
        }

    private fun generateListItems(ksValueParameter: KSValueParameter): String {
        val type = getGenericValueByType(ksValueParameter)
        val repeater: (() -> String) -> String = { generator ->
            var result = ""
            repeat(Random.nextInt(1, listElementsMaxCount)) { result+=generator() }
            result
        }
        return when (type) {
            "kotlin.String" -> repeater { "\"${generateString("")}\",\n" }
            "kotlin.Int" -> repeater { "${generateInt("")},\n" }
            "kotlin.Boolean" -> repeater { "${generateBoolean("")},\n" }
            "kotlin.Long" -> repeater { "${generateLong("")},\n" }
            else -> "\nnull\n"
        }
    }

    private fun getGenericValueByType(ksValueParameter: KSValueParameter): String {
        val typeArgs = ksValueParameter.type.element!!.typeArguments
        if (typeArgs.isNotEmpty()) {
            typeArgs.firstOrNull {
                return it.type?.resolve()?.declaration?.qualifiedName?.asString() ?: "ERROR"
            }
        }
        return "ERROR"
    }

    override fun generateLong(key: String): Long {
        if (check(dateKeys, key.lowercase())) {
            return System.currentTimeMillis()
        }
        return Random.nextLong(0, longMaxRange)
    }

    override fun generateString(key: String): String {
        val keyLowercase = key.lowercase()
        return stringList.find {
            check(it.first, keyLowercase)
        }?.let { getRandomFromList(it.second) } ?: generateRandomString()
    }

    override fun generateBoolean(key: String): Boolean {
        return Random.nextBoolean()
    }

    override fun generateInt(key: String): Int =
        if (check(ageKeys, key)) {
            Random.nextInt(10, 88)
        } else {
            Random.nextInt(randomIntMin, randomIntMax)
        }

    private fun check(fingerprintTypes: Set<String>, fingerprint: String): Boolean {
        fingerprintTypes.forEach { if (fingerprint.contains(it)) return true }
        return false
    }

    private fun getRandomFromList(list: List<String>): String = list[Random.nextInt(0, list.size)]

    private fun isDataClass(classDeclaration: KSDeclaration): Boolean {
        return classDeclaration is KSClassDeclaration &&
                classDeclaration.classKind == ClassKind.CLASS &&
                classDeclaration.modifiers.contains(Modifier.DATA)
    }

    private fun generateRandomString(): String {
        val length = Random.nextInt(randomStringMin, randomStringMax) // Adjust the range as needed
        val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length).map { chars.random() }.joinToString("")
    }
}
