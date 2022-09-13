package com.example.navidemo.repository

import com.example.navidemo.domain.response.GithubPullResponse
import com.example.navidemo.domain.response.baseresponse.ServerResponse

interface GithubApiRepo {
    suspend fun getAllClosedPullRequest(
        owner: String = "mozilla-mobile",
        repoName: String = "fenix",
        state: String = "closed",
        perPageItem: Int = 100
    ,page:Int=1
    ): ServerResponse<List<GithubPullResponse>>
}