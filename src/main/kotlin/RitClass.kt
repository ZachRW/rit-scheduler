import biweekly.util.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

class RitClass(
    val fullName: String,
    dateRangeStr: String,
    val enrollmentStatus: EnrollmentStatus,
    val dayTimes: List<DayTime>
) {
    val name: String
    val code: String
    val startDate: LocalDate
    val endDate: LocalDate

    init {
        val nameParts = fullName.split(" - ")
        code = nameParts[0]
        name = nameParts[1]

        val (startDateStr, endDateStr) = dateRangeStr.split(" - ")
        startDate = LocalDate.parse(startDateStr, dateFormat)
        endDate = LocalDate.parse(endDateStr, dateFormat)
    }
}

val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mma")

class DayTime(dayTimeStr: String, val location: String) {
    val days: List<DayOfWeek>
    val startTime: LocalTime
    val endTime: LocalTime

    init {
        val dayTimeParts = dayTimeStr.split(' ')

        val daysStr = dayTimeParts[0]
        days = parseDaysOfWeek(daysStr)

        startTime = LocalTime.parse(dayTimeParts[1], timeFormat)
        endTime = LocalTime.parse(dayTimeParts[3], timeFormat)
    }
}

enum class EnrollmentStatus(val label: String) {
    ENROLLED("Enrolled"),
    WAITING("Waiting"),
    DROPPED("Dropped")
}

fun getEnrollmentStatus(str: String) =
    EnrollmentStatus.values().first { it.label == str }
