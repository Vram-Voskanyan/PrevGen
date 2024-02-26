/*
 * Creator: Vram Voskanyan (vram.arm@gmail.com) on 26/02/2024, 20:10 Last modified: 26/02/2024, 20:10 Ⓒ 2024
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package generatorengine.v2

import generatorengine.TypeGenerators
import generatorengine.ageKeys
import generatorengine.dateKeys
import generatorengine.stringList
import kotlin.random.Random

class TypeGeneratorsV2ConstantValues : TypeGenerators {

    // Const Values
    private val constLong = 123_456_789L;
    private val constInt = 13_456;
    private val constBoolean = true
    private val constDouble = 45.765
    private val constString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."

    override fun generateLong(key: String): Long = constLong

    override fun generateString(key: String): String = constString

    override fun generateBoolean(key: String): Boolean = constBoolean
    override fun generateInt(key: String): Int = constInt

    override fun generateDouble(key: String): Double = constDouble

}