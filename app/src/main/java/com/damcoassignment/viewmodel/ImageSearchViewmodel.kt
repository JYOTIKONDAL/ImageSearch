package com.damcoassignment.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.damcoassignment.R
import com.damcoassignment.base.BaseViewModel
import com.damcoassignment.listener.ImageSearchNavigator
import com.damcoassignment.model.ColorsListModel
import com.damcoassignment.repository.ImageSearchRepository
import com.damcoassignment.utils.ApiConstant
import com.damcoassignment.utils.Event
import com.damcoassignment.utils.NetworkHelper
import com.damcoassignment.utils.Resource
import kotlinx.coroutines.launch

class ImageSearchViewmodel @ViewModelInject constructor(
    val imageSearchRepository: ImageSearchRepository,
    val networkHelper: NetworkHelper
) : BaseViewModel<ImageSearchNavigator>() {

    private val colorsData = MutableLiveData<Event<Resource<ArrayList<ColorsListModel>>>>()
    val colorsLiveData: LiveData<Event<Resource<ArrayList<ColorsListModel>>>>
        get() = colorsData

    fun getSearchColorsList(keyWords: String, numResults: String, format: String) {
        viewModelScope.launch {
            colorsData.postValue(Event(Resource.loading(ApiConstant.COLORS_LIST_DATA, null)))
            if (networkHelper.isNetworkConnected()) {
                imageSearchRepository.getColorsList(keyWords, numResults, format).let {
                    if (it.isSuccessful) {
                        colorsData.postValue(
                            Event(
                                Resource.success(
                                    ApiConstant.COLORS_LIST_DATA,
                                    it.body()
                                )
                            )
                        )
                    } else {
                        if (it.code() == 400) {
                            colorsData.postValue(
                                Event(
                                    Resource.error(
                                        ApiConstant.COLORS_LIST_DATA,
                                        it.code(),
                                        it.message(),
                                        null
                                    )
                                )
                            )
                        } else {
                            colorsData.postValue(
                                Event(
                                    Resource.error(
                                        ApiConstant.COLORS_LIST_DATA,
                                        it.code(),
                                        it.message(),
                                        null
                                    )
                                )
                            )
                        }
                    }
                }
            } else {
                colorsData.postValue(
                    Event(
                        Resource.requiredResource(
                            ApiConstant.COLORS_LIST_DATA,
                            R.string.network_is_not_available
                        )
                    )
                )
            }
        }
    }
}