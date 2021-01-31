import biweekly.util.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class RitClass(
    val name: String,
    val code: String,
    val enrollmentStatus: EnrollmentStatus,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val dayTimes: List<DayTime>
)

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
