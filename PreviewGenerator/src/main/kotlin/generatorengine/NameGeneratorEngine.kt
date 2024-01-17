package generatorengine

interface NameGeneratorEngine {
    fun generateLong(key: String): Long
    fun generateString(key: String): String
    fun generateBoolean(key: String): Boolean
    fun generateInt(key: String): Int
    fun generateValueByType(type: String, key: String): String
}