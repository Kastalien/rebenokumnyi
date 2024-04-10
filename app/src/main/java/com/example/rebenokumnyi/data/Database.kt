package com.example.rebenokumnyi.data

import com.google.firebase.database.getValue

fun loadRole(onEndLoading: () -> Unit) {
    if (AppData.isAuth()) {
            AppData.database.child("roles").child(AppData.getUserID()).get()
                .addOnSuccessListener { dataSnapshot ->
                    currentRole = dataSnapshot.getValue<UserRole>() ?: UserRole()
                    if (currentRole.role == Roles.UNKNOWN) {
                        currentRole.userId = AppData.getUserID()
                        currentRole.name = AppData.getUserName()
                        currentRole.save()
                    }
                    loadGroups {
                        loadStudents {
                            if ((students.any { it.userId == AppData.getUserID() }) && (AppData.currentChild.id == ""))
                                AppData.currentChild =
                                    students.first { it.userId == AppData.getUserID() }
                            onEndLoading()
                        }
                    }
                }.addOnFailureListener {
                    currentRole = EmptyRole
                    onEndLoading()
                }
    } else
        onEndLoading()
}

fun loadGroups(onEndLoading: () -> Unit) {
    if (isLocalData)
        groups = debugGroups.toMutableList()
    else {
        AppData.database.child("groups").get()
            .addOnSuccessListener {
                groups = it.getValue<ArrayList<Group>>()?.filterNotNull()?.toList() ?: listOf()
                onEndLoading()
            }.addOnFailureListener {
                groups = mutableListOf<Group>()
                onEndLoading()
            }
    }
}

fun loadSchedule(groupId:Int,onEndLoading: () -> Unit) {
    AppData.database.child("schedule").orderByChild("groupId").equalTo(groupId.toDouble()).get()
        .addOnSuccessListener { snapshot ->
            groupSchedule = snapshot.getValue<Map<String, Schedule>>()?.map { it.value }?.toList() ?: listOf()
            onEndLoading()
        }.addOnFailureListener {
            groupSchedule = mutableListOf<Schedule>()
            onEndLoading()
        }
}

fun loadSubjects(onEndLoading: () -> Unit) {
    AppData.database.child("subjects").get()
        .addOnSuccessListener { snapshot ->
            subjects = snapshot.getValue<Map<String, Subject>>()?.map { it.value }?.toList() ?: listOf()
            onEndLoading()
        }.addOnFailureListener {
            subjects = mutableListOf<Subject>()
            onEndLoading()
        }
}

fun loadAllRoles(onEndLoading: () -> Unit) {
    AppData.database.child("roles").get()
        .addOnSuccessListener { snapshot ->
            roles = snapshot.getValue<Map<String, UserRole>>()?.map { it.value }?.toList() ?: listOf()
            onEndLoading()
        }.addOnFailureListener {
            roles = listOf<UserRole>()
            onEndLoading()
        }
}

fun loadStudents(onEndLoading: () -> Unit) {
    AppData.database.child("students").get()
        .addOnSuccessListener { snapshot ->
            students = snapshot.getValue<Map<String, Student>>()?.map { it.value }?.toList() ?: listOf()
            onEndLoading()
        }.addOnFailureListener {
            students = mutableListOf<Student>()
            onEndLoading()
        }
}

fun loadStudents(groupId: Int,onEndLoading: () -> Unit) {
    AppData.database.child("students").orderByChild("groupId").equalTo(groupId.toDouble()).get()
        .addOnSuccessListener { snapshot ->
            students = snapshot.getValue<Map<String, Student>>()?.map { it.value }?.toList() ?: listOf()
            onEndLoading()
        }.addOnFailureListener {
            students = mutableListOf<Student>()
            onEndLoading()
        }
}

fun loadSelectedJournal(studentId: String, date:String, onEndLoading: () -> Unit) {
    AppData
        .database
        .child("journal")
        .orderByChild("date")
        .equalTo(date)
        .get()
        .addOnSuccessListener { snapshot ->
            selectedJournal = snapshot.getValue<Map<String, Journal>>()?.map { it.value }?.toList()?.filter { it.studentId==studentId } ?: listOf()
            onEndLoading()
        }.addOnFailureListener {
            selectedJournal = mutableListOf<Journal>()
            onEndLoading()
        }
}

//JUST FOR DEBUGGING AND DEVELOPMENT

val debugGroups = listOf<Group>(
    Group(0, "Группа не присвоена"),
    Group(1, "Подготовка к школе 4-7 лет"),
    Group(2, "Раннее развитие 2-4 года"),
    Group(3, "Продленка для дошколенка"),
    Group(4, "Продленка 1-4 классы"),
    Group(5, "Экспресс подготовка к школе"),
    Group(6, "Репетиторы казахского и английского языка, 1-9 классы"),
    Group(7, "Репетиторы для дошкольника"),
    Group(8, "Детский психолог"),
    Group(9, "Логопед"),
    Group(10, "Монтессори центр \"Жемчужина\"")
)

fun saveGroupAndRole() {
    //currentRole.Save()
    //for (group in groups) {
    //    group.save()
    //}
    Group(0, "Группа не присвоена").save()
}