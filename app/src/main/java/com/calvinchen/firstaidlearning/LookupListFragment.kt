package com.calvinchen.firstaidlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_lookup_list.*
import org.json.JSONArray

class LookupListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lookup_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val LookupListJSON = Constants.getJsonArrayFromFile(requireContext(), "lookuplist.json")
        val lookuplist = mutableListOf<LookupItem>();

        for (i in 0 until LookupListJSON.length()) {
            val item = LookupListJSON[i] as JSONArray
            val drawable = resources.getDrawable(resources.getIdentifier(item[1] as String,
                "drawable", requireContext().packageName), null)
            lookuplist.add(LookupItem(drawable, item[0] as String, item[2] as String))
        }

        recycler_view.adapter = LookupItemAdapter(lookuplist)
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)
    }
}