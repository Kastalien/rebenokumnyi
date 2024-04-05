package com.example.rebenokumnyi.data

import com.example.rebenokumnyi.R
import com.google.firebase.database.Exclude

enum class Roles(val role: Int) { UNKNOWN(0), ADMIN(1), TEACHER(2), PARENTUSER(3) }

val EmptyRole = UserRole("", Roles.UNKNOWN, 0, "")
var currentRole: UserRole = EmptyRole
var groups: List<Group> = listOf()
var roles: List<UserRole> = listOf()
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
    var groupId: Int = 0,
    var name: String = ""
) {
    fun getGroup(): Group? {
        return groups.find { it.id == groupId }
    }

    fun save() {
        AppData.database.child("roles").child(userId).setValue(this)
    }

    fun setNewRole(newRole: Roles) {
        role = newRole
        save()
    }

    fun setNewGroup(newGroup: Int) {
        groupId = newGroup
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
    var subject: String = "",
    var start: String = "",
    var duration: Int = 0
) {
    @Exclude
    fun getGroup(): Group? {
        return groups.find { it.id == groupId }
    }

    fun save() {
        AppData.database.child("schedule").child(id).setValue(this)
    }

    fun setNewTeacher(newUserId: String) {
        AppData.database.child("schedule").push().key?.let {
            id = it
            save()
        }
    }

    fun setNewSubjectName(newSubjectName: String) {
        subject = newSubjectName
        save()
    }

    fun remove() {
        AppData.database.child("schedule").child(id).removeValue()
    }
}
