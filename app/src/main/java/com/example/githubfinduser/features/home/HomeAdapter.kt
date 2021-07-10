package com.example.githubfinduser.features.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubfinduser.data.model.GithubFindResponseItem
import com.example.githubfinduser.databinding.UserItemBinding


class HomeAdapter(
    private val listener: OnItemClickListener,
    var repoList: List<GithubFindResponseItem>
) :
    RecyclerView.Adapter<HomeAdapter.MainvViewHolder>() {

    var currentPosition = 0
    private var selectedPos = RecyclerView.NO_POSITION


    inner class MainvViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(repoList: GithubFindResponseItem) {
            binding.apply {
                txtRepoName.text = repoList.name
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            selectedPos = layoutPosition
            listener.onItemClickListenerMovies(repoList[selectedPos])
        }
    }


    override fun onBindViewHolder(holder: MainvViewHolder, position: Int) {
        val currentItem = repoList[position]
        if (currentItem != null) {
            holder.bind(currentItem)
            currentPosition = position
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainvViewHolder {
        val binding =
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainvViewHolder(binding)
    }


    interface OnItemClickListener {
        fun onItemClickListenerMovies(currentItem: GithubFindResponseItem)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

}