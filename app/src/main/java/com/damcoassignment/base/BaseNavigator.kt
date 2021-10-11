package com.damcoassignment.base

import com.damcoassignment.extensions.empty

interface BaseNavigator {
    fun prepareAlert(title: Int, messageResourceId: Int = 0, message: String = String.empty) {}
    fun setProgressVisibility(visibility: Int) {}
}