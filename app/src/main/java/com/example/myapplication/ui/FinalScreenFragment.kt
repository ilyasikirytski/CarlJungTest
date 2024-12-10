package com.example.myapplication.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFinalScreenBinding

class FinalScreenFragment : Fragment(R.layout.fragment_final_screen) {

    private val binding by viewBinding(FragmentFinalScreenBinding::bind)
    private val navigator = DefaultAppNavigator()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        binding.answerTextView.text = sharedPref.getString("ANSWER", "")
        binding.userQuestionTextView.text = sharedPref.getString("QUESTION", "")

        binding.run {
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.helpMenu -> showDialog()
                }
                false
            }

            resultButton.setOnClickListener {
                navigator.onResultButtonClicked(requireParentFragment())
            }

            restartButton.setOnClickListener {
                navigator.onNextScreenButtonClicked(requireParentFragment())
            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                "Полученное ключевое слово и есть тот самый главный образ, " +
                        "главная ассоциация, которую вам посылает подсознание. " +
                        "Именно от нее стоит отталкиваться. Повторимся, что фактически " +
                        "интерпретировать результат можете только вы (при необходимости – совместно с психологом). " +
                        "Важно подумать и понять, почему возник именно такой образ. О чем он вам говорит? " +
                        "\n\n" +
                        "Это может быть истинная мотивация или желание, скрытый страх, " +
                        "внутреннее препятствие или установка, которая вам мешает, проблема, " +
                        "спрятанная будто совсем в другой области. Обратите внимание на то, " +
                        "совпадает ли область вашего вопроса и сфера, в которой лежит ключевое слово. " +
                        "Если не совпадает, то с чем это может быть связано?"
            )
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            .create()
            .show()
    }
}