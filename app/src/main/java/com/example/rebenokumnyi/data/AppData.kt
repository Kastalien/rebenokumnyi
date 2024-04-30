package com.example.rebenokumnyi.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.rebenokumnyi.MainActivity
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.MyUploadService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import java.util.Locale

val isLocalData:Boolean=false

object AppData {
    private lateinit var auth: FirebaseAuth
    lateinit var context: MainActivity
    lateinit var database: DatabaseReference
    var currentChild = Student()
    lateinit var cameraIntent: ActivityResultLauncher<Array<String>>
    private lateinit var broadcastReceiver: BroadcastReceiver
    private var downloadUrl: Uri? = null
    private var fileUri: Uri? = null
    var onStartUpload: () -> Unit = {}
    var onEndUpload: (String) -> Unit = {}
    private fun uploadFromUri(uploadUri: Uri) {
        Log.d("urlog", "uploadFromUri:src: $uploadUri")
        onStartUpload()
        // Save the File URI
        fileUri = uploadUri
        downloadUrl = null
        // Start MyUploadService to upload the file, so that the file is uploaded
        // even if this Activity is killed or put in the background
        context.startService(
            Intent(context, MyUploadService::class.java)
                .putExtra(MyUploadService.EXTRA_FILE_URI, uploadUri)
                .setAction(MyUploadService.ACTION_UPLOAD),
        )
    }

    fun initAuth(mainActivity: MainActivity) {
        auth = Firebase.auth
        database = Firebase.database.reference
        context=mainActivity
        cameraIntent = context.registerForActivityResult(ActivityResultContracts.OpenDocument()) { fileUri ->
            if (fileUri != null) {
                uploadFromUri(fileUri)
            } else {
                Log.w("urlog", "File URI is null")
            }
        }
        // Local broadcast receiver
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.d("urlog", "onReceive:$intent")
                //hideProgressBar()
                when (intent.action) {
                    MyUploadService.UPLOAD_COMPLETED, MyUploadService.UPLOAD_ERROR -> onUploadResultIntent(intent)
                }
            }
        }
        val manager = LocalBroadcastManager.getInstance(context)
        manager.registerReceiver(broadcastReceiver, MyUploadService.intentFilter)
    }

    private fun onUploadResultIntent(intent: Intent) {
        // Got a new intent from MyUploadService with a success or failure
        downloadUrl = intent.getParcelableExtra(MyUploadService.EXTRA_DOWNLOAD_URL)
        fileUri = intent.getParcelableExtra(MyUploadService.EXTRA_FILE_URI)
        onEndUpload(downloadUrl.toString())
        Log.d("urlog", "downloadUrl:$downloadUrl fileUri:$fileUri")
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
        return currentRole.role
    }
}