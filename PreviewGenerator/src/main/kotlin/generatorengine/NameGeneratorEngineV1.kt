package generatorengine

import kotlin.random.Random

class NameGeneratorEngineV1: NameGeneratorEngine {
    // can be added extra config for future. if needed huge data to test.
    private val longMaxRange: Long = 10000
    private val randomStringMin = 4
    private val randomStringMax = 10
    private val randomIntMin = 100
    private val randomIntMax = 1000


    override fun generateValueByType(type: String, key: String): String =
        when (type) {
            "kotlin.String" -> "\t$key = \"${generateString(key)}\",\n"
            "kotlin.Int" -> "\t$key = ${generateInt(key)},\n"
            "kotlin.Boolean" -> "\t$key = ${generateBoolean(key)},\n"
            "kotlin.Long" -> "\t$key = ${generateLong(key)},\n"
            "kotlin.collections.List" -> "\t$key = emptyList(),\n"
            else -> "\t$key = null,\n"
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
        if (check(ageKeys, key)) Random.nextInt(10, 88) else {
            Random.nextInt(randomIntMin, randomIntMax) //todo move to top
        }

    private fun check(fingerprintTypes: Set<String>, fingerprint: String): Boolean {
        fingerprintTypes.forEach { if (fingerprint.contains(it)) return true }
        return false
    }

    private fun getRandomFromList(list: List<String>): String = list[Random.nextInt(0, list.size)]

    private fun generateRandomString(): String {
        val length = Random.nextInt(randomStringMin, randomStringMax) // Adjust the range as needed
        val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length).map { chars.random() }.joinToString("")
    }

}
