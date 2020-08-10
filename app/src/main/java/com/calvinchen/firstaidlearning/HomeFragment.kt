package com.calvinchen.firstaidlearning

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedStats = activity?.getSharedPreferences("statistics", Context.MODE_PRIVATE)

        val readingTime = sharedStats?.getLong("ReadingTime", 0L)?.toInt()?.div(1000)
        val readTimeStr = "${readingTime?.div(3600)}h ${readingTime?.rem(3600)?.div(60)}m " +
                "${readingTime?.rem(60)}s"
        read_time_tv.text = readTimeStr

        val quizTime = sharedStats?.getLong("QuizTime", 0L)?.div(1000)?.toInt()
        val quizStr = "${quizTime?.div(3600)}h ${quizTime?.rem(3600)?.div(60)}m " +
                "${quizTime?.rem(60)}s"
        quiz_time_tv.text = quizStr

        val sharedVisited = activity?.getSharedPreferences("visited", Context.MODE_PRIVATE)
        val numVisited = sharedVisited?.all?.size?.times(100)?.div(51)
        if (numVisited != null) {
            read_circleBar.progress = numVisited
            percentage_pages_read.text = "${numVisited}%"
        } else {
            read_circleBar.progress = 0
            percentage_pages_read.text = "0%"
        }

        var quizScore = 0
        var quizQuestions = 0
        val sharedQuiz = activity?.getSharedPreferences("quizResults", Context.MODE_PRIVATE)
        val strings = listOf("chapter_1_quiz", "chapter_2_quiz", "chapter_3_quiz",
            "chapter_4_quiz", "chapter_5_quiz", "chapter_6_quiz", "chapter_7_quiz")

        for (s in strings) {
            val score = sharedQuiz?.getInt(s, -1)!!
            if (score != -1) {
                quizScore += score
                quizQuestions += Constants.getNumQuestions(s)
            }
        }

        if (quizQuestions == 0) {
            quiz_circleBar.progress = 0
            percentage_quiz_score.text = "--%"
        } else {
            quiz_circleBar.progress = quizScore * 100 / quizQuestions
            percentage_quiz_score.text = "${quizScore * 100 / quizQuestions}%"
        }
    }
}