import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Student(
    var id: Int, // Ændret til id
    var name: String, // Ændret til name
    var last_name: String, // Ændret til lastName
    var birthday: String, // Ændret til birthday
    var department_id: Int?, // Ændret til departmentId
) : Serializable {
    constructor(
        name: String,
        lastName: String,
        birthday: String
    ) : this(-1, name, lastName, birthday, 0)

    override fun toString(): String {
        return "$id, $name, $last_name, $birthday, $department_id"
    }

        fun isRegistered(): Boolean {
            // Implementation to determine if student is registered
            return true // Example implementation, replace with your logic
        }
}
