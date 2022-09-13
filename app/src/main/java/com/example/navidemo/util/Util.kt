package com.example.navidemo.util

import android.content.Context
import android.net.ConnectivityManager

object Util {




    fun isNetworkConnected(context:Context):Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?

        connectivityManager?.let { cm->
            return cm.activeNetworkInfo!=null &&  cm.activeNetworkInfo!!.isConnected
        }

return false

    }
}