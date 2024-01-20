package generatorengine

import com.google.devtools.ksp.symbol.KSValueParameter
import java.io.OutputStream

interface NameGeneratorEngine {
    fun generateLong(key: String): Long
    fun generateString(key: String): String
    fun generateBoolean(key: String): Boolean
    fun generateInt(key: String): Int
    fun generateValueByType(ksValueParameter: KSValueParameter, type: String, key: String): String
    fun generateValues(file: OutputStream, parameters: List<KSValueParameter>)
}