import biweekly.util.DayOfWeek

/**
 * Returns indexes of the all elements matching the given [predicate].
 */
inline fun <T> List<T>.indexesOfAll(predicate: (T) -> Boolean): List<Int> {
    val indexes = mutableListOf<Int>()
    for ((index, item) in this.withIndex()) {
        if (predicate(item)) {
            indexes.add(index)
        }
    }
    return indexes
}

fun <T> List<T>.splitAt(indexes: List<Int>): List<List<T>> {
    val subLists = mutableListOf<List<T>>()
    for (i in 0 until indexes.lastIndex) {
        subLists.add(subList(indexes[i], indexes[i + 1]))
    }
    return subLists
}

fun List<String>.concatLines(): String =
    this.joinToString("\n")

fun parseDayOfWeek(dayOfWeekStr: String) =
    when (dayOfWeekStr) {
        "Mo" -> DayOfWeek.MONDAY
        "Tu" -> DayOfWeek.TUESDAY
        "We" -> DayOfWeek.WEDNESDAY
        "Th" -> DayOfWeek.THURSDAY
        "Fr" -> DayOfWeek.FRIDAY
        else -> error("Invalid day of week: '$dayOfWeekStr'")
    }

fun parseDaysOfWeek(daysStr: String): List<DayOfWeek> {
    if (daysStr.length % 2 != 0) {
        error("Invalid days of week string: '$daysStr'")
    }

    val days = mutableListOf<DayOfWeek>()
    for (i in days.indices step 2) {
        val dayStr = daysStr.substring(i, i + 2)
        days.add(parseDayOfWeek(dayStr))
    }
    return days
}
