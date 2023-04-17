package com.devatrii.dailynews.Models

import com.devatrii.dailynews.LAYOUT_TYPE_SETTING
import java.io.Serializable

data class ArticleModel(
    val id: Int, // id
    val date: String, // date
    val title: String, // yoast_head_json => title
    val content: String, // content => rendered
    val excerpt: String, // excerpt => rendered
    val author: String, // yoast_head_json => author
    val author_profile: String, // author[0] => href
    val reading_time: String, // twitter_misc => Est. reading time"
    val twitter_site: String, // twitter_site
    val link: String, // link
    val image: String, //og_image=>url
    val categories: Int, // categories
    var LAYOUT_TYPE:Int = LAYOUT_TYPE_SETTING
) : Serializable