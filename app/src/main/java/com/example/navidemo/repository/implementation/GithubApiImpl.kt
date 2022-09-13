package com.example.navidemo.repository.implementation

import com.example.navidemo.domain.response.GithubPullResponse
import com.example.navidemo.domain.response.baseresponse.ServerResponse
import com.example.navidemo.network.interfaces.GithubAPI
import com.example.navidemo.network.interfaces.safeapicaller.SafeApiCaller
import com.example.navidemo.repository.GithubApiRepo

class GithubApiImpl(private val githubAPI: GithubAPI) :SafeApiCaller(),GithubApiRepo{
    override suspend fun getAllClosedPullRequest(
        owner: String,
        repoName: String,
        state:String,
        perPageItem:Int,
        page:Int
    ): ServerResponse<List<GithubPullResponse>> {
       return safeApiCall { githubAPI.getAllClosedPullRequest(owner,repoName,state,perPageItem,page) }
    }
}