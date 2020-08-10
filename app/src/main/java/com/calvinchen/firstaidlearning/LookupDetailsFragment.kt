package com.calvinchen.firstaidlearning

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.inSpans
import androidx.core.text.scale
import kotlinx.android.synthetic.main.fragment_lookup_details.*
import org.json.JSONArray
import org.json.JSONObject

class LookupDetailsFragment : Fragment() {

    private lateinit var details : JSONObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lookup_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailsList = Constants.getJsonArrayFromFile(requireContext(), "lookupdetails.json")
        val args = arguments?.let { LookupDetailsFragmentArgs.fromBundle(it) }
        details = detailsList[args?.position!!] as JSONObject

        (activity as MainActivity).lookupNav = args.position
        inflatePage()
    }

    private fun inflatePage() {
        val inf : LayoutInflater = activity?.applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val subInjuries = details.get("info") as JSONArray
        for (i in 0 until subInjuries.length()) {
            val v : View = inf.inflate(R.layout.layout_injury_reference, details_inflater, false)

            val objectAtIndex = subInjuries[i] as JSONObject

            val titleTV : TextView = v.findViewById(R.id.injury_title)
            titleTV.text = objectAtIndex.get("title") as String

            val signsTV : TextView = v.findViewById(R.id.signs_symptoms)
            val signsList = objectAtIndex.get("Signs and Symptoms") as JSONArray
            val signsText = SpannableStringBuilder()
            for (k in 0 until signsList.length()) {
                if (Build.VERSION.SDK_INT >= 28) {
                    signsText.inSpans(BulletSpan(15, Color.BLACK, 8)) { scale(0.95F) { append(signsList[k]
                            as String + "\n") } }
                    signsText.scale(0.2F) {append("\n")}
                }
                else {
                    signsText.inSpans(BulletSpan(15)) { scale(0.95F) { append(signsList[k] as
                            String  + "\n") } }
                    signsText.scale(0.2F) {append("\n")}
                }
            }
            signsTV.text = signsText

            val aidTV : TextView = v.findViewById(R.id.first_aid)
            val aidList = objectAtIndex.get("First Aid") as JSONArray
            val aidText = SpannableStringBuilder()
            for (j in 0 until aidList.length()) {
                if (Build.VERSION.SDK_INT >= 28) {
                    aidText.inSpans(BulletSpan(15, Color.BLACK, 8)) { scale(0.95F) { append(aidList[j] as String + "\n") } }
                    aidText.scale(0.2F) {append("\n")}
                }
                else {
                    aidText.inSpans(BulletSpan(15)) { scale(0.95F) { append(aidList[j] as String + "\n") } }
                    aidText.scale(0.2F) {append("\n")}
                }
            }
            aidTV.text = aidText

            details_inflater.addView(v, details_inflater.indexOfChild(read_more))
        }

        read_more.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences("visited", Context.MODE_PRIVATE)
            with(sharedPref?.edit()) {
                this?.putBoolean(details.get("sub_chap") as String, true)
                this?.apply()
            }
            (activity as MainActivity).navigateToInfoPage(
                details.get("chapter") as String,
                details.get("sub_chap") as String
            )
        }
    }
}