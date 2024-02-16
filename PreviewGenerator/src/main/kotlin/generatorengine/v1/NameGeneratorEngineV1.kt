/*
 * Creator: Vram Voskanyan (vram.arm@gmail.com) on 21/01/2024, 21:22 Last modified: 21/01/2024, 21:21 Ⓒ 2024
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package generatorengine.v1

import appendText
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.Modifier
import generatorengine.samples.NameGeneratorEngine
import generatorengine.TypeGenerators
import java.io.OutputStream
import kotlin.random.Random

class NameGeneratorEngineV1(private val typeGenerators: TypeGenerators) : NameGeneratorEngine {
    // can be added extra config for future. if needed huge data to test.
    private val listElementsMaxCount = 5

    override fun generateValues(file: OutputStream, parameters: List<KSValueParameter>) {
        parameters.forEach {
            getValueAndGeneratePreview(it, file)
        }
        file.appendText(")")
    }

    private fun getValueAndGeneratePreview(ksValueParameter: KSValueParameter, file: OutputStream) {
        val name = ksValueParameter.name!!.asString()
        val typeResolve = ksValueParameter.type.resolve().declaration
        val typeName by lazy { StringBuilder(typeResolve.qualifiedName?.asString() ?: "<ERROR>") }
        if (
            !handleClassType(typeResolve, name, file) &&
            !handleListType(typeName, ksValueParameter, name, file)
        ) {
            file.appendText(generateValueByType(ksValueParameter, typeName.toString(), name))
        }
    }

    private fun isDataClass(classDeclaration: KSDeclaration): Boolean {
        return classDeclaration is KSClassDeclaration &&
                classDeclaration.classKind == ClassKind.CLASS &&
                classDeclaration.modifiers.contains(Modifier.DATA)
    }

    // Class
    private fun handleClassType(
        typeResolve: KSDeclaration,
        name: String,
        file: OutputStream
    ): Boolean {
        if (!(typeResolve is KSClassDeclaration && isDataClass(typeResolve))) return false
        file.appendText("$name = ${typeResolve.simpleName.asString()}(\n")
        generateValues(file, typeResolve.primaryConstructor!!.parameters)
        file.appendText(",\n")
        return true
    }

    // List
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

    override fun generateValueByType(
        ksValueParameter: KSValueParameter,
        type: String,
        key: String
    ): String {
        return "\t$key = " + when (type) {
            "kotlin.String" -> "\"${typeGenerators.generateString(key)}\""
            "kotlin.Int" -> "${typeGenerators.generateInt(key)}"
            "kotlin.Boolean" -> "${typeGenerators.generateBoolean(key)}"
            "kotlin.Long" -> "${typeGenerators.generateLong(key)}"
            "kotlin.Double" -> "${typeGenerators.generateDouble(key)}"
            else -> "null"
        } + ",\n"
    }

    private fun generateListItems(ksValueParameter: KSValueParameter): String {
        val type = getGenericValueByType(ksValueParameter)
        val repeater: (() -> String) -> String = { generator ->
            var result = ""
            repeat(Random.nextInt(1, listElementsMaxCount)) { result += generator() }
            result
        }
        return when (type) {
            "kotlin.String" -> repeater { "\"${typeGenerators.generateString("")}\",\n" }
            "kotlin.Int" -> repeater { "${typeGenerators.generateInt("")},\n" }
            "kotlin.Boolean" -> repeater { "${typeGenerators.generateBoolean("")},\n" }
            "kotlin.Long" -> repeater { "${typeGenerators.generateLong("")},\n" }
            "kotlin.Double" -> repeater { "${typeGenerators.generateDouble("")},\n" }
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

}
