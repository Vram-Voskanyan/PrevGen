package generatorengine

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


