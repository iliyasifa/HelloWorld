package com.example.studentdashboard

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// ------------------ Student Data Class ------------------
data class Student(
    val id: Int,
    var name: String,
    var age: Int
)

// ------------------ Async Student Manager ------------------
class AsyncStudentManager {
    private val students = mutableListOf<Student>()
    private var nextId = 1

    // Add student with delay (simulating DB/API insert)
    suspend fun addStudent(name: String, age: Int) {
        delay(1000) // simulate API call
        val student = Student(nextId++, name, age)
        students.add(student)
        println("Added (async): $student")
    }

    // Remove student
    suspend fun removeStudent(id: Int, onResult: (Boolean) -> Unit) {
        delay(500) // simulate DB delay
        val removed = students.removeIf { it.id == id }
        onResult(removed)
    }

    // Get all students (callback + async)
    suspend fun getAll(onResult: (List<Student>) -> Unit) {
        delay(800) // simulate API fetch
        onResult(students)
    }

    // Find by Age Range
    suspend fun filterByAgeRange(min: Int, max: Int, onResult: (List<Student>) -> Unit) {
        delay(700)
        val filtered = students.filter { it.age in min..max }
        onResult(filtered)
    }

    // Summaries using map + when
    suspend fun getStudentSummaries(onResult: (List<String>) -> Unit) {
        delay(600)
        val summaries = students.map { s ->
            val category = when (s.age) {
                in 0..12 -> "Child"
                in 13..19 -> "Teenager"
                in 20..25 -> "Young Adult"
                in 26..60 -> "Adult"
                else -> "Senior"
            }
            "${s.name} (${s.age}) → $category"
        }
        onResult(summaries)
    }
}

// ------------------ MAIN ------------------
fun main() = runBlocking {
    val manager = AsyncStudentManager()

    // Launching coroutines in parallel
    val job = launch {
        manager.addStudent("Ali", 22)
        manager.addStudent("Sara", 19)
        manager.addStudent("John", 25)
    }
    /// wait until students are added
    job.join()

    // Fetch all students
    manager.getAll { list ->
        println("All Students:")
        list.forEach { println(it) }
    }

    // Filter by Age
    manager.filterByAgeRange(18, 23) { list ->
        println("Students between 18-23:")
        list.forEach { println(it) }
    }

    // Summaries
    manager.getStudentSummaries { summaries ->
        println("Summaries:")
        summaries.forEach { println(it) }
    }

    // Remove student
    manager.removeStudent(2) { success ->
        println(if (success) "Removed student with ID 2" else "Student not found")
    }

    // Show remaining
    manager.getAll { list ->
        println("Final Student List:")
        list.forEach { println(it) }
    }
}
