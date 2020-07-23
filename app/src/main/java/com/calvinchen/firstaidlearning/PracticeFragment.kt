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
        ch1_quiz_tab.setOnClickListener { updateDropdown(it) }
        ch2_quiz_tab.setOnClickListener { updateDropdown(it) }
        ch3_quiz_tab.setOnClickListener { updateDropdown(it) }
        ch4_quiz_tab.setOnClickListener { updateDropdown(it) }
        ch5_quiz_tab.setOnClickListener { updateDropdown(it) }
        ch6_quiz_tab.setOnClickListener { updateDropdown(it) }
        ch7_quiz_tab.setOnClickListener { updateDropdown(it) }
        ch8_quiz_tab.setOnClickListener { updateDropdown(it) }

        ch1_begin_quiz_btn.setOnClickListener {(activity as MainActivity).navigateToQuizQuestions("chapter_1_quiz.json")}
        ch2_begin_quiz_btn.setOnClickListener {(activity as MainActivity).navigateToQuizQuestions("chapter_2_quiz.json")}
        ch3_begin_quiz_btn.setOnClickListener {(activity as MainActivity).navigateToQuizQuestions("chapter_3_quiz.json")}
        ch4_begin_quiz_btn.setOnClickListener {(activity as MainActivity).navigateToQuizQuestions("chapter_4_quiz.json")}
        ch5_begin_quiz_btn.setOnClickListener {(activity as MainActivity).navigateToQuizQuestions("chapter_5_quiz.json")}
        ch6_begin_quiz_btn.setOnClickListener {(activity as MainActivity).navigateToQuizQuestions("chapter_6_quiz.json")}
        ch7_begin_quiz_btn.setOnClickListener {(activity as MainActivity).navigateToQuizQuestions("chapter_7_quiz.json")}
        ch8_begin_quiz_btn.setOnClickListener {(activity as MainActivity).navigateToQuizQuestions("chapter_8_quiz.json")}

        ch1_review_ans_btn.setOnClickListener {}
        ch2_review_ans_btn.setOnClickListener {}
        ch3_review_ans_btn.setOnClickListener {}
        ch4_review_ans_btn.setOnClickListener {}
        ch5_review_ans_btn.setOnClickListener {}
        ch6_review_ans_btn.setOnClickListener {}
        ch7_review_ans_btn.setOnClickListener {}
        ch8_review_ans_btn.setOnClickListener {}
    }

    private fun updateDropdown(view : View) {
        when(view.id) {
            R.id.ch1_quiz_tab -> {
                if (ch1_quiz_review.visibility == View.VISIBLE) {
                    ch1_quiz_review.visibility = View.GONE
                } else {
                    ch1_quiz_review.visibility = View.VISIBLE
                }
            }
            R.id.ch2_quiz_tab -> {
                if (ch2_quiz_review.visibility == View.VISIBLE) {
                    ch2_quiz_review.visibility = View.GONE
                } else {
                    ch2_quiz_review.visibility = View.VISIBLE
                }
            }
            R.id.ch3_quiz_tab -> {
                if (ch3_quiz_review.visibility == View.VISIBLE) {
                    ch3_quiz_review.visibility = View.GONE
                } else {
                    ch3_quiz_review.visibility = View.VISIBLE
                }
            }
            R.id.ch4_quiz_tab -> {
                if (ch4_quiz_review.visibility == View.VISIBLE) {
                    ch4_quiz_review.visibility = View.GONE
                } else {
                    ch4_quiz_review.visibility = View.VISIBLE
                }
            }
            R.id.ch5_quiz_tab -> {
                if (ch5_quiz_review.visibility == View.VISIBLE) {
                    ch5_quiz_review.visibility = View.GONE
                } else {
                    ch5_quiz_review.visibility = View.VISIBLE
                }
            }
            R.id.ch6_quiz_tab -> {
                if (ch6_quiz_review.visibility == View.VISIBLE) {
                    ch6_quiz_review.visibility = View.GONE
                } else {
                    ch6_quiz_review.visibility = View.VISIBLE
                }
            }
            R.id.ch7_quiz_tab -> {
                if (ch7_quiz_review.visibility == View.VISIBLE) {
                    ch7_quiz_review.visibility = View.GONE
                } else {
                    ch7_quiz_review.visibility = View.VISIBLE
                }
            }
            R.id.ch8_quiz_tab -> {
                if (ch8_quiz_review.visibility == View.VISIBLE) {
                    ch8_quiz_review.visibility = View.GONE
                } else {
                    ch8_quiz_review.visibility = View.VISIBLE
                }
            }
        }
    }
}