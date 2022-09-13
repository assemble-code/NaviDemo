package com.example.navidemo.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navidemo.R
import com.example.navidemo.databinding.ActivityMainBinding
import com.example.navidemo.domain.response.baseresponse.SafeResponse
import com.example.navidemo.ui.adapter.GithubPullAdapter
import com.example.navidemo.util.Util.isNetworkConnected
import com.example.navidemo.viewmodel.GetPullRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainActivityBinding: ActivityMainBinding
    private val mainViewModel by viewModels<GetPullRequestViewModel>()
    private var isPaused = false
    private val recyclerViewAdapter = GithubPullAdapter();
    private val isDataLoading = false;
    private val visibileItemThreshold = 5
    private var page = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //set empty adapter
        mainActivityBinding.rvGithubPullInfo.adapter = recyclerViewAdapter

        setObserver()
        setListener()


    }

    private fun setListener() {
        mainActivityBinding.tvShowErrorMsg.setOnClickListener {
            if (isNetworkConnected(this)) {
                mainViewModel.getApi(page)
            } else {
                showRetry()
            }

        }
    }

    private fun setObserver() {
        mainViewModel.githubResponseLiveData.observe(this, Observer { response ->
            when (response) {
                is SafeResponse.Error -> {
                    //close the progress bar
                    mainActivityBinding.showLoading = false
                    mainActivityBinding.showRetry = true
                }
                is SafeResponse.Loading -> {
                    mainActivityBinding.showLoading = true
                    mainActivityBinding.showRetry = false
                }
                is SafeResponse.Success -> {
                    //close the progress bar
                    mainActivityBinding.showLoading = false
                    mainActivityBinding.showRetry = false
                    val currentList = ArrayList(recyclerViewAdapter.currentList)
                    currentList.addAll(response.data)
                    recyclerViewAdapter.submitList(currentList)
                }
            }
        })


        mainActivityBinding.rvGithubPullInfo.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // bail out if scrolling upward or already loading data
                if (dy < 0 || isDataLoading) return

                val visibleItemCount = recyclerView.childCount
                val totalItemCount =
                    mainActivityBinding.rvGithubPullInfo.layoutManager?.itemCount ?: 0
                val firstVisibleItem =
                    (mainActivityBinding.rvGithubPullInfo.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (totalItemCount - visibleItemCount <= firstVisibleItem + visibileItemThreshold) {
                    loadMore()
                }
            }
        })
    }

    fun loadMore() {
        if (isNetworkConnected(this)) {
            mainViewModel.getApi(++page)
        } else {
            Toast.makeText(this, getString(R.string.no_internet_connect), Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isPaused) {
            if (isNetworkConnected(this)) {
                isPaused = false
                mainViewModel.getApi(page)
            } else {
                showRetry()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isPaused = true
    }

    private fun showRetry() {
        mainActivityBinding.showRetry = true
        mainActivityBinding.tvShowErrorMsg.text = getString(R.string.no_internet_connect_retry)
    }

}