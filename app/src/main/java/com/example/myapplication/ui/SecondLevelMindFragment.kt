package com.example.myapplication.ui

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
import com.example.myapplication.databinding.FragmentSecondLevelMindBinding
import org.json.JSONArray


class SecondLevelMindFragment : Fragment(R.layout.fragment_second_level_mind) {

    private val binding by viewBinding(FragmentSecondLevelMindBinding::bind)
    private val secondScreenList = mutableListOf<String>()
    private var number = 8
    private var pairNumber = 0
    private val navigator = DefaultAppNavigator()
    private val listOfPairs = mutableListOf<Pair<String, String>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val firstScreenList = arguments?.getStringArrayList("LIST")

        firstScreenList?.let { list ->
            var firstEl: String
            var secondEl: String
            for (index in list.indices) {
                if (index < 16) {
                    if (index % 2 == 0) {
                        firstEl = list[index]
                        secondEl = list[index + 1]
                        listOfPairs.add(Pair(firstEl, secondEl))
                    }
                }
            }
        }
        bindWordPair()
        binding.textInputLayout.hint = "Введите ассоциацию с двумя верхними словами."
        binding.countOfPairs.text = "Осталось пар: $number"

        val customAdapter = CustomAdapter(secondScreenList)
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
                    secondScreenList.add(editText.text.toString())
                    val bundle = Bundle()
                    bundle.putStringArrayList("LIST", ArrayList(secondScreenList))

                    val jsonArray = JSONArray(secondScreenList)
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString("SECOND_LIST", jsonArray.toString())
                    editor.apply()

                    navigator.onNextScreenButtonClicked(requireParentFragment(), bundle)
                } else {
                    bindWordPair()
                    secondScreenList.add(editText.text.toString())
                    editText.setText("")
                    number--
                    binding.countOfPairs.text = "Осталось пар: $number"
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

    private fun bindWordPair() {
        listOfPairs.let {
            binding.firstWordTextView.text = it[pairNumber].first
            binding.secondWordTextView.text = it[pairNumber].second
        }
        pairNumber++
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                "Придумайте к каждой паре объединяющую их ассоциацию. " +
                        "Их должно получиться 8. Вновь записанные слова/сочетания также объединятся в пары. " +
                        "И так, пока у вас не останется одна ассоциация – ключевое слово/понятие."
            )
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            .create()
            .show()
    }
}