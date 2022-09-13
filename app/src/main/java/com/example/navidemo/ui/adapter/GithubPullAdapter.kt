package com.example.navidemo.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.navidemo.R
import com.example.navidemo.databinding.ClosedPullRequestRecycleItemBinding
import com.example.navidemo.domain.response.GithubPullResponse

class GithubPullAdapter :
    ListAdapter<GithubPullResponse, GithubPullAdapter.GithubPullViewHolder>(DiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubPullViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val viewBiding = DataBindingUtil.inflate<ClosedPullRequestRecycleItemBinding>(
            layoutInflater,
            R.layout.closed_pull_request_recycle_item,
            parent,
            false
        )

        return GithubPullViewHolder(viewBiding)
    }

    override fun onBindViewHolder(holder: GithubPullViewHolder, position: Int) {
       holder.bindData(getItem(holder.bindingAdapterPosition)!!)
    }


    inner class GithubPullViewHolder(private val viewBinding: ClosedPullRequestRecycleItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindData(githubPullResponse: GithubPullResponse) {
            viewBinding.model = githubPullResponse
        }

    }

    class DiffCallBack : DiffUtil.ItemCallback<GithubPullResponse>() {
        override fun areItemsTheSame(
            oldItem: GithubPullResponse,
            newItem: GithubPullResponse
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: GithubPullResponse,
            newItem: GithubPullResponse
        ): Boolean = oldItem.id == newItem.id
    }

}