package com.example.navidemo.domain.response.baseresponse

import java.lang.Exception

sealed class ServerResponse<out  T>{
     data class Success< out T>(val data: T) : ServerResponse<T>()
     data class Error<out T>(val errorMessage:Exception,val data:T?=null) : ServerResponse<T>()
}

sealed class SafeResponse<out  T>{
     data class Success< out T>(val data: T) : SafeResponse<T>()
     data class  Loading<out T>(val data:T? = null): SafeResponse<T>()
     data class Error<out T>(val errorMessage:Exception,val data:T?=null) : SafeResponse<T>()
}



