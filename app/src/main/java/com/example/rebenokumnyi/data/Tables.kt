package com.example.rebenokumnyi.data

import com.example.rebenokumnyi.R
import com.google.firebase.database.Exclude
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

enum class Roles(val role: Int) { UNKNOWN(0), ADMIN(1), TEACHER(2), PARENTUSER(3) }

val EmptyRole = UserRole("", Roles.UNKNOWN, "")
var currentRole: UserRole = EmptyRole
var groups: List<Group> = listOf()
var subjects: List<Subject> = listOf()
var students: List<Student> = listOf()
var roles: List<UserRole> = listOf()
var selectedJournal: List<Journal> = listOf()
var selectedChat: List<Chat> = listOf()
var groupSchedule: List<Schedule> = listOf()
var centerInfo: CenterInfo = CenterInfo()
var daysOfWeek = mapOf<Int, Int>(
    Pair(1, R.string.day_of_week_1),
    Pair(2, R.string.day_of_week_2),
    Pair(3, R.string.day_of_week_3),
    Pair(4, R.string.day_of_week_4),
    Pair(5, R.string.day_of_week_5),
    Pair(6, R.string.day_of_week_6),
    Pair(7, R.string.day_of_week_7)
)

interface InfoTable {
    fun setNewInfo(name: String, info: String)
    fun addLink(it: String)

    var name: String
    var info: String
    var userId: String
    var imageLink: String
}

data class Group(
    var id: Int = -1,
    override var name: String = "",
    override var userId: String = "",
    override var info: String = "",
    override var imageLink: String = ""
) : InfoTable {
    fun save() {
        AppData.database.child("groups").child(id.toString()).setValue(this)
    }

    override fun setNewInfo(name: String, info: String){
        this.name=name
        this.info=info
        save()
    }

    override fun addLink(it: String) {
        TODO("Not yet implemented")
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
    override var userId: String = "",
    var role: Roles = Roles.UNKNOWN,
    override var name: String = "",
    override var info: String = "",
    override var imageLink: String = ""
): InfoTable {
    override fun setNewInfo(name: String, info: String){
        this.name=name
        this.info=info
        save()
    }

    override fun addLink(link: String) {
        imageLink=link
        save()
    }

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
    var id: String = "", var name: String = ""
) {
    fun save() {
        AppData.database.child("subjects").child(id).setValue(this)
    }

    fun setNewSubject(newSubjectName: String): Subject? {
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
    var id: String = "", var groupId: Int = 0, var userId: String = "", var name: String = ""
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

    @Exclude
    fun getParent(): UserRole? {
        return roles.find { it.userId == userId }
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

data class Chat(
    var id: String = "",
    var dateTime: String = "",
    var user1Id: String = "",
    var user2Id: String = "",
    var message: String = ""
) {
    @Exclude
    fun getLocalTime(): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date = df.parse(dateTime)
        df.timeZone = TimeZone.getDefault()
        df.applyPattern("dd.MM.yyyy HH:mm")
        return df.format(date)
    }

    fun save() {
        AppData.database.child("chat").child(id).setValue(this)
    }

    fun addNewMessage() {
        AppData.database.child("chat").push().key?.let {
            id = it
            save()
        }
    }
}

data class CenterInfo(
    var about: String = "", var director: String = "", var place: String = ""
) {
    fun save() {
        AppData.database.child("info").setValue(this)
    }

}