import java.io.File

val table1Pattern = Regex("Status.*Deadlines")
val table2Pattern = Regex("Class.*End Date")
val timeRangePattern = Regex("\\d{2}/\\d{2}/\\d{4} - \\d{2}/\\d{2}/\\d{4}")

fun parse(text: String) {
    val lines = text.lines()
        .let { it.subList(46, it.size - 8) }
        .filter { it.isNotEmpty() }

    val classStartLines = lines
        .indexesOfAll { it.matches(table1Pattern) }
        .map { it - 1 }

    for (classLines in lines.splitAt(classStartLines)) {
        parseClass(classLines)
        println("-----Class end-----\n")
    }
}

private fun parseClass(lines: List<String>) {
    val name = lines[0]
    val status = getEnrollmentStatus(lines[2])

    val trimIndex = lines.indexOfFirst { it.matches(table2Pattern) }
    val trimmedLines = lines.subList(trimIndex + 1, lines.size)

    val componentIndexes = trimmedLines
        .indexesOfAll { it.matches(timeRangePattern) }
        .map { it + 1 }
        .toMutableList()
    componentIndexes.add(0, 0)
    val components = trimmedLines.splitAt(componentIndexes)

    val dateRange = components[0].last()
    val startDate = dateRange.substring(0 until 10)
    val endDate = dateRange.substring(13 until 23)

    for (sectionLines in components) {
        parseComponent(sectionLines)
        println("-----Section end-----")
    }

    println(
        """
        name: $name
        status: $status
        startDate: $startDate
        endDate: $endDate
    """.trimIndent()
    )
}

private fun parseComponent(lines: List<String>) {
    val dayTimeStr = lines[lines.size - 4]
    val location = lines[lines.size - 3]

    if (location == "Split component shows meetings") {
        return
    }

    DayTime(dayTimeStr, location)

    println(
        """
        dayTimeStr: $dayTimeStr
        location: $location
    """.trimIndent()
    )
}

fun main() {
    parse(File("input.txt").readText())
}
