package com.example.githubfinduser.features.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubfinduser.data.model.GithubFindResponseItem
import com.example.githubfinduser.data.repository.GithubFindRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GithubFindViewModel @Inject constructor(private val repository: GithubFindRepository) : ViewModel() {

    var userName = ""
    fun getGithubFindRepos() = repository.getGithubFindRepo(userName)
}