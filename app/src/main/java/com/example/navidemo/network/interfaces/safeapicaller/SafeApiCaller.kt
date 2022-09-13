package com.example.navidemo.network.interfaces.safeapicaller

import com.example.navidemo.domain.response.baseresponse.SafeResponse
import com.example.navidemo.domain.response.baseresponse.ServerResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


import java.lang.Exception

abstract class SafeApiCaller {

suspend inline fun<reified T> safeApiCall (  noinline apiToBeCalled:suspend ()-> T): ServerResponse<T> {
 return withContext(Dispatchers.IO){
    try {
        ServerResponse.Success(apiToBeCalled.invoke())
    } catch (e:Exception){
        ServerResponse.Error(e)
    }
}
}


}