import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import generatorengine.NameGeneratorEngine
import generatorengine.NameGeneratorEngineV1
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
            file.appendText("package $packageName\n\n")
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
        return PreviewProcessor(environment.codeGenerator, NameGeneratorEngineV1())
    }
}