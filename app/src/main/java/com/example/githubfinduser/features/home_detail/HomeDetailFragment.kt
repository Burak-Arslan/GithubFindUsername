package com.example.githubfinduser.features.home_detail

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.githubfinduser.R
import com.example.githubfinduser.data.model.GithubFindResponseItem
import com.example.githubfinduser.databinding.FragmentHomeDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeDetailFragment @Inject constructor() : Fragment(R.layout.fragment_home_detail) {

    lateinit var binding: FragmentHomeDetailBinding
    var repo: GithubFindResponseItem? = null

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        binding = FragmentHomeDetailBinding.bind(view)

        init()
        events()
    }

    private fun init() {
        repo = arguments?.get("repo") as GithubFindResponseItem?


        binding.apply {
            Glide
                .with(requireContext())
                .load(repo?.owner?.avatarUrl)
                .into(imgProfile)

            binding.toolbarHomeDetail.navigationIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.back_arrow)
            txtUserName.text = "Username: " + repo?.owner?.login
            txtRepoNameDetail.text = "Repo Full Name: " + repo?.fullName
            txtRepoStarCount.text = "Stars Count: " + repo?.stargazersCount.toString()
        }
    }

    private fun events() {
        binding.toolbarHomeDetail.setNavigationOnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).popBackStack() };
        }
    }
}