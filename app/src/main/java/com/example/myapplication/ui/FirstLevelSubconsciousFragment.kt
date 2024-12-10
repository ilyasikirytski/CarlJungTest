package com.example.myapplication.ui

import android.R.attr.key
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstLevelSubconsciousBinding
import org.json.JSONArray


class FirstLevelSubconsciousFragment : Fragment(R.layout.fragment_first_level_subconscious) {

    private val binding by viewBinding(FragmentFirstLevelSubconsciousBinding::bind)
    private val firstScreenList = mutableListOf<String>()
    private var number = 16
    private val navigator = DefaultAppNavigator()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        binding.textInputLayout.hint = "Введите $number ассоциаций связанных с вопросом"

        val customAdapter = CustomAdapter(firstScreenList)
        binding.recyclerView.adapter = customAdapter

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
                if (number == 1) {
                    firstScreenList.add(editText.text.toString())
                    val bundle = Bundle()
                    bundle.putStringArrayList("LIST", ArrayList(firstScreenList))

                    val jsonArray = JSONArray(firstScreenList)
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString("FIRST_LIST", jsonArray.toString())
                    editor.apply()

                    navigator.onNextScreenButtonClicked(requireParentFragment(), bundle)
                } else {
                    firstScreenList.add(editText.text.toString())
                    editText.setText("")
                    number--
                    textInputLayout.hint = "Введите $number ассоциаций связанных с вопросом"
                    customAdapter.notifyDataSetChanged()
                }
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
                "Запишите 16 ассоциаций к выбранной проблеме/теме. " +
                        "Пишите все, что приходит в голову, даже если с логической точки зрения " +
                        "это кажется смешным, абсурдным, из другой темы и пр. " +
                        "Ассоциаций должно быть именно 16"
            )
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            .create()
            .show()
    }
}