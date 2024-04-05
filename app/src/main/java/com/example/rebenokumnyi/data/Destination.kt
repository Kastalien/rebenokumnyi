package com.example.rebenokumnyi.data

import android.accounts.Account
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.rebenokumnyi.R
import com.google.android.gms.tasks.Tasks

interface URDestination {
    val icon: Int
    val route: String
    val title :Int
}

object ParentSchedule : URDestination {
    @DrawableRes override val icon = R.drawable.schedule
    override val route = "parentschedule"
    @StringRes override val title= R.string.parent_schedule
}

object ParentChat : URDestination {
    @DrawableRes override val icon = R.drawable.chat
    override val route = "parentchat"
    @StringRes override val title= R.string.parent_chat
}

object ParentJournal : URDestination {
    @DrawableRes override val icon = R.drawable.journal
    override val route = "parentjournal"
    @StringRes override val title= R.string.parent_journal
}

object ParentPaydata : URDestination {
    @DrawableRes override val icon = R.drawable.payment
    override val route = "parentpaydata"
    @StringRes override val title= R.string.pay_data
}

object AuthPage : URDestination {
    @DrawableRes override val icon = R.drawable.parents
    override val route = "authpage"
    @StringRes override val title= R.string.auth
}

object AboutPage : URDestination {
    @DrawableRes override val icon = R.drawable.about
    override val route = "aboutpage"
    @StringRes override val title= R.string.about_short
}

object AdminGroup : URDestination {
    @DrawableRes override val icon = R.drawable.group
    override val route = "admingroup"
    @StringRes override val title= R.string.admin_group
}

object AdminTeacher : URDestination {
    @DrawableRes override val icon = R.drawable.teacher
    override val route = "adminteacher"
    @StringRes override val title= R.string.admin_teacher
}

object TeacherSchedule : URDestination {
    @DrawableRes override val icon = R.drawable.schedule
    override val route = "teacherschedule"
    @StringRes override val title= R.string.teacher_schedule
}

object TeacherJournal : URDestination {
    @DrawableRes override val icon = R.drawable.journal
    override val route = "teacherjournal"
    @StringRes override val title= R.string.teacher_journal
}

object TeacherChat : URDestination {
    @DrawableRes override val icon = R.drawable.chat
    override val route = "teacherchat"
    @StringRes override val title= R.string.teacher_chat
}

val parentTabRowScreens = listOf(AuthPage,ParentSchedule, ParentChat, ParentJournal, ParentPaydata, AboutPage)
val notAuthTabRowScreens = listOf(AuthPage,AboutPage)
val adminTabRowScreens = listOf(AuthPage,AdminGroup,AdminTeacher,AboutPage)
val teacherTabRowScreens = listOf(AuthPage, TeacherSchedule, TeacherJournal,TeacherChat, AboutPage)