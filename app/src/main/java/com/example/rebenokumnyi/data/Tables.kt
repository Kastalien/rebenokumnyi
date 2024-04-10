package com.example.rebenokumnyi.data

import com.example.rebenokumnyi.R
import com.google.firebase.database.Exclude

enum class Roles(val role: Int) { UNKNOWN(0), ADMIN(1), TEACHER(2), PARENTUSER(3) }

val EmptyRole = UserRole("", Roles.UNKNOWN, "")
var currentRole: UserRole = EmptyRole
var groups: List<Group> = listOf()
var subjects: List<Subject> = listOf()
var students: List<Student> = listOf()
var roles: List<UserRole> = listOf()
var selectedJournal: List<Journal> = listOf()
var groupSchedule: List<Schedule> = listOf()
var daysOfWeek = mapOf<Int, Int>(
    Pair(1, R.string.day_of_week_1),
    Pair(2, R.string.day_of_week_2),
    Pair(3, R.string.day_of_week_3),
    Pair(4, R.string.day_of_week_4),
    Pair(5, R.string.day_of_week_5),
    Pair(6, R.string.day_of_week_6),
    Pair(7, R.string.day_of_week_7)
)

data class Group(
    var id: Int = -1,
    var name: String = "",
    var userId: String = ""
) {
    fun save() {
        AppData.database.child("groups").child(id.toString()).setValue(this)
    }

    fun setNewTeacher(newUserId: String) {
        userId = newUserId
        save()
    }

    @Exclude
    fun getTeacher(): UserRole? {
        return roles.find { it.userId == userId }
    }

    fun setNewName(newGroupName: String) {
        name = newGroupName
        save()
    }
}

data class UserRole(
    var userId: String = "",
    var role: Roles = Roles.UNKNOWN,
    var name: String = ""
) {

    fun save() {
        AppData.database.child("roles").child(userId).setValue(this)
    }

    fun addNewRole(newRole: Roles) {
        role = newRole
        save()
    }

    fun setNewName(newName: String) {
        name = newName
        save()
    }

    fun remove() {
        AppData.database.child("roles").child(userId).removeValue()
    }
}

data class Schedule(
    var id: String = "",
    var groupId: Int = 0,
    var dayOfWeek: Int = 1,
    var subjectId: String = "",
    var start: String = "",
    var duration: Int = 0
) {
    @Exclude
    fun getSubject(): Subject? {
        return subjects.find { it.id == subjectId }
    }

    @Exclude
    fun getNote(): String? {
        return selectedJournal.find { it.scheduleId == id }?.note
    }
    @Exclude
    fun getNoteId(): String? {
        return selectedJournal.find { it.scheduleId == id }?.id
    }

    fun save() {
        AppData.database.child("schedule").child(id).setValue(this)
    }

    fun addNewSchedule() {
        AppData.database.child("schedule").push().key?.let {
            id = it
            save()
        }
    }
    fun remove() {
        AppData.database.child("schedule").child(id).removeValue()
    }
}

data class Subject(
    var id: String = "",
    var name: String = ""
) {
    fun save() {
        AppData.database.child("subjects").child(id).setValue(this)
    }
    fun setNewSubject(newSubjectName: String):Subject? {
        AppData.database.child("subjects").push().key?.let {
            id = it
            name = newSubjectName
            save()
            return this
        }
        return null
    }
}

data class Student(
    var id: String = "",
    var groupId: Int = 0,
    var userId: String = "",
    var name: String = ""
) {
    @Exclude
    fun getGroup(): Group? {
        return groups.find { it.id == groupId }
    }

    fun save() {
        AppData.database.child("students").child(id).setValue(this)
    }

    fun addNewStudent() {
        AppData.database.child("students").push().key?.let {
            id = it
            save()
        }
    }
    fun setNewGroup(newGroupId: Int) {
        groupId = newGroupId
        save()
    }
    fun remove() {
        AppData.database.child("students").child(id).removeValue()
    }

    fun setNewName(newName: String) {
        name = newName
        save()
    }
}

data class Journal(
    var id: String = "",
    var date: String = "",
    var scheduleId: String = "",
    var studentId: String = "",
    var note: String = ""
) {

    fun save() {
        AppData.database.child("journal").child(id).setValue(this)
    }

    fun addNewJournal() {
        AppData.database.child("journal").push().key?.let {
            id = it
            save()
        }
    }
    fun remove() {
        AppData.database.child("schedule").child(id).removeValue()
    }
}