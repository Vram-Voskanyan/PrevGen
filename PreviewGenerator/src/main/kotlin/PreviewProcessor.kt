/*
 * Creator: Vram Voskanyan (vram.arm@gmail.com) on 21/01/2024, 21:21 Last modified: 21/01/2024, 21:03 Ⓒ 2024
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import generatorengine.samples.NameGeneratorEngine
import generatorengine.v1.NameGeneratorEngineV1
import generatorengine.v1.TypeGeneratorsV1
import java.io.OutputStream

fun OutputStream.appendText(str: String) {
    this.write(str.toByteArray())
}

class PreviewProcessor(
    val codeGenerator: CodeGenerator,
    val generatorEngine: NameGeneratorEngine
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation("DataPreview")
        val ret = symbols.filter { !it.validate() }.toList()
        symbols
            .filter { it is KSClassDeclaration && it.validate() }
            .forEach { it.accept(PreviewVisitor(), Unit) }
        return ret
    }

    inner class PreviewVisitor : KSVisitorVoid() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            classDeclaration.primaryConstructor!!.accept(this, data)
        }

        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            val parent = function.parentDeclaration as KSClassDeclaration
            val packageName = parent.containingFile!!.packageName.asString()
            val className = parent.simpleName.asString()

            val file = codeGenerator.createNewFile(
                Dependencies(true, function.containingFile!!),
                packageName,
                className
            )
            if (packageName.isNotBlank()) {
                file.appendText("package $packageName\n")
            }
            file.appendText("val ${className.replaceFirstChar { className.lowercase()[0] }}Preview = $className(\n")
            generatorEngine.generateValues(file, function.parameters)
            file.close()
        }
    }

}

class PreviewProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return PreviewProcessor(environment.codeGenerator, NameGeneratorEngineV1(TypeGeneratorsV1()))
    }
}