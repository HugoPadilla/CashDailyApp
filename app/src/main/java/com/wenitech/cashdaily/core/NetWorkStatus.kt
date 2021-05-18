package com.wenitech.cashdaily.core

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

class NetWorkStatus {
    companion object {
        fun hayRed(activity: AppCompatActivity): Boolean{
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netWorkInfo = connectivityManager.activeNetworkInfo
            return netWorkInfo != null && netWorkInfo.isConnected
        }
    }
}