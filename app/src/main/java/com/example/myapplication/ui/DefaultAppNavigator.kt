package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R

class DefaultAppNavigator : AppNavigator {

    override fun onNextScreenButtonClicked(fragment: Fragment, bundle: Bundle?) {
        fragment.findNavController().navigate(R.id.to_next_screen, args = bundle)
    }

    override fun onResultButtonClicked(fragment: Fragment) {
        fragment.findNavController().navigate(R.id.action_to_result_tab_fragment)
    }
}