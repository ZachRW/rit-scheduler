data class RitClass(
    val name: String,
    val code: String,
    val enrollmentStatus: EnrollmentStatus,
    val components: List<Component>
)

data class Component(

)

data class DayTime(

)

enum class EnrollmentStatus(label: String) {
    ENROLLED("Enrolled"),
    WAITING("Waiting"),
    DROPPED("Dropped")
}