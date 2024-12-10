package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

interface AppNavigator {
    fun onNextScreenButtonClicked(fragment: Fragment, bundle: Bundle? = null)
    fun onResultButtonClicked(fragment: Fragment)
}