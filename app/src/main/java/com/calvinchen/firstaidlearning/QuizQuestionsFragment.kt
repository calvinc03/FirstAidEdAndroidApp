package com.calvinchen.firstaidlearning

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import kotlinx.android.synthetic.main.fragment_quiz_questions.*
import org.json.JSONArray


class QuizQuestionsFragment : Fragment() {

    lateinit var questionList : JSONArray
    lateinit var questionNumbers: List<Int>
    var index = 0

    private var screenWidth : Int = 0

    var userAnswers = mutableListOf<String>()
    var correctAnswers = mutableListOf<Boolean>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionList = Constants.getJsonArrayFromFile(requireContext(), activity?.intent?.extras?.get("quiz") as String)
        generateRandomList()

        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        screenWidth = metrics.widthPixels - 20

        println(questionList.length())
        println(questionNumbers.size)
        populateQuestion(view)
    }

    private fun generateRandomList() {
        questionNumbers = (0 until questionList.length()).shuffled()
    }

    @SuppressLint("SetTextI18n")
    private fun populateQuestion(view : View) {
        val question = questionList[questionNumbers[index]] as JSONArray

        quiz_question.text = question[0] as String

        if (question[1] !== null) {
            val drawable = resources.getDrawable(resources.getIdentifier(question[1] as String, "drawable", requireContext().packageName), null)
            drawable.setBounds(10, 0, screenWidth, (drawable.intrinsicHeight * screenWidth) / drawable.intrinsicWidth)
            quiz_image.setImageDrawable(drawable)
        }

        quiz_progress_bar.progress = index + 1
        quiz_tv_progress.text = "${index+1}/10"
        quiz_option_1.text = question[2] as String
        quiz_option_2.text = question[3] as String
        quiz_option_3.text = question[4] as String
        quiz_option_4.text = question[5] as String

        quiz_submit.setOnClickListener {
            val checked = view.findViewById(quiz_radio.checkedRadioButtonId) as RadioButton

            userAnswers.add(checked.text as String)
            correctAnswers.add(checked.text == question[question[6] as Int] as String)

            quiz_radio.clearCheck()

            index++
            if (index > 9) {

            } else populateQuestion(view)
        }
    }
}