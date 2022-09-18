package com.onp24.onlinenewsportal24.ui.categorywisenews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onp24.onlinenewsportal24.model.data.DetailsNews
import com.onp24.onlinenewsportal24.model.data.News
import com.onp24.onlinenewsportal24.repository.NewsCategoryRepository
import com.onp24.onlinenewsportal24.util.OperationCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.Exception

class CategoryNewsViewModel(val repository: NewsCategoryRepository, val url: String) : ViewModel() {
    var news = MutableLiveData<Array<News>>()
    var listener: OperationCallBack? = null

    init {
        GlobalScope.launch {
            getNewsOperation()
        }
    }

    private suspend fun getNewsOperation() {
        with(Dispatchers.IO) {
            try {
                var doc = getDocumentFromUrl(url)
                var ele = doc.body().getElementsByClass("col-sm-8")
                var newsList = repository.getNews(ele)
                news.postValue(newsList!!)
            } catch (e: Exception) {
                listener?.onError(e.message.toString())
            }
        }
    }

    private fun getDocumentFromUrl(url: String): Document {
        val html = Jsoup.connect(url).get()
        return Jsoup.parse(html.toString())
    }
}