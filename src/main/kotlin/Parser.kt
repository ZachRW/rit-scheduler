import java.io.File

val table1Pattern = Regex("Status.*Deadlines")
val table2Pattern = Regex("Class.*End Date")
val timeRangePattern = Regex("\\d{2}/\\d{2}/\\d{4} - \\d{2}/\\d{2}/\\d{4}")

fun parse(text: String): List<RitClass> {
    val lines = text.lines()
        .let { it.subList(46, it.size - 8) }
        .filter { it.isNotEmpty() }

    val classStartLines = lines
        .indexesOfAll { it.matches(table1Pattern) }
        .map { it - 1 }

    val classes = mutableListOf<RitClass>()
    for (classLines in lines.splitAt(classStartLines)) {
        classes.add(parseClass(classLines))
    }
    return classes
}

private fun parseClass(lines: List<String>): RitClass {
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

    val dayTimes = mutableListOf<DayTime>()
    for (sectionLines in components) {
        val dayTime = parseComponent(sectionLines)
        if (dayTime != null) {
            dayTimes.add(dayTime)
        }
    }
    return RitClass(name, dateRange, status, dayTimes)
}

private fun parseComponent(lines: List<String>): DayTime? {
    val dayTimeStr = lines[lines.size - 4]
    val location = lines[lines.size - 3]

    if (location == "Split component shows meetings") {
        return null
    }
    return DayTime(dayTimeStr, location)
}

fun main() {
    val classes = parse(File("input.txt").readText())
    println(classes)
}
