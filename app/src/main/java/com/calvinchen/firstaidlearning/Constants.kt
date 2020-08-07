package com.calvinchen.firstaidlearning

import android.content.Context
import android.os.ParcelFileDescriptor.open
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

object Constants {

    fun getJsonArrayFromFile(context : Context, fileName : String): JSONArray {
        var jsonString : String? = null
        var inputStream : InputStream? = null

        try {
            inputStream = context.assets.open(fileName)
            jsonString = inputStream.bufferedReader().use{it.readText()}
        } catch (e : IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            return JSONArray(jsonString)
        }
    }

    fun getJSONObjectFromFile(context : Context, fileName : String): JSONObject {
        var jsonString : String? = null
        var inputStream : InputStream? = null

        try {
            inputStream = context.assets.open(fileName)
            jsonString = inputStream.bufferedReader().use{it.readText()}
        } catch (e : IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            return JSONObject(jsonString)
        }
    }

    fun getNumQuestions(chapter : String) : Int {
        return when (chapter) {
            "chapter_1_quiz" -> 8
            "chapter_2_quiz" -> 20
            "chapter_3_quiz" -> 15
            "chapter_4_quiz" -> 20
            "chapter_5_quiz" -> 30
            "chapter_6_quiz" -> 15
            "chapter_7_quiz" -> 15
            else -> -1
        }
    }
}
