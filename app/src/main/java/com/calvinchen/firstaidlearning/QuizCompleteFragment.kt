package com.calvinchen.firstaidlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_quiz_complete.*


class QuizCompleteFragment : Fragment() {

    private var chapter : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_complete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.let { QuizCompleteFragmentArgs.fromBundle(it) }
        quiz_score.text = args?.score
        chapter = args?.chapter

        return_home.setOnClickListener {
            (activity as QuizQuestionsActivity).onSupportNavigateUp()
        }

        go_to_review.setOnClickListener {
            chapter?.let { it1 -> (activity as QuizQuestionsActivity).navigateToReviewQuestions(it1) }
        }
    }
}