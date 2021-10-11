package com.damcoassignment.ui

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.GridLayoutManager
import com.damcoassignment.R
import com.damcoassignment.adapter.ColorsListAdapter
import com.damcoassignment.base.BaseActivity
import com.damcoassignment.base.ItemClickListener
import com.damcoassignment.databinding.ActivityMainLayoutBinding
import com.damcoassignment.model.ColorsListModel
import com.damcoassignment.utils.EventObserver
import com.damcoassignment.utils.Status
import com.damcoassignment.viewmodel.ImageSearchViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ImageSearchActivity : BaseActivity<ActivityMainLayoutBinding, ImageSearchViewmodel>(),
    ItemClickListener {
    private var gridLayoutManager: GridLayoutManager? = null
    private var adapter: ColorsListAdapter? = null
    private var colorsList: ArrayList<ColorsListModel> = ArrayList()
    private var numResults: String = ""

    override val viewModel: Class<ImageSearchViewmodel>
        get() = ImageSearchViewmodel::class.java

    override fun getBindingVariable(): Int {
        return BR.imageSearchViewModel
    }

    override val layoutId: Int
        get() = R.layout.activity_main_layout

    override fun initUserInterface() {
        updateUi()
    }

    private fun updateUi() {
        numResults = "20"
        setAdapter()
        setObservers()
        viewDataBinding.btnSearch.setOnClickListener {
            hideKeyboardFrom(this, viewDataBinding.mainContainer)
            if (viewDataBinding.searchView.query.length in 3..9) {
                injectedViewModel.getSearchColorsList(
                    viewDataBinding.searchView.query.toString(),
                    numResults,
                    "json"
                )
            }
        }
    }

    private fun setAdapter() {
        gridLayoutManager = GridLayoutManager(this, 2)
        viewDataBinding.colorsDataList.layoutManager = gridLayoutManager
        adapter = ColorsListAdapter(this, this)
        viewDataBinding.colorsDataList.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        colorsList[position].url.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(colorsList[position].url))
            startActivity(browserIntent)
        }
    }

    override fun onLikeUnlikeClick(position: Int) {
        colorsList[position].isLiked = !colorsList[position].isLiked
        adapter?.setItems(colorsList)
    }

    private fun setObservers() {
        injectedViewModel.colorsLiveData.observe(this, EventObserver {
            when (it.status) {
                Status.SUCCESS -> {
                    setProgressBarVisibility(View.GONE)
                    it.data?.let { list ->
                        colorsList = ArrayList()
                        colorsList.addAll(list)
                        adapter?.setItems(list)
                    }
                }
                Status.LOADING -> {
                    setProgressBarVisibility(View.VISIBLE)
                }
                Status.ERROR -> {
                    setProgressBarVisibility(View.GONE)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.RESOURCE -> {
                    setProgressBarVisibility(View.GONE)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    setProgressBarVisibility(View.GONE)
                }
            }
        })
    }

}