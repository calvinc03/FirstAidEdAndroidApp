package com.calvinchen.firstaidlearning

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_quiz_questions.*
import org.json.JSONArray
import java.io.FileOutputStream


class QuizQuestionsFragment : Fragment() {

    private lateinit var questionList : JSONArray
    private lateinit var questionNumbers: List<Int>
    var index = 0

    private var screenWidth : Int = 0
    private lateinit var chapter : String

    private var accumulatedAnswers = ""
    private var numCorrectAnswers = 0
    private var numQuestions = -1
    private var startTime = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chapter = activity?.intent?.extras?.get("quiz") as String
        questionList = Constants.getJsonArrayFromFile(requireContext(), "$chapter.json")
        questionNumbers = (0 until questionList.length()).shuffled()

        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        screenWidth = metrics.widthPixels

        numQuestions = Constants.getNumQuestions(chapter)
        quiz_progress_bar.max = numQuestions

        populateQuestion(view)
        startTime = System.currentTimeMillis()
    }

    @SuppressLint("SetTextI18n")
    private fun populateQuestion(view : View) {
        val question = questionList[questionNumbers[index]] as JSONArray

        quiz_question.text = question[0] as String

        if (!question.isNull(1)) {
            val drawable = resources.getDrawable(resources.getIdentifier(question[1] as String, "drawable", requireContext().packageName), null)
//            drawable.setBounds(10, 0, screenWidth - 20, (drawable.intrinsicHeight * (screenWidth - 20) / drawable.intrinsicWidth))
            quiz_image.setImageDrawable(drawable)
        } else {
            quiz_image.setImageDrawable(null)
        }

        quiz_progress_bar.progress = index + 1
        quiz_tv_progress.text = "${index + 1}/$numQuestions"
        quiz_option_1.text = question[2] as String
        quiz_option_2.text = question[3] as String
        quiz_option_3.text = question[4] as String
        quiz_option_4.text = question[5] as String

        quiz_submit.setOnClickListener {
            if (quiz_radio.checkedRadioButtonId == -1) return@setOnClickListener
            val checked = view.findViewById(quiz_radio.checkedRadioButtonId) as RadioButton

            val correct = checked.text == question[question[6] as Int]
            numCorrectAnswers += if (correct) 1 else 0

            accumulatedAnswers += "${question[0] as String}:::${checked.text as String}:::${correct}"
            accumulatedAnswers += if (index != numQuestions - 1) "|||" else ""

            index++

            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    quiz_radio.clearCheck()

                    if (index > numQuestions - 1) onFinishQuiz(view)
                    else populateQuestion(view)
                }

                override fun onAnimationStart(p0: Animation?) {}

            })
            view.startAnimation(animation)
        }
    }

    private fun onFinishQuiz(view: View) {
        val outputStream : FileOutputStream
        try {
            outputStream = requireContext().openFileOutput(chapter, Context.MODE_PRIVATE)
            outputStream.write(accumulatedAnswers.toByteArray())
            outputStream.close()
        } catch (e : Exception) {
            e.printStackTrace()
        }

        val sharedPref = activity?.getSharedPreferences("quizResults", Context.MODE_PRIVATE)
        if (sharedPref?.getInt(chapter, -1)!! <= numCorrectAnswers) {
            with (sharedPref.edit()) {
                this?.putInt(chapter, numCorrectAnswers)
                this?.commit()
            }
        }

        val sharedStats = activity?.getSharedPreferences("statistics", Context.MODE_PRIVATE)
        with (sharedStats?.edit()) {
            this?.putLong("QuizTime", (sharedStats?.getLong("QuizTime", 0L)!! + System.currentTimeMillis()
                    - startTime))
            this?.commit()
        }

        val finishQuiz = QuizQuestionsFragmentDirections.completeQuiz("${numCorrectAnswers}/${numQuestions}",
            chapter)
        Navigation.findNavController(view).navigate(finishQuiz)
    }
}