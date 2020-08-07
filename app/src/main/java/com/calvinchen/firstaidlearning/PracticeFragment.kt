package com.calvinchen.firstaidlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_practice.*


class PracticeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeClickListeners()
    }

    private fun initializeClickListeners() {
        ch1_quiz_tab.setOnClickListener {(activity as MainActivity).navigateToReviewQuestions("chapter_1_quiz")}
        ch2_quiz_tab.setOnClickListener {(activity as MainActivity).navigateToReviewQuestions("chapter_2_quiz")}
        ch3_quiz_tab.setOnClickListener {(activity as MainActivity).navigateToReviewQuestions("chapter_3_quiz")}
        ch4_quiz_tab.setOnClickListener {(activity as MainActivity).navigateToReviewQuestions("chapter_4_quiz")}
        ch5_quiz_tab.setOnClickListener {(activity as MainActivity).navigateToReviewQuestions("chapter_5_quiz")}
        ch6_quiz_tab.setOnClickListener {(activity as MainActivity).navigateToReviewQuestions("chapter_6_quiz")}
        ch7_quiz_tab.setOnClickListener {(activity as MainActivity).navigateToReviewQuestions("chapter_7_quiz")}
    }
}