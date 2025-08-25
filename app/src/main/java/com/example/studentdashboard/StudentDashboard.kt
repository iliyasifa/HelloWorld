package com.example.studentdashboard

import kotlinx.coroutines.runBlocking


// ------------------ App Navigation States ------------------
sealed class Screen {
    data object MainMenu : Screen()
    data object ViewStudents : Screen()
    data object AddStudent : Screen()
    data object Exit : Screen()
}

// ------------------ Global State ------------------
val studentList = mutableListOf(
    Student(1, "Ali", 22),
    Student(2, "Sara", 19)
)

// ------------------ Main ------------------
fun main() = runBlocking {
    var currentScreen: Screen = Screen.MainMenu

    while (currentScreen != Screen.Exit) {
        currentScreen = when (currentScreen) {
            is Screen.MainMenu -> showMainMenu()
            is Screen.ViewStudents -> showAllStudents()
            is Screen.AddStudent -> addNewStudent()
            is Screen.Exit -> {
                println("Exiting the app...")
                Screen.Exit
            }
        }
    }
}

// ------------------ Screens ------------------
fun showMainMenu(): Screen {
    println("\n=== Student Dashboard ===")
    println("1 View Students")
    println("2 Add Student")
    println("3 Exit")

    print("Choose option (1-3): ")
    return when (readlnOrNull()?.trim()) {
        "1" -> Screen.ViewStudents
        "2" -> Screen.AddStudent
        "3" -> Screen.Exit
        else -> {
            println("Invalid option, try again.")
            Screen.MainMenu
        }
    }
}

fun showAllStudents(): Screen {
    println("\nAll Students:")
    if (studentList.isEmpty()) {
        println("No students found.")
    } else {
        studentList.forEach { println("• ID: ${it.id}, Name: ${it.name}, Age: ${it.age}") }
    }

    println("\nPress Enter to go back to Main Menu.")
    readlnOrNull()
    return Screen.MainMenu
}

fun addNewStudent(): Screen {
    print("Enter Student ID: ")
    val id = readlnOrNull()?.toIntOrNull()

    print("Enter Student Name: ")
    val name = readlnOrNull()?.trim()

    print("Enter Student Age: ")
    val age = readlnOrNull()?.toIntOrNull()

    if (id != null && !name.isNullOrEmpty() && age != null) {
        val newStudent = Student(id, name, age)
        studentList.add(newStudent)
        println("Student added: $newStudent")
    } else {
        println("Invalid input. Student not added.")
    }

    println("\nPress Enter to go back to Main Menu.")
    readlnOrNull()
    return Screen.MainMenu
}
