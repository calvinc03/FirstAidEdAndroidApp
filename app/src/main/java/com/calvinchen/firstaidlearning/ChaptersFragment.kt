package com.calvinchen.firstaidlearning

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import org.json.JSONArray
import org.json.JSONObject


class ChaptersFragment : Fragment() {

    private lateinit var chaptersList : JSONObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        chaptersList = Constants.getJSONObjectFromFile(this.requireContext(), "chapters_list.json")
        return inflater.inflate(R.layout.fragment_chapters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.let { ChaptersFragmentArgs.fromBundle(it) }

        populateSubChapters(view, args?.chapter)
    }

    private fun populateSubChapters(view : View, chapter_num : String?) {
        val layout : LinearLayout = view.findViewById(R.id.chapters_layout)
        val chapter : JSONObject = chaptersList.get(chapter_num) as JSONObject

        val title : String = chapter.get("title") as String
        val titleTv : TextView = view.findViewById(R.id.chapter_title)
        titleTv.text = title

        val subChapters = chapter.get("sub_chap") as JSONArray
        val inf : LayoutInflater = activity?.applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        for (i in 0 until subChapters.length()) {
            val v : View = inf.inflate(R.layout.layout_subchapters, layout, false)

            val tv : TextView = v.findViewById(R.id.subChapter_title)
            tv.text = subChapters[i] as String

            val image : ImageView = v.findViewById(R.id.subChapter_check)
            image.setImageResource(R.drawable.ic_check)

            v.setOnClickListener {
                if (chapter_num != null) {
                    (activity as MainActivity).navigateToInfoPage(chapter_num, subChapters[i] as String)
                }
            }

            layout.addView(v)
        }
    }

    private fun openPage(view : View, file : String) {
        (activity as MainActivity).studyStack.push(file)

        val nextPage = ChaptersFragmentDirections.openPage(file)
        Navigation.findNavController(view).navigate(nextPage)
    }
}