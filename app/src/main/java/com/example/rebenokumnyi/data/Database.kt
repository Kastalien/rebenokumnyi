package com.example.rebenokumnyi.data

import com.google.firebase.database.getValue

fun loadRole(onEndLoading: () -> Unit) {
    if (AppData.isAuth()) {
        if (isLocalData)
            currentRole = UserRole("EieaSdVnbFhrJxIaTr6GkrFoZRZ2", Roles.ADMIN, 1, "Павел Петрович")
        else {
            AppData.database.child("roles").child(AppData.getUserID()).get()
                .addOnSuccessListener {
                    currentRole = it.getValue<UserRole>() ?: UserRole()
                    if (currentRole.role == Roles.UNKNOWN) {
                        currentRole.userId = AppData.getUserID()
                        currentRole.name = AppData.getUserName()
                        currentRole.save()
                    }
                    loadGroups(onEndLoading)
                }.addOnFailureListener {
                    currentRole = EmptyRole
                    onEndLoading()
                }
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