package com.example.helloworld

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// ------------------ Sealed API Response ------------------
sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
    data object Loading : ApiResponse<Nothing>()
}

// ------------------ Fake API Service ------------------
class FakeStudentApi {
    private val students = listOf(
        Student(1, "Ali", 22),
        Student(2, "Sara", 19),
        Student(3, "John", 25)
    )

    // Simulates API fetching with random error possibility
    suspend fun fetchStudents(): ApiResponse<List<Student>> {
        delay(1000)
        return if ((1..10).random() > 3) { // 70% success
            ApiResponse.Success(students)
        } else {
            ApiResponse.Error("Network Error: Failed to fetch students")
        }
    }

    // Simulate single student fetch
    suspend fun fetchStudentById(id: Int): ApiResponse<Student> {
        delay(700)
        val student = students.find { it.id == id }
        return student?.let { ApiResponse.Success(it) }
            ?: ApiResponse.Error("Student with ID $id not found")
    }
}


// ------------------ MAIN ------------------
fun main() = runBlocking {
    val api = FakeStudentApi()

    println("📡 Fetching all students...")
    val result1 = api.fetchStudents()
    handleResponse(result1)

    println("\n📡 Fetching single student with ID 2...")
    val result2 = api.fetchStudentById(2)
    handleResponse(result2)

    println("\n📡 Fetching single student with ID 99...")
    val result3 = api.fetchStudentById(99)
    handleResponse(result3)
}

// ------------------ Response Handler ------------------
fun <T> handleResponse(response: ApiResponse<T>) {
    when (response) {
        is ApiResponse.Success -> println("✅ Success → ${response.data}")
        is ApiResponse.Error -> println("❌ Error → ${response.message}")
        is ApiResponse.Loading -> println("⏳ Loading...")
    }
}
