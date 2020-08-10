package com.calvinchen.firstaidlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_study.*


class StudyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_study, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeOnClickListeners(view)
    }

    private fun goToChapter(v : View, chapter : String) {
        (activity as MainActivity).studyNav = chapter

        val openChapter = StudyFragmentDirections.openChapter(chapter)
        Navigation.findNavController(v).navigate(openChapter)
    }

    private fun initializeOnClickListeners(view : View) {
        ch1.setOnClickListener {goToChapter(it, "chapter_1")}
        ch2.setOnClickListener {goToChapter(it, "chapter_2")}
        ch3.setOnClickListener {goToChapter(it, "chapter_3")}
        ch4.setOnClickListener {goToChapter(it, "chapter_4")}
        ch5.setOnClickListener {goToChapter(it, "chapter_5")}
        ch6.setOnClickListener {goToChapter(it, "chapter_6")}
        ch7.setOnClickListener {goToChapter(it, "chapter_7")}
    }
}