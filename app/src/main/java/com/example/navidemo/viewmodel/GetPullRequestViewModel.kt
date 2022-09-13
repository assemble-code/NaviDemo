package com.example.navidemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.navidemo.domain.response.GithubPullResponse
import com.example.navidemo.domain.response.baseresponse.SafeResponse
import com.example.navidemo.domain.response.baseresponse.ServerResponse

import com.example.navidemo.repository.GithubApiRepo

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetPullRequestViewModel @Inject constructor(
    var githubApiRepo: GithubApiRepo
) : ViewModel() {

     val githubResponseLiveData = MutableLiveData<SafeResponse<List<GithubPullResponse>>>()

    fun getApi(page:Int) {
        viewModelScope.launch {
            //show progress bar
            githubResponseLiveData.postValue(SafeResponse.Loading())
            when (val response = githubApiRepo.getAllClosedPullRequest( perPageItem = 20, page = page)) {
                is ServerResponse.Success -> {
                    println(response.data)
                    githubResponseLiveData.postValue(SafeResponse.Success(response.data))
                }
                is ServerResponse.Error -> {
                    println(response.errorMessage)
                    githubResponseLiveData.postValue(SafeResponse.Error(response.errorMessage))
                }
            }
        }
    }
}