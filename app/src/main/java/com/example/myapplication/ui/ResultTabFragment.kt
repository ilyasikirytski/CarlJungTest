package com.example.myapplication.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentResultTabBinding
import org.json.JSONArray

class ResultTabFragment : Fragment(R.layout.fragment_result_tab) {

    private val binding by viewBinding(FragmentResultTabBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        setTasksText(sharedPref)
        setFirstColumn(sharedPref)
        setSecondColumn(sharedPref)
        setThirdColumn(sharedPref)
        setFourthColumn(sharedPref)
        setFifthColumn(sharedPref)
    }

    private fun setTasksText(sharedPref: SharedPreferences) {
        binding.userQuestionTextView.text = sharedPref.getString("QUESTION", "")
        binding.answerTextView.text = sharedPref.getString("ANSWER", "")
    }

    private fun setFirstColumn(sharedPref: SharedPreferences) {
        val firstList = getSharedPrefList(sharedPref, "FIRST_LIST")

        binding.first1.text = String.format("1: %s", firstList.get(0))
        binding.first2.text = String.format("2: %s", firstList.get(1))
        binding.first3.text = String.format("3: %s", firstList.get(2))
        binding.first4.text = String.format("4: %s", firstList.get(3))
        binding.first5.text = String.format("5: %s", firstList.get(4))
        binding.first6.text = String.format("6: %s", firstList.get(5))
        binding.first7.text = String.format("7: %s", firstList.get(6))
        binding.first8.text = String.format("8: %s", firstList.get(7))
        binding.first9.text = String.format("9: %s", firstList.get(8))
        binding.first10.text = String.format("10: %s", firstList.get(9))
        binding.first11.text = String.format("11: %s", firstList.get(10))
        binding.first12.text = String.format("12: %s", firstList.get(11))
        binding.first13.text = String.format("13: %s", firstList.get(12))
        binding.first14.text = String.format("14: %s", firstList.get(13))
        binding.first15.text = String.format("15: %s", firstList.get(14))
        binding.first16.text = String.format("16: %s", firstList.get(15))
    }

    private fun setSecondColumn(sharedPref: SharedPreferences) {
        val secondList = getSharedPrefList(sharedPref, "SECOND_LIST")

        binding.second1.text = String.format("1: %s", secondList.get(0))
        binding.second2.text = String.format("2: %s", secondList.get(1))
        binding.second3.text = String.format("3: %s", secondList.get(2))
        binding.second4.text = String.format("4: %s", secondList.get(3))
        binding.second5.text = String.format("5: %s", secondList.get(4))
        binding.second6.text = String.format("6: %s", secondList.get(5))
        binding.second7.text = String.format("7: %s", secondList.get(6))
        binding.second8.text = String.format("8: %s", secondList.get(7))
    }

    private fun setThirdColumn(sharedPref: SharedPreferences) {
        val thirdList = getSharedPrefList(sharedPref, "THIRD_LIST")

        binding.third1.text = String.format("1: %s", thirdList.get(0))
        binding.third2.text = String.format("2: %s", thirdList.get(1))
        binding.third3.text = String.format("3: %s", thirdList.get(2))
        binding.third4.text = String.format("4: %s", thirdList.get(3))
    }

    private fun setFourthColumn(sharedPref: SharedPreferences) {
        val fourthList = getSharedPrefList(sharedPref, "FOURTH_LIST")

        binding.fourth1.text = String.format("1: %s", fourthList.get(0))
        binding.fourth2.text = String.format("2: %s", fourthList.get(0))
    }

    private fun setFifthColumn(sharedPref: SharedPreferences) {
        binding.fifth1.text = String.format("1: %s", sharedPref.getString("FIFTH_LIST", ""))
    }

    private fun getSharedPrefList(sharedPref: SharedPreferences, key: String): MutableList<String> {
        val firstList = mutableListOf<String>()
        val jsonArray = JSONArray(sharedPref.getString(key, ""))
        for (i in 0 until jsonArray.length()) {
            firstList.add(jsonArray.getString(i))
        }
        return firstList
    }
}