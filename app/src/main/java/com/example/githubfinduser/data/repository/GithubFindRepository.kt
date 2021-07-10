package com.example.githubfinduser.data.repository


import com.example.githubfinduser.api.Api
import com.example.githubfinduser.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubFindRepository @Inject constructor(
    private val api: Api
) {

    fun getGithubFindRepo(userName:String) = flow {

        emit(State.loading())

        var getGithubFind = api.getGitHubFindRepo(userName)
        emit(State.success(getGithubFind))

    }.catch {

        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}