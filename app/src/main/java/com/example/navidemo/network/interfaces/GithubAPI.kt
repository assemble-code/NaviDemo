package com.example.navidemo.network.interfaces

import com.example.navidemo.domain.response.GithubPullResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {
    @GET("repos/{owner}/{repo}/pulls")
    suspend fun getAllClosedPullRequest(@Path("owner") owner:String="mozilla-mobile",@Path("repo") repoName:String ="fenix",@Query("state") state:String,@Query("per_page") perPageItem:Int,@Query("page") pageNumber:Int): List<GithubPullResponse>
}