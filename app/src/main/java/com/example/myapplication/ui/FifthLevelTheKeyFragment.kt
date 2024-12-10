package com.example.myapplication.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFifthLevelTheKeyBinding

class FifthLevelTheKeyFragment : Fragment(R.layout.fragment_fifth_level_the_key) {

    private val binding by viewBinding(FragmentFifthLevelTheKeyBinding::bind)

    private val navigator = DefaultAppNavigator()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val firstScreenList = arguments?.getStringArrayList("LIST")

        firstScreenList?.let { list ->
            binding.firstWordTextView.text = list[0]
            binding.secondWordTextView.text = list[1]
        }
        binding.textInputLayout.hint = "Введите ассоциацию с двумя верхними словами."

        binding.run {

            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.helpMenu -> showDialog()
                }
                false
            }

            button.setOnClickListener {
                val bundle = Bundle()
                with(sharedPref.edit()) {
                    putString("FIFTH_LIST", editText.text.toString())
                    putString("ANSWER", editText.text.toString())
                    apply()
                }
                navigator.onNextScreenButtonClicked(requireParentFragment(), bundle)
            }

            editText.addTextChangedListener {
                if (it.isNullOrEmpty()) {
                    binding.button.visibility = View.GONE
                } else {
                    binding.button.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                "Придумайте к паре объединяющую их ассоциацию."
            )
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            .create()
            .show()
    }
}