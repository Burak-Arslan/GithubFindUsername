package com.example.githubfinduser.api


import com.example.githubfinduser.data.model.GithubFindResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    companion object {
        const val BASE_URL = "https://api.github.com/users/"
    }

    @GET("{user}/repos")
    suspend fun getGitHubFindRepo(@Path("user") user: String): List<GithubFindResponseItem>
}