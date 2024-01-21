/*
 * Creator: Vram Voskanyan (vram.arm@gmail.com) on 21/01/2024, 21:21 Last modified: 21/01/2024, 20:58 Ⓒ 2024
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package generatorengine.v1

import generatorengine.TypeGenerators
import generatorengine.ageKeys
import generatorengine.dateKeys
import generatorengine.stringList
import kotlin.random.Random
class TypeGeneratorsV1: TypeGenerators {

    private val longMaxRange: Long = 10000
    private val randomStringMin = 4
    private val randomStringMax = 10
    private val randomIntMin = 100
    private val randomIntMax = 1000

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

    private fun generateRandomString(): String {
        val length = Random.nextInt(randomStringMin, randomStringMax) // Adjust the range as needed
        val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length).map { chars.random() }.joinToString("")
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

}