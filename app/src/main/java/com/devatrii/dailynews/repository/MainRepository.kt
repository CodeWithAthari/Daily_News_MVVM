package com.devatrii.dailynews.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request.Method
import com.android.volley.toolbox.StringRequest
import com.devatrii.dailynews.Models.ArticleModel
import com.devatrii.dailynews.utils.POSTS_URL
import com.devatrii.dailynews.utils.VolleySingleton
import org.json.JSONArray

class MainRepository(private val context: Context) {
    private val articlesList = MutableLiveData<APIResponses<ArrayList<ArticleModel>>>()
    val articleLiveData get() = articlesList

    fun getArticles(page: String) {
        articleLiveData.value = APIResponses.Loading()
        val url = "$POSTS_URL&page=$page"
        val requestQueue = VolleySingleton.getInstance(context).requestQueue
        val stringRequest = StringRequest(Method.GET, url, {
            if (it != null) {
                val tempList: ArrayList<ArticleModel> = ArrayList()
                val jsonArray = JSONArray(it)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    jsonObject.apply {
                        val model =
                            ArticleModel(
                                id = getInt("id"),
                                date = getString("date"),
                                title = getJSONObject("title").getString("rendered"),
                                content = getJSONObject("content").getString("rendered"),
                                excerpt = getJSONObject("excerpt").getString("rendered"),
                                author = getJSONObject("yoast_head_json").getString("author"),
                                author_profile = getJSONObject("_links").getJSONArray("author")
                                    .getJSONObject(0).getString("href"),
                                reading_time = getJSONObject("yoast_head_json").getJSONObject("twitter_misc")
                                    .getString("Est. reading time"),
                                twitter_site = getJSONObject("yoast_head_json").getString("twitter_site"),
                                link = getString("link"),
                                image = getJSONObject("yoast_head_json").getJSONArray("og_image")
                                    .getJSONObject(0).getString("url"),
                                categories = getJSONArray("categories").get(0).toString().toInt()
                            )
                        tempList.add(model)
                    }
                }
                if (tempList.isNotEmpty()) {
                    articleLiveData.value = APIResponses.Success(tempList)
                }
            }


        }, {
            articleLiveData.value = APIResponses.Error(it.toString())
        })
        requestQueue.add(stringRequest)
    }
}