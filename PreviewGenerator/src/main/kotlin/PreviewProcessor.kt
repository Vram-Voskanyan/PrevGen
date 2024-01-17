import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import generatorengine.NameGeneratorEngine
import generatorengine.NameGeneratorEngineV1
import java.io.OutputStream

// Git upload
// mavenPublish
// enum, non-dataClass
// readme?
// map?
// annotation Values, length, custom field
// file read?

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
            file.appendText("package $packageName\n\n")
            file.appendText("val ${className.replaceFirstChar { className.lowercase()[0] }}Preview = $className(\n")
            generateValues(file, function.parameters)
            file.close()
        }

        private fun generateValues(file: OutputStream, parameters: List<KSValueParameter>) {
            parameters.forEach {
                val name = it.name!!.asString()
                val typeResolve = it.type.resolve().declaration
                if (typeResolve is KSClassDeclaration && isDataClass(typeResolve)) {
                    file.appendText("$name = ${typeResolve.simpleName.asString()}(\n")
                    generateValues(file, typeResolve.primaryConstructor!!.parameters)
                    file.appendText(",\n")
                } else {
                    val typeName = StringBuilder(
                        it.type.resolve().declaration.qualifiedName?.asString() ?: "<ERROR>"
                    )
                    file.appendText( generatorEngine.generateValueByType(typeName.toString(), name))
                }
            }
            file.appendText(")")
        }
    }

    private fun isDataClass(classDeclaration: KSDeclaration): Boolean {
        return classDeclaration is KSClassDeclaration &&
                classDeclaration.classKind == ClassKind.CLASS &&
                classDeclaration.modifiers.contains(Modifier.DATA)
    }

}

class PreviewProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return PreviewProcessor(environment.codeGenerator, NameGeneratorEngineV1())
    }
}