package com.example.studentdashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class StudentViewModel : ViewModel() {

    private val _students = MutableLiveData<List<Student>>(emptyList())
    val students: LiveData<List<Student>> get() = _students

    private var nextId = 1

    fun addStudent(name: String, age: Int) {
        val newStudent = Student(nextId++, name, age)
        val updatedList = _students.value!!.toMutableList()
        updatedList.add(newStudent)
        _students.value = updatedList
    }
}
