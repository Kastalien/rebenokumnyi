package com.example.rebenokumnyi.data

import android.graphics.Color.GREEN
import android.util.Log
import com.example.rebenokumnyi.MainActivity
import com.example.rebenokumnyi.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

val isLocalData:Boolean=false

object AppData {
    private lateinit var auth: FirebaseAuth
    lateinit var context: MainActivity
    lateinit var database: DatabaseReference
    fun initAuth(mainActivity: MainActivity) {
        auth = Firebase.auth
        database = Firebase.database.reference
        context=mainActivity
    }
    fun isAuth():Boolean{
        Log.d("umnlog","in isAuth")
        val user=auth.currentUser
        Log.d("umnlog","out isAuth")
        return !(user == null)
    }
    fun getUserName(): String {
        val user = auth.currentUser
        return if (user != null) {
            user.displayName?:auth.currentUser?.email?:context.getString(R.string.anonymous_user)
        } else auth.currentUser?.email?:context.getString(R.string.anonymous_user)
    }
    fun getUserID(): String {
        val user = auth.currentUser
        return if (user != null) {
            user.uid?:""
        } else ""
    }
    fun getRole():Roles{
        return Roles.PARENTUSER
    }
}