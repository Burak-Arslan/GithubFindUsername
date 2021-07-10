package com.example.githubfinduser.features.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubfinduser.R
import com.example.githubfinduser.data.model.GithubFindResponseItem
import com.example.githubfinduser.databinding.HomeFragmentBinding
import com.example.githubfinduser.util.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment @Inject constructor() : Fragment(R.layout.home_fragment),
    HomeAdapter.OnItemClickListener {

    lateinit var binding: HomeFragmentBinding
    private val viewModel: GithubFindViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = HomeFragmentBinding.bind(view)

        if (isOnline(requireContext())) {
            init()
            events()
        } else {
            Toast.makeText(
                requireContext(),
                "Cihazınızda İnternet Bağlantısı Bulunmamaktadır!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun events() {
        try {
            binding.btnSearch.setOnClickListener {
                if (!binding.edtSearch.text.isNullOrEmpty()) {
                    viewModel.userName = binding.edtSearch.text.toString()
                    callService()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Search Kısmı Boş Bırakılamaz",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (ex: Exception) {
            Log.e("events", ex.message.toString())
        }
    }

    private fun init() {
        try {
            if (!viewModel.userName.isNullOrEmpty())
                callService()

            binding.toolbar.title = "Home"
        } catch (ex: Exception) {
            Log.e("init", ex.message.toString())
        }
    }

    private fun callService() {
        try {
            runBlocking {
                loadPosts()
            }
        } catch (ex: Exception) {
            Log.e("callService", ex.message.toString())
        }
    }

    private suspend fun loadPosts() {

        viewModel.getGithubFindRepos().collect { state ->
            when (state) {
                is State.Loading -> {

                }
                is State.Success -> {
                    var homeAdapter: HomeAdapter?
                    var movieListSuccess: List<GithubFindResponseItem>? = state.data
                    homeAdapter = movieListSuccess?.let { HomeAdapter(this, it) }
                    binding?.mainRecycler?.adapter = homeAdapter
                    binding?.mainRecycler?.layoutManager =
                        LinearLayoutManager(requireContext())
                }
                is State.Failed -> {

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    override fun onItemClickListenerMovies(currentItem: GithubFindResponseItem) {
        val bundle = bundleOf("repo" to currentItem)
        findNavController().navigate(R.id.action_homeFragment_to_homeDetailFragment, bundle)
        //findNavController().navigate(R.id.action_homeFragment_to_homeDetailFragment)
       // HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment().also { view?.findNavController()?.navigate(it) }
    }
}