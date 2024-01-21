/*
 * Creator: Vram Voskanyan (vram.arm@gmail.com) on 21/01/2024, 21:18 Last modified: 21/01/2024, 21:03 Copyright: All rights reserved Ⓒ 2024 http://hiteshsahu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package generatorengine

import generatorengine.samples.dateTimeValues
import generatorengine.samples.firstNamesValues
import generatorengine.samples.statusValues
import generatorengine.samples.surnamesValues
import generatorengine.samples.userNamesValues

internal val dateKeys = setOf("date", "time", "timestamp")

internal val ageKeys = setOf("age", "old")

private val userNameKeys = setOf("user", "id")
private val surnameKeys = setOf("surname")
private val nameKeys = setOf("name")

private val statusKeys = setOf("type", "state", "status")

internal val stringList = listOf(
    dateKeys to dateTimeValues,
    userNameKeys to userNamesValues,
    surnameKeys to surnamesValues,
    nameKeys to firstNamesValues,
    statusKeys to statusValues
)


