package com.example.helloworld

data class Student(
    val id: Int,
    var name: String,
    var age: Int
)

// StudentManager.kt
class StudentManager {
    private val students = mutableListOf<Student>()
    private var nextId = 1

    // ✅ Add Student
    fun addStudent(name: String, age: Int) {
        val student = Student(nextId++, name, age)
        students.add(student)
        println("✅ Added: $student")
    }

    // ✅ Remove Student
    fun removeStudent(id: Int) {
        val removed = students.removeIf { it.id == id }
        if (removed) println("❌ Removed student with ID $id")
        else println("⚠️ No student found with ID $id")
    }

    // ✅ Update Student
    fun updateStudent(id: Int, newName: String, newAge: Int) {
        val student = students.find { it.id == id }
        if (student != null) {
            student.name = newName
            student.age = newAge
            println("✏️ Updated: $student")
        } else {
            println("⚠️ No student found with ID $id")
        }
    }

    // ✅ Display Students (with forEach)
    fun displayStudents() {
        if (students.isEmpty()) {
            println("📭 No students found")
        } else {
            println("👨‍🎓 Student List:")
            students.forEach { student ->
                println("ID: ${student.id}, Name: ${student.name}, Age: ${student.age}")
            }
        }
    }

    // ✅ Filter by Age
    fun filterByAge(minAge: Int) {
        val filtered = students.filter { it.age >= minAge }
        println("🔍 Students with age >= $minAge:")
        filtered.forEach { println(it) }
    }

    // ✅ Filter by Age Range
    fun filterByAgeRange(min: Int, max: Int) {
        val filtered = students.filter { it.age in min..max }
        println("🔍 Students aged $min to $max:")
        filtered.forEach { println(it) }
    }

    // ✅ Get All Students
    fun getAll(): List<Student> = students

    // ✅ Extract Names (using map)
    fun getStudentNames(): List<String> {
        return students.map { it.name }
    }

    // ✅ Categorize Ages (using when + map)
    fun getStudentSummaries(): List<String> {
        return students.map { student ->
            val category = categorizeStudentAge(student.age)
            "${student.name} (${student.age}) → $category"
        }
    }

    // ✅ Age Category (using when)
    private fun categorizeStudentAge(age: Int): String {
        return when (age) {
            in 0..12 -> "Child"
            in 13..19 -> "Teenager"
            in 20..25 -> "Young Adult"
            in 26..60 -> "Adult"
            else -> "Senior"
        }
    }
}

// Main.kt
fun main() {
    val manager = StudentManager()

    // Add Students
    manager.addStudent("Ali", 22)
    manager.addStudent("Sara", 19)
    manager.addStudent("John", 25)

    // Display
    manager.displayStudents()

    // Filter
    manager.filterByAge(21)
    manager.filterByAgeRange(18, 23)

    // Update
    manager.updateStudent(2, "Sara Updated", 20)

    // Names
    println("📚 Student Names: ${manager.getStudentNames()}")

    // Summaries
    println("📋 Student Summaries:")
    manager.getStudentSummaries().forEach { println(it) }

    // Remove
    manager.removeStudent(1)

    // Final List
    manager.displayStudents()
}
