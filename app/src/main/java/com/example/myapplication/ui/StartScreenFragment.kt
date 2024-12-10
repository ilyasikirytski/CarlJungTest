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
import com.example.myapplication.databinding.FragmentStartScreenBinding

class
StartScreenFragment : Fragment(R.layout.fragment_start_screen) {

    private val navigator = DefaultAppNavigator()
    private val binding by viewBinding(FragmentStartScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenInputText()
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.helpMenu -> showDialog()
            }
            false
        }

        binding.button.setOnClickListener {
            with(sharedPref.edit()) {
                putString("QUESTION", binding.editText.text.toString())
                apply()
            }
            navigator.onNextScreenButtonClicked(this)
        }
    }

    private fun listenInputText() {
        binding.run {
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
                "Тест ассоциаций Юнга может проводиться при проблеме из любой жизненной сферы " +
                        "и при любом вопросе, на который вы никак не можете найти ответ. " +
                        "Это упражнение позволяет обратиться к своему подсознанию (бессознательному) " +
                        "и таким образом по-новому взглянуть на имеющиеся проблемы, " +
                        "понять свои истинные желания, мотивации и причины." +
                        "\n\n" +
                        "Чтобы упражнение на ассоциации Юнга показало свою эффективность, " +
                        "при прохождении важно быть максимально честным и открытым с самим собой. " +
                        "Также настройтесь на работу с образами и на их интерпретацию. " +
                        "Здесь вы не получите конкретную расшифровку в духе «Вы набрали 5-10 баллов, значит, вы интроверт». " +
                        "Вся работа по расшифровке ляжет именно на вас, потому что никто не знает вас, вашу личность, " +
                        "ваше прошлое и собственно ваш вопрос лучше вас самих." +
                        "\n\n" +
                        "Кратко сформулируйте проблему, для которой вы ищете решение. " +
                        "Например, «Отношения с партнером/родителями», " +
                        "«Проблемы с коллегами» или просто «Коллеги», «Партнер», «Родители» и т.д."
            )
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            .create()
            .show()
    }
}