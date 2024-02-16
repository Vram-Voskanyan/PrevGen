/*
 * Creator: Vram Voskanyan (vram.arm@gmail.com) on 21/01/2024, 21:21 Last modified: 21/01/2024, 20:41 Ⓒ 2024
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package generatorengine

interface TypeGenerators {
    fun generateLong(key: String): Long
    fun generateString(key: String): String
    fun generateBoolean(key: String): Boolean
    fun generateInt(key: String): Int
    fun generateDouble(key: String): Double
}