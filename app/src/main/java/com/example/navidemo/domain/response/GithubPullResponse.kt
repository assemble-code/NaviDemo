package com.example.navidemo.domain.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubPullResponse(
    @Json(name="id")
    val id: Int?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "closed_at")
    val closedAt: String?,
    @Json(name="user")
    val user: User?
)

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "login")
    val loginName: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String?
)
