package com.devatrii.dailynews

import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.devatrii.dailynews.Adapters.INTENT_ARTICLE_MODEL_EXTRA
import com.devatrii.dailynews.Models.ArticleModel
import com.devatrii.dailynews.databinding.ActivityArticleBinding
import com.devatrii.dailynews.utils.ImageGetter
import com.devatrii.dailynews.utils.convertDateFormat
import com.devatrii.dailynews.utils.loadImageWithGlide
import com.devatrii.dailynews.utils.logInfo

private const val TAG = "Article_Activity"

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    val activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val model = intent.getSerializableExtra(INTENT_ARTICLE_MODEL_EXTRA) as ArticleModel
        logInfo(TAG, "Model $model")
        binding.apply {
            mTitle.text = model.title
            mAuthorName.text = model.author
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mPublishDate.text = convertDateFormat(model.date)
            }
            mArticle.displayHtml(model.content)
            mArticle.movementMethod = LinkMovementMethod.getInstance();
            loadImageWithGlide(model.image, mArticleBanner, activity)
        }
    }

    private fun TextView.displayHtml(html: String) {
        val imageGetter = ImageGetter(resources, this, activity)

        val styledText = HtmlCompat.fromHtml(
            html, HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, null
        )
        movementMethod = LinkMovementMethod.getInstance()
        text = styledText

    }


}